package com.myntra.core.business;

public class WishList extends BusinessFlow {
    public static WishList getInstance() {
        return (WishList) getInstance(Of.WISHLIST);
    }
}
