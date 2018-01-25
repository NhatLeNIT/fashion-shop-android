package net.nhatle.nshop.presenter.product;


import android.content.Context;
import android.util.Log;

import net.nhatle.nshop.data.api.ProductApi;
import net.nhatle.nshop.data.local.CartModel;
import net.nhatle.nshop.data.model.Product;
import net.nhatle.nshop.view.product.IProductView;

import java.util.List;

/**
 * Created by NhatLe on 27-Dec-17.
 */

public class ProductPresenter implements IProductPresenter {
    private IProductView productView;
    private ProductApi productApi;
    private CartModel cartModel;
    public ProductPresenter() {
        productApi = new ProductApi();
        cartModel = new CartModel();
    }
    public ProductPresenter(IProductView productView) {
        this.productView = productView;
        productApi = new ProductApi();
        cartModel = new CartModel();
    }


    @Override
    public void getProduct(int productId) {
        Product product = productApi.getProduct(productId);
        productView.displayProduct(product);
//        Log.d("111", product.getName());
        productView.displayImage(product.getImages());
        productView.displayComment(product.getComments());
    }

    @Override
    public void getProductSameCategory(int categoryId) {
        List<Product> productList = productApi.getProductListByCategoryId(categoryId, 4);
        productView.displayProductSameCategory(productList);
    }

    @Override
    public void addCart(Product product, Context context) {
        cartModel.openConnect(context);
        boolean result = cartModel.insert(product);
        if(result) {
            productView.addCartSuccess();
        } else {

            productView.addCartFail();
        }
    }

    public int countItemInCart( Context context) {
        cartModel.openConnect(context);
        List<Product> productList = cartModel.selectAll();
        return productList.size();
    }
}
