package net.nhatle.nshop.data.api;

import net.nhatle.nshop.connect.DownloadJSON;
import net.nhatle.nshop.data.model.Category;
import net.nhatle.nshop.connect.Config;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by NhatLe on 25-Dec-17.
 */

public class MenuApi {
    /**
     * Parse JSON sang list object category
     * @param data
     * @return
     */
    public List<Category> ParseJSONMenu(String data) {
        List<Category> categoryList = new ArrayList<>();
        try {
            JSONArray jsonArrayCategory = new JSONArray(data);
            categoryList.addAll(Category.getCategories(jsonArrayCategory));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return categoryList;
    }

    /**
     * Get danh sách category dựa theo category parent id
     * @param parent_id
     * @return
     */
    public List<Category> getListWithParentId(int parent_id) {
        List<Category> categoryList = new ArrayList<>();
        String link = Config.BASE_URL + "category?parent_id=" + parent_id;
        DownloadJSON downloadJSON = new DownloadJSON(link);
        downloadJSON.execute();
        try {
            String DataJSON = downloadJSON.get();
            MenuApi menuApi = new MenuApi();
            categoryList = menuApi.ParseJSONMenu(DataJSON);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return categoryList;
    }
}
