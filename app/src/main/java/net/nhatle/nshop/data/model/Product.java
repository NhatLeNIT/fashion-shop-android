package net.nhatle.nshop.data.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NhatLe on 26-Dec-17.
 */

public class Product {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("price")
    @Expose
    private int price;
    @SerializedName("quantity")
    @Expose
    private int quantity;
    @SerializedName("promotion")
    @Expose
    private int promotion;
    @SerializedName("hot_product")
    @Expose
    private int hotProduct;
    @SerializedName("view")
    @Expose
    private int view;
    @SerializedName("count_sell")
    @Expose
    private int countSell;
    @SerializedName("id_cate")
    @Expose
    private int idCate;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("comments")
    @Expose
    private List<Comment> comments = null;

    private byte[] imageCart;
    private int quantityBuy;

    public int getQuantityBuy() {
        return quantityBuy;
    }

    public void setQuantityBuy(int quantityBuy) {
        this.quantityBuy = quantityBuy;
    }

    public byte[] getImageCart() {
        return imageCart;
    }

    public void setImageCart(byte[] imageCart) {
        this.imageCart = imageCart;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPromotion() {
        return promotion;
    }

    public void setPromotion(int promotion) {
        this.promotion = promotion;
    }

    public int getHotProduct() {
        return hotProduct;
    }

    public void setHotProduct(int hotProduct) {
        this.hotProduct = hotProduct;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getCountSell() {
        return countSell;
    }

    public void setCountSell(int countSell) {
        this.countSell = countSell;
    }

    public int getIdCate() {
        return idCate;
    }

    public void setIdCate(int idCate) {
        this.idCate = idCate;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public static Product getProduct(JSONObject jsonObject) {
        return new Gson().fromJson(jsonObject.toString(), Product.class);
    }

    public static List<Product> getProducts(JSONArray jsonArray) throws JSONException {
        List<Product> arrayList = new ArrayList<>();
        int length = jsonArray.length();
        for (int i = 0; i < length; i++) {
            arrayList.add(getProduct(jsonArray.getJSONObject(i)));
        }
        return arrayList;
    }
}
