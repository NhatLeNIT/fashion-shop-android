package net.nhatle.nshop.data.api;

import android.util.Log;

import net.nhatle.nshop.connect.Config;
import net.nhatle.nshop.connect.DownloadJSON;
import net.nhatle.nshop.data.model.Comment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by NhatLe on 29-Dec-17.
 */

public class CommentApi {
    public boolean postComment(Comment comment) {
        String link = Config.BASE_URL + "comment";

        //Create JSONObject here
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("product_id", comment.getIdProduct());
            jsonParam.put("content", comment.getContent());
            jsonParam.put("user_id", comment.getIdUser());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        DownloadJSON downloadJSON = new DownloadJSON(link, jsonParam);
        downloadJSON.execute();
        boolean result = false;
        try {
            String dataJSON = downloadJSON.get();
            JSONObject jsonObject = new JSONObject(dataJSON);
            result = jsonObject.getBoolean("result");

        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }

        return result;
    }
}
