package com.joc.todo.exception;

// checked Exception 과 unchecked Exception 이 있음.
// RuntimeException 는 unchecked Exception 임. 그래서 툴에서 에러가 발생되지 않음.
public class ApplicationException extends RuntimeException {

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }
}
