package com.revature.erm.util.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super("No resource found using the provided search parameters!");

//    public ResourceNotFoundException(String message) {
//        super (message);
//        }

    }
}
