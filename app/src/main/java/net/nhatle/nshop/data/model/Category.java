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
 * Created by NhatLe on 25-Dec-17.
 */

public class Category {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id_parent")
    @Expose
    private Integer idParent;
    @SerializedName("status")
    @Expose
    private Integer status;
    private List<Category> categoryListChild;

    public List<Category> getCategoryListChild() {
        return categoryListChild;
    }

    public void setCategoryListChild(List<Category> categoryListChild) {
        this.categoryListChild = categoryListChild;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIdParent() {
        return idParent;
    }

    public void setIdParent(Integer idParent) {
        this.idParent = idParent;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    private static Category getCategory (JSONObject jsonObject){
        return new Gson().fromJson(jsonObject.toString(),Category.class);
    }
    public static List<Category> getCategories(JSONArray jsonArray) throws JSONException {
        List<Category> arrayList = new ArrayList<>();
        int length = jsonArray.length();
        for(int i = 0; i < length; i++) {
            arrayList.add(getCategory(jsonArray.getJSONObject(i)));
        }
        return arrayList;
    }
}
