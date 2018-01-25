package net.nhatle.nshop.presenter.product;

import android.content.Context;

import net.nhatle.nshop.data.model.Product;

/**
 * Created by NhatLe on 27-Dec-17.
 */

public interface IProductPresenter {
    void getProduct(int id);
    void getProductSameCategory(int categoryId);
    void addCart(Product product, Context context);
}
