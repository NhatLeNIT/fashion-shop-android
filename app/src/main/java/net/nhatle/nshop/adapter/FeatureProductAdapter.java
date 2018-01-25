package net.nhatle.nshop.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.nhatle.nshop.R;
import net.nhatle.nshop.data.model.Product;

import java.util.List;

/**
 * Created by NhatLe on 26-Dec-17.
 */

public class FeatureProductAdapter extends RecyclerView.Adapter<FeatureProductAdapter.ViewHolderFeatureProduct> {
    private Context context;


    private List<Product> menProductList;
    private List<Product> womenProductList;
    private List<Product> childrenProductList;
    private List<Product> shoesProductList;

    public FeatureProductAdapter(Context context, List<Product> menProductList, List<Product> womenProductList, List<Product> childrenProductList, List<Product> shoesProductList) {
        this.context = context;
        this.menProductList = menProductList;
        this.womenProductList = womenProductList;
        this.childrenProductList = childrenProductList;
        this.shoesProductList = shoesProductList;

//        Log.d("111", childrenProductList.size() + "size");

    }

    public void setMenProductList(List<Product> menProductList) {
        this.menProductList = menProductList;
    }

    public void setWomenProductList(List<Product> womenProductList) {
        this.womenProductList = womenProductList;
    }

    public void setChildrenProductList(List<Product> childrenProductList) {
        this.childrenProductList = childrenProductList;
    }

    public void setShoesProductList(List<Product> shoesProductList) {
        this.shoesProductList = shoesProductList;
    }

    public class ViewHolderFeatureProduct extends RecyclerView.ViewHolder {
        RecyclerView recyclerViewMenProduct, recyclerViewWomenProduct, recyclerViewChildrenProduct, recyclerViewShoes;

        public ViewHolderFeatureProduct(View itemView) {
            super(itemView);
            recyclerViewMenProduct = itemView.findViewById(R.id.recycleViewMenProduct);
            recyclerViewWomenProduct = itemView.findViewById(R.id.recycleViewWomenProduct);
            recyclerViewChildrenProduct = itemView.findViewById(R.id.recycleViewChildrenProduct);
            recyclerViewShoes = itemView.findViewById(R.id.recycleViewShoes);
        }
    }

    @Override
    public ViewHolderFeatureProduct onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_recycler_view_feature, parent, false);
        ViewHolderFeatureProduct viewHolderFeatureProduct = new ViewHolderFeatureProduct(view);
        return viewHolderFeatureProduct;
    }

    @Override
    public void onBindViewHolder(ViewHolderFeatureProduct holder, int position) {

        ProductRecyclerViewAdapter linearProductAdapter = new ProductRecyclerViewAdapter(context, menProductList, R.layout.custom_recycler_view_fragment_product_4_column);
        ProductRecyclerViewAdapter womenProductAdapter = new ProductRecyclerViewAdapter(context, womenProductList, R.layout.custom_recycler_view_fragment_product_4_column);
        ProductRecyclerViewAdapter childrenProductAdapter = new ProductRecyclerViewAdapter(context, childrenProductList, R.layout.custom_recycler_view_fragment_product_4_column);
        ProductRecyclerViewAdapter shoesProductAdapter = new ProductRecyclerViewAdapter(context, shoesProductList, R.layout.custom_recycler_view_fragment_product_4_column);

        RecyclerView.LayoutManager layoutManagerMenProduct = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManagerWomenProduct = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManagerChildrenProduct = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManagerShoesProduct = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
//        RecyclerView.LayoutManager layoutManager2 = new GridLayoutManager(context ,3 ,GridLayoutManager.HORIZONTAL,false );

        holder.recyclerViewMenProduct.setLayoutManager(layoutManagerMenProduct);
        holder.recyclerViewMenProduct.setAdapter(linearProductAdapter);

        holder.recyclerViewWomenProduct.setLayoutManager(layoutManagerWomenProduct);
        holder.recyclerViewWomenProduct.setAdapter(womenProductAdapter);

        holder.recyclerViewChildrenProduct.setLayoutManager(layoutManagerChildrenProduct);
        holder.recyclerViewChildrenProduct.setAdapter(childrenProductAdapter);

        holder.recyclerViewShoes.setLayoutManager(layoutManagerShoesProduct);
        holder.recyclerViewShoes.setAdapter(shoesProductAdapter);

        linearProductAdapter.notifyDataSetChanged();
        womenProductAdapter.notifyDataSetChanged();
        childrenProductAdapter.notifyDataSetChanged();
        shoesProductAdapter.notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return 1;
    }


}
