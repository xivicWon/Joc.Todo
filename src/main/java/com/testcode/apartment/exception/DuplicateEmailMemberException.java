package com.testcode.apartment.exception;

public class DuplicateEmailMemberException extends MemberException {
    public DuplicateEmailMemberException(String message) {
        super(message);
    }

    public DuplicateEmailMemberException(String message, Throwable cause) {
        super(message, cause);
    }
}
