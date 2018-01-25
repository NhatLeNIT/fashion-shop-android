package net.nhatle.nshop.presenter.search;

import android.util.Log;

import net.nhatle.nshop.data.api.ProductApi;
import net.nhatle.nshop.data.model.Product;
import net.nhatle.nshop.view.search.ISearchView;

import org.json.JSONException;

import java.util.List;

/**
 * Created by NhatLe on 30-Dec-17.
 */

public class SearchPresenter implements ISearchPresenter {


    private ISearchView searchView;
    private ProductApi productApi;
    public SearchPresenter(ISearchView searchView) {
        this.searchView = searchView;
        productApi = new ProductApi();
    }
    @Override
    public void getProductBySearch(String keyword) throws JSONException {

        List<Product> productList = productApi.getProductBySearch(keyword, 20);
        searchView.displayProductList(productList);
    }
}
