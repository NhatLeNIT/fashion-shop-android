package net.nhatle.nshop.view.home;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.nhatle.nshop.adapter.ExpandAdapter;
import net.nhatle.nshop.adapter.FeatureProductAdapter;
import net.nhatle.nshop.adapter.HomeViewPagerAdapter;
import net.nhatle.nshop.R;
import net.nhatle.nshop.data.api.AuthApi;
import net.nhatle.nshop.data.model.Category;
import net.nhatle.nshop.data.model.Customer;
import net.nhatle.nshop.data.model.Product;
import net.nhatle.nshop.presenter.home.FeatureProductPresenter;
import net.nhatle.nshop.presenter.home.MenuPresenter;
import net.nhatle.nshop.presenter.product.ProductPresenter;
import net.nhatle.nshop.view.cart.CartActivity;
import net.nhatle.nshop.view.login.LoginActivity;
import net.nhatle.nshop.view.search.SearchActivity;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements IMenuView, AppBarLayout.OnOffsetChangedListener {
    ProductPresenter productPresenter;
    MenuPresenter menuPresenter;
    AuthApi authApi;

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ExpandableListView expandableListView;
    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout appBarLayout;
    Button buttonSearch;
    TextView txtItemCartNumber;

    Menu menu;
    MenuItem itemLogin;
    MenuItem itemLogout;

    HomeViewPagerAdapter homeViewPagerAdapter;

    private int lastExpandedPosition = -1;
    boolean checkPause = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        binding();

        productPresenter = new ProductPresenter();
        menuPresenter = new MenuPresenter(this);
        authApi = new AuthApi();

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBarDrawerToggle.syncState();

        // set adapter
        homeViewPagerAdapter = new HomeViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(homeViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        // set menu
        menuPresenter.getListMenuRoot();

//        Collapse all group except selected group in expandable list view
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    expandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });

        // set event click cho appbar
        appBarLayout.addOnOffsetChangedListener(this);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSearch = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intentSearch);
            }
        });
    }


    private void binding() {
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewPager);
        drawerLayout = findViewById(R.id.drawerLayout);
        expandableListView = findViewById(R.id.expandableMenu);
        appBarLayout = findViewById(R.id.appbarLayout);
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbarLayout);
        buttonSearch = findViewById(R.id.buttonSearch);
    }

    /**
     * Đưa danh sách category lên menu
     *
     * @param categoryList
     */
    @Override
    public void DisplayMenu(List<Category> categoryList) {
        ExpandAdapter expandAdapter = new ExpandAdapter(this, categoryList);
        expandableListView.setAdapter(expandAdapter);
        expandAdapter.notifyDataSetChanged();
    }

    // Xu ly menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        this.menu = menu;
        itemLogin = menu.findItem(R.id.itemLogin);
        itemLogout = menu.findItem(R.id.itemLogout);


        Customer customer = authApi.getCacheLogin(this);
        if (customer.getId() != 0) {
            itemLogin.setTitle(customer.getName());
            itemLogout.setVisible(true);
        }

        MenuItem menuItemCart = menu.findItem(R.id.itemCart);
        View viewCartCustom = MenuItemCompat.getActionView(menuItemCart);
        txtItemCartNumber = viewCartCustom.findViewById(R.id.textViewItemNumber);
        txtItemCartNumber.setText(productPresenter.countItemInCart(this) + "");

//        set click cho item cart
        viewCartCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCart = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intentCart);
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Click item o menu
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        set click cho drawerToggle
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        int id = item.getItemId();
        switch (id) {
            case R.id.itemLogin:
                if (authApi.getCacheLogin(this).getId() == 0) {
                    Intent intentLogin = new Intent(this, LoginActivity.class);
                    startActivity(intentLogin);
                }
                break;
            case R.id.itemLogout:
                if (authApi.getCacheLogin(this) != null) {
                    authApi.updateCacheLogin(this, new Customer());
                    this.menu.clear();
                    this.onCreateOptionsMenu(this.menu);
                    Toast.makeText(this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.itemSearch:
                Intent intentSearch = new Intent(this, SearchActivity.class);
                startActivity(intentSearch);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        LinearLayout linearLayout = appBarLayout.findViewById(R.id.linearSearch);

        if (collapsingToolbarLayout.getHeight() + verticalOffset <= 1.5 + ViewCompat.getMinimumHeight(collapsingToolbarLayout)) {
            linearLayout.animate().alpha(0).setDuration(200);
            MenuItem itemSearch = menu.findItem(R.id.itemSearch);
            itemSearch.setVisible(true);
        } else {
            linearLayout.animate().alpha(1).setDuration(800);
            try {
                MenuItem itemSearch = menu.findItem(R.id.itemSearch);
                itemSearch.setVisible(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(checkPause)
            txtItemCartNumber.setText(productPresenter.countItemInCart(this) + "");
    }

    @Override
    protected void onPause() {
        super.onPause();
        checkPause = true;
    }
}
