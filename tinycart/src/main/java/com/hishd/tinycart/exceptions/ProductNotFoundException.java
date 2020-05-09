package com.hishd.tinycart.exceptions;

//An exception which will throws while the requested product is not th the cart.
public class ProductNotFoundException extends RuntimeException {
    //Class version for serialization
    private static final long serialVersionUID = 20L;
    private static final String DEFAULT_MESSAGE = "The Product is not found inside the cart.";

    public ProductNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}
