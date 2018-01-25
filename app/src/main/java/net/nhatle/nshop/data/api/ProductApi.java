package net.nhatle.nshop.data.api;

import android.util.Log;

import net.nhatle.nshop.connect.Config;
import net.nhatle.nshop.connect.DownloadJSON;
import net.nhatle.nshop.data.model.Category;
import net.nhatle.nshop.data.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by NhatLe on 26-Dec-17.
 */

public class ProductApi {
    public static List<Product> ParseJSON(String data) {
        List<Product> productList = new ArrayList<>();
        try {
            JSONArray jsonArrayProduct = new JSONArray(data);
            productList.addAll(Product.getProducts(jsonArrayProduct));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return productList;
    }
    public List<Product> getProductListByCategoryId(int categoryId, int limit){
        List<Product> productList = new ArrayList<>();
        String link = Config.BASE_URL + "product-by-category?category_id=" + categoryId + "&limit=" + limit;
        DownloadJSON downloadJSON = new DownloadJSON(link);
        downloadJSON.execute();
        try {
            String dataJSON = downloadJSON.get();
            productList = ProductApi.ParseJSON(dataJSON);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public List<Product> getPopularProductList(){
        List<Product> productList = new ArrayList<>();
        String link = Config.BASE_URL + "product-popular";
        DownloadJSON downloadJSON = new DownloadJSON(link);
        downloadJSON.execute();
        try {
            String dataJSON = downloadJSON.get();
            productList = ProductApi.ParseJSON(dataJSON);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public List<Product> getSalesProductList(){
        List<Product> productList = new ArrayList<>();
        String link = Config.BASE_URL + "product-sales";
        DownloadJSON downloadJSON = new DownloadJSON(link);
        downloadJSON.execute();
        try {
            String dataJSON = downloadJSON.get();
            productList = ProductApi.ParseJSON(dataJSON);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public List<Product> getRandomProductList(){
        List<Product> productList = new ArrayList<>();
        String link = Config.BASE_URL + "product-random";
        DownloadJSON downloadJSON = new DownloadJSON(link);
        downloadJSON.execute();
        try {
            String dataJSON = downloadJSON.get();
            productList = ProductApi.ParseJSON(dataJSON);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public List<Product> getPromotionProductList(){
        List<Product> productList = new ArrayList<>();
        String link = Config.BASE_URL + "product-promotion";
        DownloadJSON downloadJSON = new DownloadJSON(link);
        downloadJSON.execute();

        try {
            String dataJSON = downloadJSON.get();
            productList = ProductApi.ParseJSON(dataJSON);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public Product getProduct(int productId){
        Product product = new Product();
        String link = Config.BASE_URL + "product/" + productId;
        DownloadJSON downloadJSON = new DownloadJSON(link);
        downloadJSON.execute();

        try {
            String dataJSON = downloadJSON.get();
            JSONArray jsonArrayProduct = new JSONArray(dataJSON);
            JSONObject jsonObjectProduct = jsonArrayProduct.getJSONObject(0);
            product = Product.getProduct(jsonObjectProduct);
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
        return product;
    }

    public List<Product> getProductBySearch(String keyword, int limit) throws JSONException {
        List<Product> productList = new ArrayList<>();
        String link = Config.BASE_URL + "search";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("keyword", keyword);
        jsonObject.put("limit", limit);
        DownloadJSON downloadJSON = new DownloadJSON(link, jsonObject);
        downloadJSON.execute();
        try {
            String dataJSON = downloadJSON.get();

            productList = ProductApi.ParseJSON(dataJSON);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return productList;
    }
}
