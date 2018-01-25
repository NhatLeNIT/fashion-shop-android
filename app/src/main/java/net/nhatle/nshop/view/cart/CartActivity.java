package net.nhatle.nshop.view.cart;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.nhatle.nshop.R;
import net.nhatle.nshop.adapter.CartRecyclerViewAdapter;
import net.nhatle.nshop.data.api.AuthApi;
import net.nhatle.nshop.data.model.Customer;
import net.nhatle.nshop.data.model.Product;
import net.nhatle.nshop.presenter.cart.CartPresenter;
import net.nhatle.nshop.presenter.product.ProductPresenter;
import net.nhatle.nshop.view.home.HomeActivity;
import net.nhatle.nshop.view.login.LoginActivity;
import net.nhatle.nshop.view.order.OrderActivity;

import java.util.List;

public class CartActivity extends AppCompatActivity implements ICartView{

    RecyclerView recyclerViewCart;
    CartPresenter cartPresenter;

    LinearLayout linearLayoutCartEmpty, linearLayoutCartSummary;
    Button btnOrder, btnContinue;

    Toolbar toolbar;
    AuthApi authApi;


    Menu menu;
    MenuItem itemLogin;
    MenuItem itemLogout;
    TextView txtItemCartNumber;
    ProductPresenter productPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        binding();

        cartPresenter = new CartPresenter(this);
        cartPresenter.getProductList(this);


        //        set tool bar
        toolbar.setTitle("Giỏ hàng");

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
        authApi = new AuthApi();
        productPresenter = new ProductPresenter();

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentOrder = new Intent(CartActivity.this, OrderActivity.class);
                startActivity(intentOrder);
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void binding() {
        recyclerViewCart = findViewById(R.id.recyclerViewCart);
        toolbar = findViewById(R.id.toolbarCart);
        linearLayoutCartEmpty = findViewById(R.id.linearCartEmpty);
        linearLayoutCartSummary = findViewById(R.id.linearCartSummary);
        btnOrder = findViewById(R.id.buttonOrder);
        btnContinue = findViewById(R.id.buttonContinue);
    }

    @Override
    public void displayProduct(List<Product> productList) {
        linearLayoutCartEmpty.setVisibility(View.GONE);
        linearLayoutCartSummary.setVisibility(View.VISIBLE);
        recyclerViewCart.setVisibility(View.VISIBLE);
        btnContinue.setVisibility(View.GONE);
        btnOrder.setVisibility(View.VISIBLE);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        CartRecyclerViewAdapter cartRecyclerViewAdapter = new CartRecyclerViewAdapter(this, productList);
        recyclerViewCart.setLayoutManager(layoutManager);
        recyclerViewCart.setAdapter(cartRecyclerViewAdapter);
        cartRecyclerViewAdapter.notifyDataSetChanged();
    }

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
                Intent intentCart = new Intent(CartActivity.this, CartActivity.class);
                startActivity(intentCart);
            }
        });
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

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
        }
        return super.onOptionsItemSelected(item);
    }
}
