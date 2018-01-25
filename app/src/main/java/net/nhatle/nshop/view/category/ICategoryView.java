package net.nhatle.nshop.view.category;

import net.nhatle.nshop.data.model.Category;
import net.nhatle.nshop.data.model.Product;

import java.util.List;

/**
 * Created by NhatLe on 27-Dec-17.
 */

public interface ICategoryView {
    void displayProductList(List<Product> productList);
}
