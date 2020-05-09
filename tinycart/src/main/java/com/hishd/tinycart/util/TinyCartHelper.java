package com.hishd.tinycart.util;

import com.hishd.tinycart.model.Cart;

//Class which returns a static Cart
//Usage: call getCart() to retrieve the cart anywhere
public class TinyCartHelper {
    private static Cart cart = new Cart();

    //Usage: Retrieves the cart anywhere by calling the method. Make sure you retrieve before performing any cart operations
    public static Cart getCart() {
        if (cart == null) {
            cart = new Cart();
        }
        return cart;
    }
}
