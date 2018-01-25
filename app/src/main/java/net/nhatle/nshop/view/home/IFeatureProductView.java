package net.nhatle.nshop.view.home;

import net.nhatle.nshop.data.model.Product;

import java.util.List;

/**
 * Created by NhatLe on 26-Dec-17.
 */

public interface IFeatureProductView {
    /**
     * Hiển thị các danh sách sản phẩm lên các fragment tương ứng
     * @param menProductList
     * @param womenProductList
     * @param childrenProductList
     * @param shoesProductList
     */
    void displayListProduct(List<Product> menProductList, List<Product> womenProductList, List<Product> childrenProductList, List<Product> shoesProductList);
}
