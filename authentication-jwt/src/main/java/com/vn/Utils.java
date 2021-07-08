package com.vn;

import org.springframework.validation.FieldError;

import java.lang.reflect.Field;
import java.util.*;

public class Utils {

    public static Map<String, Object> buildResponse(int status, String message, Object data, List<FieldError> errorList) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("message", message);
        response.put("data", data);
        if (errorList != null && !errorList.isEmpty()) {
            List<Map<String, Object>> list = new ArrayList<>();
            errorList.forEach(value -> {
                Map<String, Object> map = new HashMap<>();
                map.put("field", value.getField());
                map.put("message", value.getDefaultMessage());
                list.add(map);
            });
            response.put("fieldErrors", list);
        }
        return response;
    }

    public static Map<String, Object> buildExcludes(Object data, String[] field) throws IllegalAccessException {
        Map<String, Object> response = new HashMap<>();
        List<String> stringList = Arrays.asList(field);
        for (Field item : data.getClass().getDeclaredFields()) {
            item.setAccessible(true);
            if (!stringList.contains(item.getName()))
                response.put(item.getName(), item.get(data));
        }
        return response;
    }
}
