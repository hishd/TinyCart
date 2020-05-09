package com.hishd.tinycart.model;

import java.math.BigDecimal;

//The Interface should be implemented before performing any operations
//Usage : Class [Classname] implements Item
public interface Item {
    BigDecimal getItemPrice();

    String getItemName();
}
