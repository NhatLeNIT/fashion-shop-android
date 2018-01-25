package net.nhatle.nshop.presenter.category;

import net.nhatle.nshop.data.api.ProductApi;
import net.nhatle.nshop.data.model.Product;
import net.nhatle.nshop.view.category.ICategoryView;

import java.util.List;

/**
 * Created by NhatLe on 27-Dec-17.
 */

public class CategoryPresenter implements ICategoryPresenter {
    private ICategoryView categoryView;
    private ProductApi productApi;

    public CategoryPresenter(ICategoryView categoryView) {
        this.categoryView = categoryView;
        productApi = new ProductApi();
    }

    @Override
    public void getProductByCategoryId(int categoryId) {
        List<Product> productList = productApi.getProductListByCategoryId(categoryId, 20);
        categoryView.displayProductList(productList);
    }
}
