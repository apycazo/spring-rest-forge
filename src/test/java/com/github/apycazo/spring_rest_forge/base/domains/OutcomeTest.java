package com.github.apycazo.spring_rest_forge.base.domains;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Basic outcome testing.
 * @author Andres Picazo
 */
public class OutcomeTest
{

    @Test
    public void createSuccessfulOutcome() throws Exception
    {
        Outcome outcome = Outcome.success();
        Assert.assertEquals(true, outcome.isSuccess());
    }

    @Test
    public void createFailedOutcome() throws Exception
    {
        String message = "An error has been detected";
        Outcome outcome = Outcome.failure(message);
        Assert.assertEquals(false, outcome.isSuccess());
        Assert.assertEquals(message, outcome.getMessage());
    }

}