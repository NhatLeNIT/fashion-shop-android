package net.nhatle.nshop.data.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import net.nhatle.nshop.connect.Config;
import net.nhatle.nshop.connect.DownloadJSON;
import net.nhatle.nshop.data.model.Category;
import net.nhatle.nshop.data.model.Customer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by NhatLe on 26-Dec-17.
 */

public class AuthApi {
    public boolean Register(Customer customer) {

        String link = Config.BASE_URL + "register";

        //Create JSONObject here
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("name", customer.getName());
            jsonParam.put("email", customer.getEmail());
            jsonParam.put("password", customer.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DownloadJSON downloadJSON = new DownloadJSON(link, jsonParam);
        downloadJSON.execute();
        Boolean result = false;

        try {
            String dataJSON = downloadJSON.get();
            JSONObject jsonObject = new JSONObject(dataJSON);
            result = jsonObject.getBoolean("result");

        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    public Customer Login(Context context, String email, String password) {
        String link = Config.BASE_URL + "login";

        //Create JSONObject here
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("email", email);
            jsonParam.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        DownloadJSON downloadJSON = new DownloadJSON(link, jsonParam);
        downloadJSON.execute();
        Customer customer = null;
        try {
            String dataJSON = downloadJSON.get();
            JSONObject jsonObject = new JSONObject(dataJSON);

            customer = new Gson().fromJson(jsonObject.getString("user"),Customer.class);

        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }

//        Luu cache login
        if (customer != null) {
            updateCacheLogin(context, customer);
        }


        return customer;
    }

    public Customer getCacheLogin(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        int customerId = sharedPreferences.getInt("id", 0);
        String customerName = sharedPreferences.getString("name", "");
        String customerEmail = sharedPreferences.getString("email", "");

        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setName(customerName);
        customer.setEmail(customerEmail);
//        Log.d("111", customer.getId() + " - " + customer.getName());
        return customer;
    }

    public void updateCacheLogin(Context context, Customer customer) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("id", customer.getId());
        editor.putString("name", customer.getName());
        editor.putString("email", customer.getEmail());
        editor.apply();
    }

    public boolean resetPassword(String email) throws JSONException {
        String link = Config.BASE_URL + "reset";
        JSONObject jsonParams = new JSONObject();
        jsonParams.put("email", email);
        DownloadJSON downloadJSON = new DownloadJSON(link, jsonParams);
        downloadJSON.execute();
        boolean result = false;
        try {
            String dataJSON  = downloadJSON.get();
            JSONObject jsonObject = new JSONObject(dataJSON);
            result = jsonObject.getBoolean("result");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return result;
    }
}
