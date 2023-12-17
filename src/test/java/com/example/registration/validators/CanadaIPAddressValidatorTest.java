package com.example.registration.validators;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.AssertionErrors;

class CanadaIPAddressValidatorTest {

    @Test
    void isValid_test() {
        CanadaIPAddressValidator canadaIPAddressValidator = new CanadaIPAddressValidator();

        AssertionErrors.assertTrue("The IP 123.123.123.123 must be valid",
                canadaIPAddressValidator.isValid("123.123.123.123", null));
        AssertionErrors.assertTrue("The IP 0.0.0.0 must be valid",
                canadaIPAddressValidator.isValid("0.0.0.0", null));

        AssertionErrors.assertFalse("The IP 1234.123.123.123 must be invalid",
                canadaIPAddressValidator.isValid("1234.123.123.123", null));
        AssertionErrors.assertFalse("The IP 123.123.123.123. must be invalid",
                canadaIPAddressValidator.isValid("123.123.123.123.", null));
        AssertionErrors.assertFalse("The IP 123.123.123.256. must be invalid",
                canadaIPAddressValidator.isValid("123.123.123.256", null));
        AssertionErrors.assertFalse("The IP 12o.123.123.256. must be invalid",
                canadaIPAddressValidator.isValid("12o.123.123.256", null));
        AssertionErrors.assertFalse("The IP 2001:0db8:3c4d:0015:0000:0000:1a2f:1a2b must be invalid",
                canadaIPAddressValidator.isValid("2001:0db8:3c4d:0015:0000:0000:1a2f:1a2b", null));
    }
}
