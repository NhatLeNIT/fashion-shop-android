package net.nhatle.nshop.presenter.home;

import net.nhatle.nshop.data.api.ProductApi;
import net.nhatle.nshop.data.model.Product;
import net.nhatle.nshop.view.home.fragment.IProductListView;

import java.util.List;

/**
 * Created by NhatLe on 26-Dec-17.
 */

public class HomeProductPresenter implements IHomeProductPresenter {
    private IProductListView productListView;
    private ProductApi productApi;


    public HomeProductPresenter(IProductListView productListView) {
        this.productListView = productListView;
        productApi = new ProductApi();
    }

    @Override
    public void getPopularProductList() {
        List<Product> popularProductList = productApi.getPopularProductList();
        productListView.displayListProduct(popularProductList);
    }

    @Override
    public void getSalesProductList() {
        List<Product> salesProductList = productApi.getSalesProductList();
        productListView.displayListProduct(salesProductList);
    }

    @Override
    public void getRandomProductList() {
        List<Product> randomProductList = productApi.getRandomProductList();
        productListView.displayListProduct(randomProductList);
    }

    @Override
    public void getPromotionProductList() {
        List<Product> promotionProductList = productApi.getPromotionProductList();
        productListView.displayListProduct(promotionProductList);
    }
}
