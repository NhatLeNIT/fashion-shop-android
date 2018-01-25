package net.nhatle.nshop.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by NhatLe on 29-Dec-17.
 */

public class CartDB extends SQLiteOpenHelper {
    static String table = "cart";
    static String product_id = "product_id";
    static String name = "name";
    static String price = "price";
    static String image = "image";
    static String quantity = "quantity";
    static String quantity_available = "quantity_available";

    public CartDB(Context context) {
        super(context, "product", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String tableCart = "CREATE TABLE " + table + " (" + product_id + " INTEGER PRIMARY KEY, " + name + " TEXT, " + price + " REAL, " + image + " BLOB, " + quantity + " INTEGER, " + quantity_available + " INTEGER)";
        sqLiteDatabase.execSQL(tableCart);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
