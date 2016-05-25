package com.github.apycazo.spring_rest_forge.base.domains;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
* @author Andres Picazo
*/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(value = {"success", "timestamp", "message", "data"})
public class Outcome
{
    private boolean success = true;
    private long timestamp = System.currentTimeMillis();
    private String message = null;
    private Object data = null;

    public Outcome()
    {
        // empty constructor
    }

    public Outcome(boolean success)
    {
        this(success, null, null);
    }

    public Outcome(boolean success, String msg)
    {
        this(success, msg, null);
    }

    public Outcome(boolean success, String msg, Object data)
    {
        this.success = success;
        this.message = msg;
        this.data = data;
    }

    public ResponseEntity<Object> asResponseEntity()
    {
        return new ResponseEntity<>(this, HttpStatus.OK);
    }

    public ResponseEntity<Object> asResponseEntity(HttpStatus status)
    {
        return new ResponseEntity<>(this, status);
    }

    public static Outcome success()
    {
        return new Outcome(true);
    }

    public static Outcome failure(String msg)
    {
        return new Outcome(false, msg);
    }

    public static Outcome exception(Exception e)
    {
        return exception("An exception has been thrown", e);
    }

    public static Outcome exception(String msg, Exception e)
    {
        Outcome outcome = new Outcome(false, msg);
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("exceptionName", e.getClass().getName());
        map.put("exceptionMessage", StringUtils.isEmpty(e.getMessage()) ? "message not available" : e.getMessage());
        try {
            StackTraceElement ste = e.getStackTrace()[0];
            String location = String.format("%s->%s:%d", ste.getClassName(), ste.getMethodName(), ste.getLineNumber());
            map.put("location", location);
        } catch (Exception e2) {
            map.put("location", "Unknown, stack not available");
        }
        outcome.data = map;
        return outcome;
    }
}

