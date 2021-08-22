package com.zvil.shefing;

import static org.hamcrest.CoreMatchers.containsString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ShefingTestApplicationTests {
    @Autowired
    private MockMvc mvc;

    @Test
    void testPlus() throws Exception {
        mvc.perform(
            MockMvcRequestBuilders.post("/calc")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{  \n" +
                "    \"operator\":\"plus\",  \n" +
                "    \"left\": 5,  \n" +
                "    \"right\": 7  \n" +
                "}"
            ))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("5+7=12"))
        );
    }

    @Test
    void testMinus() throws Exception {
        mvc.perform(
            MockMvcRequestBuilders.post("/calc")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{  \n" +
                "    \"operator\":\"minus\",  \n" +
                "    \"left\": 5,  \n" +
                "    \"right\": 7  \n" +
                "}"
            ))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("5-7=-2"))
        );
    }

    @Test
    void testMultiply() throws Exception {
        mvc.perform(
            MockMvcRequestBuilders.post("/calc")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{  \n" +
                "    \"operator\":\"multiply\",  \n" +
                "    \"left\": 5,  \n" +
                "    \"right\": 7  \n" +
                "}"
            ))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("5*7=35"))
        );
    }

    @Test
    void testDivide() throws Exception {
        mvc.perform(
            MockMvcRequestBuilders.post("/calc")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{  \n" +
                "    \"operator\":\"divide\",  \n" +
                "    \"left\": 5,  \n" +
                "    \"right\": 7  \n" +
                "}"
            ))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("5/7=0.7142857142857143"))
        );
    }

    @Test
    void testDivideByZero() throws Exception {
        mvc.perform(
            MockMvcRequestBuilders.post("/calc")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{  \n" +
                "    \"operator\":\"divide\",  \n" +
                "    \"left\": 5,  \n" +
                "    \"right\": 0  \n" +
                "}"
            ))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Divide by zero"))
        );
    }

    @Test
    void testWrongOp() throws Exception {
        mvc.perform(
            MockMvcRequestBuilders.post("/calc")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{  \n" +
                "    \"operator\":\"power\",  \n" +
                "    \"left\": 5,  \n" +
                "    \"right\": 0  \n" +
                "}"
            ))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Unrecognized operator 'power'"))
        );
    }

    @Test
    void testMissingOp() throws Exception {
        mvc.perform(
            MockMvcRequestBuilders.post("/calc")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{  \n" +
                "    \"operators\":\"power\",  \n" +
                "    \"left\": 5,  \n" +
                "    \"right\": 0  \n" +
                "}"
            ))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Operator is missing"))
        );
    }

    @Test
    void testMissingLeftOp() throws Exception {
        mvc.perform(
            MockMvcRequestBuilders.post("/calc")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{  \n" +
                "    \"operator\":\"divide\",  \n" +
                "    \"lefti\": 5,  \n" +
                "    \"right\": 0  \n" +
                "}"
            ))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("The left operand is missing"))
        );
    }

    @Test
    void testMissingRightOp() throws Exception {
        mvc.perform(
            MockMvcRequestBuilders.post("/calc")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{  \n" +
                "    \"operator\":\"divide\",  \n" +
                "    \"left\": 5,  \n" +
                "    \"righti\": 0  \n" +
                "}"
            ))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("The right operand is missing"))
        );
    }

    @Test
    void tesInvalidNumber() throws Exception {
        mvc.perform(
            MockMvcRequestBuilders.post("/calc")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{  \n" +
                "    \"operator\":\"divide\",  \n" +
                "    \"left\": 5.1,  \n" +
                "    \"right\": 0  \n" +
                "}"
            ))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("5.1 is not an integer number"))
        );
    }

    /**
     * Repeat the previous test in order to test the cache
     * @throws Exception 
     */
    @Test
    void testCache() throws Exception {
        mvc.perform(
            MockMvcRequestBuilders.post("/calc")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{  \n" +
                "    \"operator\":\"divide\",  \n" +
                "    \"left\": 5.1,  \n" +
                "    \"right\": 0  \n" +
                "}"
            ))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("5.1 is not an integer number"))
        );
    }

    @Test
    void testBadJSON() throws Exception {
        mvc.perform(
            MockMvcRequestBuilders.post("/calc")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{  \n" +
                "    \"operator\":\"divide\"  \n" +
                "    \"left\": 5.1,  \n" +
                "    \"right\": 0  \n" +
                "}"
            ))
            .andExpect(status().isBadRequest()
        );
    }
}
