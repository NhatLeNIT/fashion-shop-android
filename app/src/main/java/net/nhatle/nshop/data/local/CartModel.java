package net.nhatle.nshop.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import net.nhatle.nshop.data.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NhatLe on 29-Dec-17.
 */

public class CartModel {
    SQLiteDatabase sqLiteDatabase;

    public void openConnect(Context context) {
        CartDB cartDB = new CartDB(context);
        sqLiteDatabase = cartDB.getWritableDatabase();
    }

    public boolean insert(Product product) {
        Product productResult = this.select(product.getId());
        if(productResult.getId() != 0) {
            int quantity = productResult.getQuantityBuy();
            if (quantity == product.getQuantity()) {
                return false;
            } else
                return this.update(product.getId(), ++quantity);

        }
        else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(CartDB.product_id, product.getId());
            contentValues.put(CartDB.name, product.getName());
            contentValues.put(CartDB.price, product.getPrice());
            contentValues.put(CartDB.image, product.getImageCart());
            contentValues.put(CartDB.quantity_available, product.getQuantity());
            contentValues.put(CartDB.quantity, 1);

            long id = sqLiteDatabase.insert(CartDB.table, null, contentValues);
            return id > 0;
        }
    }

    public List<Product> selectAll(){
        List<Product> productList = new ArrayList<>();

        String sql = "SELECT * FROM " + CartDB.table;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
           int product_id = cursor.getInt(cursor.getColumnIndex(CartDB.product_id));
           String name = cursor.getString(cursor.getColumnIndex(CartDB.name));
           int price = cursor.getInt(cursor.getColumnIndex(CartDB.price));
           byte[] image = cursor.getBlob(cursor.getColumnIndex(CartDB.image));
           int quantity_available = cursor.getInt(cursor.getColumnIndex(CartDB.quantity_available));
           int quantity = cursor.getInt(cursor.getColumnIndex(CartDB.quantity));

           Product product = new Product();
           product.setId(product_id);
           product.setName(name);
           product.setPrice(price);
           product.setImageCart(image);
           product.setQuantity(quantity_available);
           product.setQuantityBuy(quantity);

           productList.add(product);
           cursor.moveToNext();
        }

        return productList;
    }

    public Product select(int productId) {
        Product product = new Product();
        String sql = "SELECT * FROM " + CartDB.table + " WHERE " + CartDB.product_id + "=" + productId;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            String name = cursor.getString(cursor.getColumnIndex(CartDB.name));
            int price = cursor.getInt(cursor.getColumnIndex(CartDB.price));
            byte[] image = cursor.getBlob(cursor.getColumnIndex(CartDB.image));
            int quantity = cursor.getInt(cursor.getColumnIndex(CartDB.quantity));
            product.setId(productId);
            product.setName(name);
            product.setPrice(price);
            product.setImageCart(image);
            product.setQuantityBuy(quantity);
        }


        return product;
    }

    public boolean removeItem(int productId) {
        int result = sqLiteDatabase.delete(CartDB.table, CartDB.product_id + "=" + productId, null);
        return result > 0;
    }

    public boolean update(int productId, int quantity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CartDB.quantity, quantity);
        int result = sqLiteDatabase.update(CartDB.table, contentValues, CartDB.product_id + "=" + productId, null);
        return result > 0;
    }
}
