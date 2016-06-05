package com.github.apycazo.spring_rest_forge.sample.java8;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Andres Picazo
 */
public class DoubleColonTest
{
    public interface Alterable {
        int alterValue (int value);
    }

    public class Operations {

        public int addOne (int number)
        {
            return number + 1;
        }

        public int minusOne (int number)
        {
            return number - 1;
        }
    }

    @Test
    public void testDoubleColon ()
    {
        int value = 0;

        Operations ops = new Operations();
        Alterable plus = ops::addOne;

        value = plus.alterValue(value);
        Assert.assertTrue(1 == value);

        Alterable minus = ops::minusOne;
        value = minus.alterValue(value);
        Assert.assertTrue(0 == value);
    }
}
