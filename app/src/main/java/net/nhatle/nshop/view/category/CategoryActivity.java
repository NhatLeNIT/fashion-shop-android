package net.nhatle.nshop.view.category;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import net.nhatle.nshop.R;
import net.nhatle.nshop.adapter.ProductRecyclerViewAdapter;
import net.nhatle.nshop.data.api.AuthApi;
import net.nhatle.nshop.data.model.Customer;
import net.nhatle.nshop.data.model.Product;
import net.nhatle.nshop.presenter.category.CategoryPresenter;
import net.nhatle.nshop.presenter.product.ProductPresenter;
import net.nhatle.nshop.view.home.HomeActivity;
import net.nhatle.nshop.view.login.LoginActivity;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CategoryActivity extends Fragment implements ICategoryView {

    RecyclerView recyclerView ;


    Button btnDisplayType;
    Button btnFilter;
    Button btnSort;
    boolean displayGridView = true;
    RecyclerView.LayoutManager layoutManager;
    CategoryPresenter categoryPresenter;
    int categoryId;

    Toolbar toolbar;
    AuthApi authApi;
    MenuInflater inflater;

    Menu menu;
    MenuItem itemLogin;
    MenuItem itemLogout;
    TextView txtItemCartNumber;
    ProductPresenter productPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_category, container, false);
        setHasOptionsMenu(false);
        binding(view);

        // get category id in intent
//        Intent intent = getIntent();
//        categoryId = intent.getIntExtra("categoryId", 0);
//        String categoryName = intent.getStringExtra("categoryName");
        Bundle bundle = getArguments();
        categoryId = bundle.getInt("categoryId", 0);
        String categoryName = bundle.getString("categoryName");

        categoryPresenter = new CategoryPresenter(this);
        categoryPresenter.getProductByCategoryId(categoryId);

        btnDisplayType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayGridView = !displayGridView;
                categoryPresenter.getProductByCategoryId(categoryId);
            }
        });


        //        set tool bar
        toolbar.setTitle(categoryName);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // set event button back
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack("HomeActivity", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });


        authApi = new AuthApi();
        productPresenter = new ProductPresenter();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        this.inflater = inflater;
        inflater.inflate(R.menu.home_menu, menu);
        this.menu = menu;
        itemLogin = menu.findItem(R.id.itemLogin);
        itemLogout = menu.findItem(R.id.itemLogout);
        Customer customer = authApi.getCacheLogin(getContext());
        if (customer.getId() != 0) {
            itemLogin.setTitle(customer.getName());
            itemLogout.setVisible(true);
        }

        MenuItem menuItemCart = menu.findItem(R.id.itemCart);
        View viewCartCustom = MenuItemCompat.getActionView(menuItemCart);
        txtItemCartNumber = viewCartCustom.findViewById(R.id.textViewItemNumber);
        txtItemCartNumber.setText(productPresenter.countItemInCart(getContext()) + "");
    }

    private void binding(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewProduct);

        btnFilter = view.findViewById(R.id.buttonFilter);
        btnSort = view.findViewById(R.id.buttonSort);
        btnDisplayType = view.findViewById(R.id.buttonDisplayType);

        toolbar = view.findViewById(R.id.toolbarCategory);
    }

    @Override
    public void displayProductList(List<Product> productList) {
        ProductRecyclerViewAdapter productRecyclerViewAdapter;

        if(displayGridView) {
            productRecyclerViewAdapter = new ProductRecyclerViewAdapter(getContext() , productList, R.layout.custom_recycler_view_fragment_product_2_column);
            layoutManager = new GridLayoutManager(getContext(), 2);
        } else {
            productRecyclerViewAdapter = new ProductRecyclerViewAdapter(getContext() , productList, R.layout.custom_recycler_view_fragment_promotion);
            layoutManager = new LinearLayoutManager(getContext());
        }
        //set layoutManager va apdapter cho reyclerver
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(productRecyclerViewAdapter);
        productRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.itemLogin:
                if (authApi.getCacheLogin(getContext()).getId() == 0) {
                    Intent intentLogin = new Intent(getContext(), LoginActivity.class);
                    startActivity(intentLogin);
                }
                break;
            case R.id.itemLogout:
                if (authApi.getCacheLogin(getContext()) != null) {
                    authApi.updateCacheLogin(getContext(), new Customer());
                    this.menu.clear();
                    this.onCreateOptionsMenu(this.menu, this.inflater);
                    Toast.makeText(getContext(), "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), HomeActivity.class);
                    startActivity(intent);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
