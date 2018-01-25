package net.nhatle.nshop.view.home.fragment;

import net.nhatle.nshop.data.model.Product;

import java.util.List;

/**
 * Created by NhatLe on 26-Dec-17.
 */

public interface IProductListView {
    /**
     * Hiển thị danh sách sản phẩm lên fragment
     * @param productList
     */
    void displayListProduct(List<Product> productList);
}
