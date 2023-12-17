package com.example.registration.validators;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.AssertionErrors;

@Slf4j
class StrongPasswordValidatorTest {

    @Test
    void isValid_test() {
        StrongPasswordValidator validator = new StrongPasswordValidator();

        log.info("Testing the following password validation rules:");
        log.info("Test Case: 1 - The string must contain at least one uppercase letter.");
        log.info("Test Case: 2 - The string must contain at least one digit.");
        log.info("Test Case: 3 - The string must contain at least one special character from the set _, #, $, %, .");
        log.info("Test Case: 4 - The string must be at least 8 characters long.");


        // Negative Password Tests:
        //Satisfying Only Test Case 1: "A"
        AssertionErrors.assertFalse("Failed to validate Test Case 1 of password validation rules.",
                validator.isValid("A", null));

        //Satisfying Only Test Case 2: "1"
        AssertionErrors.assertFalse("Failed to validate Test Case 2 of password validation rules.",
                validator.isValid("1", null));

        //Satisfying Only Test Case 3: "#"
        AssertionErrors.assertFalse("Failed to validate Test Case 3 of password validation rules.",
                validator.isValid("#", null));

        //Satisfying Only Test Case 4: "**********"
        AssertionErrors.assertFalse("Failed to validate Test Case 4 of password validation rules.",
                validator.isValid("**********", null));

        //Satisfying Only Test Cases 1,2: "A1"
        AssertionErrors.assertFalse("Failed to validate Test Cases 1 & 2 of password validation rules.",
                validator.isValid("A1", null));

        //Satisfying Only Test Cases 1,3: "A#"
        AssertionErrors.assertFalse("Failed to validate Test Cases 1 & 3 of password validation rules.",
                validator.isValid("A#", null));

        //Satisfying Only Test Cases 1,2: "1,4: A**********"
        AssertionErrors.assertFalse("Failed to validate Test Cases 1 & 4 of password validation rules.",
                validator.isValid("A**********", null));

        //Satisfying Only Test Cases 2,3: "1#"
        AssertionErrors.assertFalse("Failed to validate Test Cases 2 & 3 of password validation rules.",
                validator.isValid("1#", null));

        //Satisfying Only Test Cases 2,4: "1**********"
        AssertionErrors.assertFalse("Failed to validate Test Cases 2 & 4 of password validation rules.",
                validator.isValid("1**********", null));

        //Satisfying Only Test Cases 3,4: "#**********"
        AssertionErrors.assertFalse("Failed to validate Test Cases 3 & 4 of password validation rules.",
                validator.isValid("#**********", null));


        // Positive Password Tests:
        //Satisfying Only Test Cases 1,2,3,4: "A1234567_"
        AssertionErrors.assertTrue("Failed to validate Test Case 1 of password validation rules.",
                validator.isValid("A1234567_", null));

        //Satisfying Only Test Cases 1,2,3,4: "A1234567_"
        AssertionErrors.assertTrue("Failed to validate Test Case 1 of password validation rules.",
                validator.isValid("A1234567#", null));

        //Satisfying Only Test Cases 1,2,3,4: "A1234567_"
        AssertionErrors.assertTrue("Failed to validate Test Case 1 of password validation rules.",
                validator.isValid("A1234567$", null));

        //Satisfying Only Test Cases 1,2,3,4: "A1234567_"
        AssertionErrors.assertTrue("Failed to validate Test Case 1 of password validation rules.",
                validator.isValid("A1234567%", null));

        //Satisfying Only Test Cases 1,2,3,4: "A1234567_"
        AssertionErrors.assertTrue("Failed to validate Test Case 1 of password validation rules.",
                validator.isValid("A1234567.", null));
    }
}
