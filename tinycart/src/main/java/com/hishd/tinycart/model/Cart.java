package com.hishd.tinycart.model;

import androidx.annotation.NonNull;

import com.hishd.tinycart.exceptions.ProductNotFoundException;
import com.hishd.tinycart.exceptions.QuantityInvalidException;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Cart implements Serializable {
    private static final long serialVersionUID = 20L;
    private BigDecimal totalPrice = BigDecimal.ZERO;
    private int totalQty = 0;
    //Item to hold the Item and Integer to hold the quantity
    private Map<Item, Integer> cartItemMap = new HashMap<>();

    //Add a new item to the cart
    //Usage: call addItem(@param Item, @param qty)
    // item = The item type object which has several properties after implemented
    // qty = quantity of the corresponding item
    public void addItem(final Item item, final int qty) {
        if (cartItemMap.containsKey(item))
            cartItemMap.put(item, cartItemMap.get(item) + qty);
        else
            cartItemMap.put(item, qty);

        totalPrice = totalPrice.add(item.getItemPrice().multiply(BigDecimal.valueOf(qty)));
        totalQty += qty;
    }

    //Update an existing item the the class
    //Usage call updateItem(@param item, @param qty)
    //item = The item type object which has several properties after implemented
    //qty = the new quantity of the item
    //@throws QuantityInvalidException if the passed quantity is not valid
    //@throws ProductNotFoundException if the product does not exist in the cart
    public void updateItem(final Item item, final int qty) throws QuantityInvalidException, ProductNotFoundException {
        if (qty < 1) {
            throw new QuantityInvalidException();
        }
        if (!cartItemMap.containsKey(item)) {
            throw new ProductNotFoundException();
        }

        int prevQty = cartItemMap.get(item);
        BigDecimal totalItemPrice = item.getItemPrice().multiply(BigDecimal.valueOf(prevQty));
        cartItemMap.put(item, qty);
        totalQty = totalQty - prevQty + qty;
        totalPrice = totalPrice.subtract(totalItemPrice).add(item.getItemPrice().multiply(BigDecimal.valueOf(qty)));
    }

    //Removes quantity of a certain item
    //item = The item type object which has several properties after implemented
    //qty = the desired quantity of the item which will be removed
    //@throws QuantityInvalidException if the passed quantity is not valid
    //@throws ProductNotFoundException if the product does not exist in the cart
    public void removeItemQty(final Item item, final int qty) throws QuantityInvalidException, ProductNotFoundException {
        if (!cartItemMap.containsKey(item)) {
            throw new ProductNotFoundException();
        }
        int prevQty = cartItemMap.get(item);
        if (qty > prevQty || qty < 1) {
            throw new QuantityInvalidException("Invalid Item Quantity, the quantity should be greater than zero and the previous quantity of the item.");
        }

        if (prevQty == qty) {
            cartItemMap.remove(item);
        } else {
            cartItemMap.put(item, prevQty - qty);
        }

        totalPrice = totalPrice.subtract(item.getItemPrice().multiply(BigDecimal.valueOf(qty)));
        totalQty -= qty;
    }

    //Removes an item in the cart
    //item = The item type object which has several properties after implemented
    //@throws ProductNotFoundException if the product does not exist in the cart
    public void removeItem(final Item item) throws ProductNotFoundException {
        if (!cartItemMap.containsKey(item)) {
            throw new ProductNotFoundException();
        }
        int prevQty = cartItemMap.get(item);
        cartItemMap.remove(item);
        totalPrice = totalPrice.subtract(item.getItemPrice().multiply(BigDecimal.valueOf(prevQty)));
        totalQty -= prevQty;
    }

    //Removes all the products and clear the cart
    public void clearCart() {
        cartItemMap.clear();
        totalPrice = BigDecimal.ZERO;
        totalQty = 0;
    }

    //Checks and returns if the cart is empty
    public boolean isCartEmpty() {
        return cartItemMap.isEmpty();
    }

    //Retrieves the quantity of a particular item
    //item = The item type object which has several properties after implemented
    //@throws ProductNotFoundException if the product does not exist in the cart
    public int getItemQty(final Item item) throws ProductNotFoundException {
        if (!cartItemMap.containsKey(item)) {
            throw new ProductNotFoundException();
        }

        return cartItemMap.get(item);
    }

    //Retrieve the total price of the cart
    public BigDecimal getTotalPrice(){
        return totalPrice;
    }

    //Retrieves all the Item names
    public Set<Item> getItemNames(){
        return cartItemMap.keySet();
    }

    //Retrieves all the items in the cart along with their quantity as Map
    public Map<Item,Integer> getAllItemsWithQty(){
        return cartItemMap;
    }

    //Returns a concatenated string which contains the cart information
    @NonNull
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(Map.Entry<Item,Integer> entry:cartItemMap.entrySet()){
            stringBuilder.append(String.format("Item Name : %s , Value : %f , Quantity : %d%n",entry.getKey().getItemName(),entry.getKey().getItemPrice(),entry.getValue()));
        }

        stringBuilder.append(String.format("Total QTY : %d , Total Price : %f",totalQty,totalPrice));
        return stringBuilder.toString();
    }
}
