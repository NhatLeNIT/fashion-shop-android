package net.nhatle.nshop.view.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.nhatle.nshop.R;
import net.nhatle.nshop.adapter.FeatureProductAdapter;
import net.nhatle.nshop.data.model.Product;
import net.nhatle.nshop.presenter.home.FeatureProductPresenter;
import net.nhatle.nshop.view.home.IFeatureProductView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NhatLe on 24-Dec-17.
 */

public class FragmentFeature extends Fragment implements IFeatureProductView{
    RecyclerView recyclerView ;
    FeatureProductAdapter featureProductAdapter;

    FeatureProductPresenter featureProductPresenter;
    List<Product> menProductList;
    List<Product> womenProductList;
    List<Product> childrenProductList;
    List<Product> shoesProductList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_feature, container, false);

        menProductList = new ArrayList<>();
        womenProductList = new ArrayList<>();
        childrenProductList = new ArrayList<>();
        shoesProductList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.recycleViewFeature);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        featureProductAdapter = new FeatureProductAdapter(getActivity() , menProductList, womenProductList, childrenProductList, shoesProductList );

        //set layoutManager va apdapter cho reyclerver
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(featureProductAdapter);

        featureProductAdapter.notifyDataSetChanged();

        // set product feature
        featureProductPresenter = new FeatureProductPresenter(this);
        featureProductPresenter.getListFeatureProduct();

        return view;
    }

    @Override
    public void displayListProduct(List<Product> menProductList, List<Product> womenProductList, List<Product> childrenProductList, List<Product> shoesProductList) {
        featureProductAdapter.setMenProductList(menProductList);
        featureProductAdapter.setWomenProductList(womenProductList);
        featureProductAdapter.setChildrenProductList(childrenProductList);
        featureProductAdapter.setShoesProductList(shoesProductList);
        featureProductAdapter.notifyDataSetChanged();
    }
}
