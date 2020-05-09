package com.hishd.easycart.model;

import com.hishd.tinycart.model.Item;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductItem implements Item, Serializable {

    private String productName;
    private BigDecimal productPrice;
    private String productDescription;
    private String productImage;
    private int imgDrawable;

    public ProductItem(){
        super();
    }

    public ProductItem(String productName, BigDecimal productPrice, String productDescription, String productImage) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.productImage = productImage;
    }

    public ProductItem(String productName, BigDecimal productPrice, String productDescription, int imgDrawable) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.productImage = productImage;
        this.imgDrawable = imgDrawable;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    @Override
    public BigDecimal getItemPrice() {
        return productPrice;
    }

    @Override
    public String getItemName() {
        return productName;
    }

    public int getImgDrawable() {
        return imgDrawable;
    }

    public void setImgDrawable(int imgDrawable) {
        this.imgDrawable = imgDrawable;
    }
}
