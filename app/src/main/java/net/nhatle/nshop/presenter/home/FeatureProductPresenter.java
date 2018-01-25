package net.nhatle.nshop.presenter.home;

import android.util.Log;

import net.nhatle.nshop.data.api.ProductApi;
import net.nhatle.nshop.data.model.Product;
import net.nhatle.nshop.view.home.IFeatureProductView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NhatLe on 26-Dec-17.
 */

public class FeatureProductPresenter implements IFeatureProductPresenter {
    private IFeatureProductView featureProductView;
    private ProductApi productApi;

    public FeatureProductPresenter(IFeatureProductView featureProductView) {
        this.featureProductView = featureProductView;
        productApi = new ProductApi();
    }

    @Override
    public void getListFeatureProduct() {
        List<Product> menProductList = productApi.getProductListByCategoryId(1, 6);
        List<Product> womenProductList = productApi.getProductListByCategoryId(11, 6);
        List<Product> childrenProductList = productApi.getProductListByCategoryId(31, 6);
        List<Product> shoesProductList = productApi.getProductListByCategoryId(49, 6);

        featureProductView.displayListProduct(menProductList, womenProductList, childrenProductList, shoesProductList);
    }
}
