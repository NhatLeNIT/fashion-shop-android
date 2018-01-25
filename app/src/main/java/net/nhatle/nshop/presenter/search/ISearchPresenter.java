package net.nhatle.nshop.presenter.search;

import org.json.JSONException;

/**
 * Created by NhatLe on 30-Dec-17.
 */

public interface ISearchPresenter {
    void getProductBySearch(String keyword) throws JSONException;
}
