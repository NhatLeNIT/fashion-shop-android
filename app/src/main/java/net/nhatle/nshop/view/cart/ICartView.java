package net.nhatle.nshop.view.cart;

import net.nhatle.nshop.data.model.Product;

import java.util.List;

/**
 * Created by NhatLe on 29-Dec-17.
 */

public interface ICartView {
    void displayProduct(List<Product> productList);
}
