package net.nhatle.nshop.view.search;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import net.nhatle.nshop.R;
import net.nhatle.nshop.adapter.ProductRecyclerViewAdapter;
import net.nhatle.nshop.data.api.AuthApi;
import net.nhatle.nshop.data.model.Customer;
import net.nhatle.nshop.data.model.Product;
import net.nhatle.nshop.presenter.product.ProductPresenter;
import net.nhatle.nshop.presenter.search.SearchPresenter;
import net.nhatle.nshop.view.category.CategoryActivity;
import net.nhatle.nshop.view.home.HomeActivity;
import net.nhatle.nshop.view.login.LoginActivity;

import org.json.JSONException;

import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, ISearchView {
    Toolbar toolbar;

    SearchPresenter searchPresenter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        binding();
        //        set tool bar
        toolbar.setTitle("");

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // set event button back
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });

        searchPresenter = new SearchPresenter(this);
    }

    private void binding() {
        toolbar = findViewById(R.id.toolbarSearch);
        recyclerView = findViewById(R.id.recyclerViewProduct);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem itemSearch = menu.findItem(R.id.itemSearch);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(itemSearch);
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {

            case R.id.itemSearch:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        try {
            searchPresenter.getProductBySearch(query);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void displayProductList(List<Product> productList) {
        ProductRecyclerViewAdapter productRecyclerViewAdapter = new ProductRecyclerViewAdapter(this, productList, R.layout.custom_recycler_view_fragment_promotion);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        //set layoutManager va apdapter cho reyclerver
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(productRecyclerViewAdapter);
        productRecyclerViewAdapter.notifyDataSetChanged();
    }
}
