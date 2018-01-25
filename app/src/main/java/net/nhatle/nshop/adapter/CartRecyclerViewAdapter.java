package net.nhatle.nshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import net.nhatle.nshop.R;
import net.nhatle.nshop.connect.Config;
import net.nhatle.nshop.data.local.CartModel;
import net.nhatle.nshop.data.model.Product;
import net.nhatle.nshop.presenter.product.ProductPresenter;

import java.text.NumberFormat;
import java.util.List;

/**
 * Created by NhatLe on 29-Dec-17.
 */

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.ViewHolderCart> {
    private Context context;
    private List<Product> productList;
    CartModel cartModel;

    public CartRecyclerViewAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
        cartModel = new CartModel();
        cartModel.openConnect(context);
    }

    public class ViewHolderCart extends RecyclerView.ViewHolder {
        TextView txtName;
        TextView txtPrice;
        TextView txtQuantity;
        ImageView imageProduct;
        ImageButton imgButtonWishlist, imgButtonDelItem, imgDecreaseQty, imgIncreaseQty;
        public ViewHolderCart(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.textViewName);
            txtPrice = itemView.findViewById(R.id.textViewPrice);
            imageProduct    = itemView.findViewById(R.id.imageViewProduct);
            txtQuantity = itemView.findViewById(R.id.textViewQuantity);
            imgButtonWishlist = itemView.findViewById(R.id.imageButtonWishList);
            imgButtonDelItem = itemView.findViewById(R.id.imageButtonDelItem);
            imgDecreaseQty = itemView.findViewById(R.id.imageButtonDecreaseQuantity);
            imgIncreaseQty = itemView.findViewById(R.id.imageButtonIncreaseQuantity);

        }
    }
    @Override
    public ViewHolderCart onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_recycler_view_cart, parent , false);
        ViewHolderCart viewHolderCart = new ViewHolderCart(view);
        return viewHolderCart;
    }

    @Override
    public void onBindViewHolder(final ViewHolderCart holder, final int position) {
        final Product product = productList.get(position);
        holder.txtName.setText(product.getName());
        holder.txtPrice.setText(NumberFormat.getInstance().format(product.getPrice()) + "đ");

        byte[] imageProduct = product.getImageCart();
        Bitmap bitmapImage = BitmapFactory.decodeByteArray(imageProduct, 0, imageProduct.length);
        holder.imageProduct.setImageBitmap(bitmapImage);
        holder.imgButtonDelItem.setTag(product.getId());
        holder.txtQuantity.setText(product.getQuantityBuy() + "");

        holder.imgDecreaseQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(holder.txtQuantity.getText().toString());
                if(quantity > 1) {
                    holder.txtQuantity.setText(--quantity + "");
                    cartModel.update(product.getId(), quantity);
                }
            }
        });

        holder.imgIncreaseQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(holder.txtQuantity.getText().toString());
                if(quantity == product.getQuantity()) {
                    Toast.makeText(context, "Xin lỗi! Sản phẩm đã vượt quá số lượng tồn kho!", Toast.LENGTH_SHORT).show();
                } else {
                    holder.txtQuantity.setText(++quantity + "");
                    cartModel.update(product.getId(), quantity);
                }
            }
        });
        // set event click del item
        holder.imgButtonDelItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int productId = (int)view.getTag();

                CartModel cartModel = new CartModel();
                cartModel.openConnect(context);
                boolean result = cartModel.removeItem(productId);
                if(result) {
                    productList.remove(position);
                    notifyDataSetChanged();
                }
               else Toast.makeText(context, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


}
