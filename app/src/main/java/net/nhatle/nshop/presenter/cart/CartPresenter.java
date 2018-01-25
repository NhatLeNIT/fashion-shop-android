package net.nhatle.nshop.presenter.cart;

import android.content.Context;

import net.nhatle.nshop.data.local.CartModel;
import net.nhatle.nshop.data.model.Product;
import net.nhatle.nshop.view.cart.ICartView;

import java.util.List;

/**
 * Created by NhatLe on 29-Dec-17.
 */

public class CartPresenter implements ICartPresenter {
    private ICartView cartView;
    private CartModel cartModel;

    public CartPresenter(ICartView cartView) {
        this.cartView = cartView;
        this.cartModel = new CartModel();
    }

    @Override
    public void getProductList(Context context) {
        cartModel.openConnect(context);
        List<Product> productList = cartModel.selectAll();
        if(productList.size() > 0 ){
            cartView.displayProduct(productList);
        }
    }
}
