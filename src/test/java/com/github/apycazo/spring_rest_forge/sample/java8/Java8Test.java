package com.github.apycazo.spring_rest_forge.sample.java8;

import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * This is just a test on how java 8 stream api, lambdas and such behaves.
 * @author Andres Picazo
 */
public class Java8Test
{

    // Wrapper to keep results, only as a help to showcase the examples.
    @Data
    private class Result<T> {
        T value;
    }

    @Test
    public void testOptionalBehaviour ()
    {
        String str = "string";
        String emptyStr = "";
        String nullStr = null;

        String defaultValue = "str is null";

        String result;
        result = Optional.ofNullable(nullStr).orElse(defaultValue);
        Assert.assertEquals(defaultValue, result);
        // Logs: Result (nullString) = 'str is null'

        result = Optional.ofNullable(emptyStr).orElse(defaultValue);
        Assert.assertEquals(emptyStr, result);
        // Logs: Result (emptyString) = ''

        result = Optional.ofNullable(str).orElse(defaultValue);
        Assert.assertEquals(str, result);
        // Logs: Result (validString) = 'string'

        result = Optional.of(emptyStr).orElse(defaultValue);
        Assert.assertEquals(emptyStr, result);
        // Logs: Result (emptyString, not nullable) = ''
    }

    @Test
    public void forEachOnList ()
    {
        // Creates a sample list to do the test
        List<Integer> list = new LinkedList<>();
        for (int i = 0; i <10; i++) {
            list.add(i);
        }
        // Wrap the result because inner lambda values must be final or effectively final
        Result<Integer> result = new Result<>();
        result.value = 0;
        list.forEach(element -> result.value += element);
        // Result should be 0+1+2+3+4+5+6+7+8+9
        Assert.assertTrue(result.value.equals(45));
    }

    @Test
    public void predicateBehaviour ()
    {
        Predicate<Integer> isOdd = (x) -> x % 2 == 0;

        Assert.assertTrue(isOdd.test(4));
        Assert.assertFalse(isOdd.test(3));
    }

    @Test
    public void functionBehaviour ()
    {
        String string = " str ";

        Function<String, Integer> trimmedLength = (x) -> {
            if (x == null) return 0;
            else return x.trim().length();
        };

        Assert.assertEquals(5, string.length());
        Assert.assertTrue(3 == trimmedLength.apply(string));
    }

    @Test
    public void consumerBehaviour ()
    {
        Result<Integer> result = new Result<>();
        result.value = 0;
        Consumer<Result<Integer>> consumer = (x) -> x.value++ ;

        consumer.accept(result);
        Assert.assertTrue(1 == result.getValue());
    }

    @Test
    public void mapReduceBehaviour ()
    {
        List<String> strings = new LinkedList<String>() {{
            add("12345");
            add("1234567");
            add("123456");
        }};

        // get the longest string size
        int longest = strings.stream()
                // map values by string length
                .map(String::length) // shorthand for: .map((s) -> s.length())
                // initial value is 0, if next one is larger, return it instead
                .reduce(0, (a,b) -> a >= b ? a : b);

        Assert.assertTrue(7 == longest);
    }

    @Test
    public void filterAndCollect ()
    {

        List<String> strings = new LinkedList<String>() {{
            add("12345");
            add("123456789");
            add("123456");
            add("123456789");
        }};

        // Collect strings larger than 6.
        List<String> results = strings.stream()
                .filter((s) -> s.length() > 6)
                .collect(Collectors.toList());

        Assert.assertEquals(2, results.size());
        Assert.assertEquals("123456789", results.get(0));
        Assert.assertEquals("123456789", results.get(1));
    }

}
