package net.nhatle.nshop.view.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.nhatle.nshop.R;
import net.nhatle.nshop.adapter.ProductRecyclerViewAdapter;
import net.nhatle.nshop.data.model.Product;
import net.nhatle.nshop.presenter.home.HomeProductPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NhatLe on 24-Dec-17.
 */

public class FragmentSales extends Fragment implements IProductListView {
    RecyclerView recyclerView;
    ProductRecyclerViewAdapter productRecyclerViewAdapter;
    HomeProductPresenter homeProductPresenter;

    List<Product> productList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sales, container, false);
        productList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.recyclerViewSales);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext() ,2);
        productRecyclerViewAdapter = new ProductRecyclerViewAdapter(getContext(), productList, R.layout.custom_recycler_view_fragment_product_2_column);

        //set layoutManager va apdapter cho reyclerver
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(productRecyclerViewAdapter);

        productRecyclerViewAdapter.notifyDataSetChanged();

        // set product popular
        homeProductPresenter = new HomeProductPresenter(this);
        homeProductPresenter.getSalesProductList();
        return view;
    }

    @Override
    public void displayListProduct(List<Product> productList) {
       productRecyclerViewAdapter.setProductList(productList);
//        productRecyclerViewAdapter = new Grid2ColumnProductAdapter(getContext(), productList);
//        recyclerView.setAdapter(grid2ColumnProductAdapter);
        productRecyclerViewAdapter.notifyDataSetChanged();
    }
}
