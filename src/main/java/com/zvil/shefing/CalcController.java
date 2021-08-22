package com.zvil.shefing;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.zvil.shefing.aop.CacheCalc;

/**
 * The Controller object that implements the REST API
 * @author Zvi Lifshitz
 */

@RestController
public class CalcController {
    /**
     * Calculate using the values in the CalcResource entity. Check for edge cases (invalid values, divide by
     * zero, missing values, unrecognized operand) and return a graceful message if they occur.
     * @param calc  a CalcModel entity converted from the JSON posted by the user
     * @return the equation with the result. If an exception occurs return a graceful message with the details.
     */
    @PostMapping(
        path = "calc",
        consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = MediaType.TEXT_PLAIN_VALUE
    )
    @CacheCalc
    public @ResponseBody String calculate(@RequestBody CalcModel calc) {
        return Calculate.process(calc.getLeft(), calc.getOperator(), calc.getRight());
    }
}
