package com.joc.todo.exception;

public class InvalidUserProblemException extends AuthenticationProblemException {
    public InvalidUserProblemException(String message) {
        super(message);
    }

    public InvalidUserProblemException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidUserProblemException(Throwable cause) {
        super(cause);
    }
}
