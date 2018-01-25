package net.nhatle.nshop.view.search;

import net.nhatle.nshop.data.model.Product;

import java.util.List;

/**
 * Created by NhatLe on 30-Dec-17.
 */

public interface ISearchView {
    void displayProductList(List<Product> productList);
}
