package com.hishd.easycart.model;

public class CartItem  {
    ProductItem productItem;
    int qty;

    public CartItem(ProductItem productItem, int qty) {
        this.productItem = productItem;
        this.qty = qty;
    }

    public ProductItem getProductItem() {
        return productItem;
    }

    public void setProductItem(ProductItem productItem) {
        this.productItem = productItem;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
