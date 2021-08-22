package com.zvil.shefing.aop;

import com.zvil.shefing.CalcModel;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * The class that encapsulates the cache management aspect
 *
 * @author Zvi Lifshitz
 */
@Aspect
@Component
public class CacheManager {
    private final HashMap<CalcModel, String> cache;
    private final ReentrantReadWriteLock lock;              // We use it to synchronize the cache
    
    /**
     * Cache shall not exceed a predefined size, in real life this value should be configurable through the
     * application's properties file. Another option is to set it through an API, which will give us an easy
     * way to test this mechanism. But this is too much for this little test...
     */
    private static final int CACHE_LIMIT = 1000;

    /**
     * Instantiate the cache in the constructor
     */
    public CacheManager() {
        cache = new HashMap<>();
        lock = new ReentrantReadWriteLock();
    }

    /**
     * Do the work. Try to locate the input object in the cash. If found then return the value from the
     * cash otherwise calculate the result and store it in the cash.
     * @param joinPoint     Contains information about the execution point, including the arguments of the method
     * @return      the result either from the cash or by calculation.
     * @throws Throwable
     */
    @Around("@annotation(CacheCalc)")
    public Object ManageCache(ProceedingJoinPoint joinPoint) throws Throwable {
        // Get the argument and locate it in the cache
        CalcModel key = (CalcModel)(joinPoint.getArgs()[0]);
        if (key == null)
            return joinPoint.proceed();
        String result = getFromCache(key);
        
        // If the object is found in the cache return the cached value. Otherwise proceed with the original
        // method and store the result in the cash
        if (result == null) {
            result = (String)joinPoint.proceed();
            storeInCache(key, result);
        }
        return result;
    }
    
    /**
     * Get an object from the map while synchronizing the map
     * @param key      the key to look for
     * @return          the found object
     */
    private String getFromCache(CalcModel key) {
        lock.readLock().lock();
        try {
            return cache.get(key);
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Store an object in the map while synchronizing the map. If the cache size exceeds the configured limit
     * clear it before adding a new entry.
     * @param key       the key
     * @param value     the value
     */
    private void storeInCache(CalcModel key, String value) {
        lock.writeLock().lock();
        try {
            if (cache.size() >= CACHE_LIMIT)
                cache.clear();
            cache.put(key, value);
        } finally {
            lock.writeLock().unlock();
        }
    }
}
