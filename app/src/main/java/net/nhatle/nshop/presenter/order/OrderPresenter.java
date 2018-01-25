package net.nhatle.nshop.presenter.order;

import android.content.Context;

import net.nhatle.nshop.data.api.OrderApi;
import net.nhatle.nshop.data.local.CartModel;
import net.nhatle.nshop.data.model.Order;
import net.nhatle.nshop.data.model.Product;
import net.nhatle.nshop.view.order.IOrderView;

import org.json.JSONException;

import java.util.List;

/**
 * Created by NhatLe on 30-Dec-17.
 */

public class OrderPresenter implements IOrderPresenter {
    private IOrderView orderView;
    private OrderApi orderApi;
    private CartModel cartModel;
    private Context context;
    public OrderPresenter(IOrderView orderView, Context context) {
        this.orderView = orderView;
        orderApi = new OrderApi();
        cartModel = new CartModel();
        this.context = context;
    }


    @Override
    public void addOrder(Order order) throws JSONException {
        boolean orderResult = orderApi.addOrder(order);
        if (orderResult){
            cartModel.openConnect(context);
            List<Product> productList =  cartModel.selectAll();
            int length = productList.size();
            for (int i = 0; i < length; i++) {
                cartModel.removeItem(productList.get(i).getId());
            }

            this.orderView.addOrderSuccess();
        }
        else this.orderView.addOrderFail();
    }
}
