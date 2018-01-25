package net.nhatle.nshop.data.api;

import android.util.Log;

import net.nhatle.nshop.connect.Config;
import net.nhatle.nshop.connect.DownloadJSON;
import net.nhatle.nshop.data.model.Order;
import net.nhatle.nshop.data.model.OrderDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by NhatLe on 30-Dec-17.
 */

public class OrderApi {
    public boolean addOrder(Order order) throws JSONException {
        String link = Config.BASE_URL + "order";

        List<OrderDetail> orderDetailList = order.getDetails();
        JSONArray jsonArrayDetail = new JSONArray();

        int length = orderDetailList.size();
        for (int i = 0; i < length; i++) {
            JSONObject jsonObjectDetail = new JSONObject();
            jsonObjectDetail.put("product_id", orderDetailList.get(i).getIdProduct());
            jsonObjectDetail.put("quantity", orderDetailList.get(i).getQuantity());
            jsonObjectDetail.put("price", orderDetailList.get(i).getPrice());

            jsonArrayDetail.put(jsonObjectDetail);
        }
        //Create JSONObject here
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("user_id", order.getIdUser());
            jsonParam.put("receiver_name", order.getReceiverName());
            jsonParam.put("receiver_phone", order.getReceiverPhone());
            jsonParam.put("receiver_address", order.getReceiverAddress());
            jsonParam.put("note", order.getNote());
            jsonParam.put("details", jsonArrayDetail);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("111", jsonParam.toString());
        DownloadJSON downloadJSON = new DownloadJSON(link, jsonParam);
        downloadJSON.execute();
        boolean result = false;
        try {
            String dataJSON = downloadJSON.get();
//            Log.d("111", dataJSON);
            JSONObject jsonObject = new JSONObject(dataJSON);
            result = jsonObject.getBoolean("result");

        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }

        return result;
    }
}
