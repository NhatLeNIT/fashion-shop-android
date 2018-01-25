package net.nhatle.nshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.nhatle.nshop.R;
import net.nhatle.nshop.connect.Config;
import net.nhatle.nshop.data.model.Product;
import net.nhatle.nshop.view.product.ProductActivity;

import java.text.NumberFormat;
import java.util.List;

/**
 * Created by NhatLe on 26-Dec-17.
 */

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ViewHolderGridProduct>{

    private Context context;
    private List<Product> productList;
    private int layoutInflater;

    public ProductRecyclerViewAdapter(Context context, List<Product> productList, int layoutInflater) {
        this.context = context;
        this.productList = productList;
        this.layoutInflater = layoutInflater;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public class ViewHolderGridProduct extends RecyclerView.ViewHolder {
        TextView txtName;
        TextView txtPrice;
        ImageView imageProduct;
        TextView txtPricePromotion;
        LinearLayout linearProduct;
        public ViewHolderGridProduct(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.textViewName);
            txtPrice = itemView.findViewById(R.id.textViewPrice);
            imageProduct    = itemView.findViewById(R.id.imageViewProduct);
            txtPricePromotion = itemView.findViewById(R.id.textViewPricePromotion);
            linearProduct = itemView.findViewById(R.id.linearProduct);
        }
    }
    @Override
    public ViewHolderGridProduct onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(this.layoutInflater, parent , false);
        ViewHolderGridProduct viewHolderGridProduct = new ViewHolderGridProduct(view);
        return viewHolderGridProduct;
    }

    @Override
    public void onBindViewHolder(ViewHolderGridProduct holder, int position) {
        final Product product = productList.get(position);

        holder.txtName.setText(product.getName());
        if(product.getPromotion() != 0) {
            holder.txtPrice.setText(NumberFormat.getInstance().format(product.getPrice()) + "đ");
            holder.txtPricePromotion.setText(NumberFormat.getInstance().format(product.getPromotion()) + "đ");
            holder.txtPrice.setVisibility(View.VISIBLE);
        }
        else   holder.txtPricePromotion.setText(NumberFormat.getInstance().format(product.getPrice()) + "đ");

        String imageUrl = Config.URL_IMAGE + "uploads/products/" + product.getImages().get(0).getImage();
        Picasso.with(context).load(imageUrl).resize(180, 220).into(holder.imageProduct);

        // set event click linear product
        holder.linearProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d("111", product.getName());
                Intent intentProduct = new Intent(context, ProductActivity.class);
                intentProduct.putExtra("productId", product.getId());
                context.startActivity(intentProduct);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


}
