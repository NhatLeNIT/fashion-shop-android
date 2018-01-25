package net.nhatle.nshop.presenter.order;

import net.nhatle.nshop.data.model.Order;

import org.json.JSONException;

/**
 * Created by NhatLe on 30-Dec-17.
 */

public interface IOrderPresenter {
    void addOrder(Order order) throws JSONException;
}
