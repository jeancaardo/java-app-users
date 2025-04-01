package com.example.java_app_users.infrastructure.exception;

public class InvalidSortParamException extends RuntimeException{
    public InvalidSortParamException(String value, Boolean isField) {
        super(buildMessage(value, isField));
    }

    private static String buildMessage(String value, Boolean isField) {
        if (isField) {
            return "Field with value " + value + " is not valid. Try with: name, email or createdAt";
        } else {
            return "Sort with value " + value + " is not valid. Try with: asc or desc";
        }
    }
}
