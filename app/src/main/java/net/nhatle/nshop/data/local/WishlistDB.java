package net.nhatle.nshop.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by NhatLe on 29-Dec-17.
 */

public class WishlistDB extends SQLiteOpenHelper {
    static String table = "wishlist";
    static String product_id = "product_id";
    static String name = "name";
    static String price = "price";
    static String image = "image";

    public WishlistDB(Context context) {
        super(context, "wishlist", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String tableWishlist = "CREATE TABLE "+ table +" ("+ product_id +" INTEGER PRIMARY KEY, "+ name +" TEXT, "+ price +" REAL, "+ image +" BLOB)";
        sqLiteDatabase.execSQL(tableWishlist);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
