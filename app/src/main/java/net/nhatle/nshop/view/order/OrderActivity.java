package net.nhatle.nshop.view.order;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.nhatle.nshop.R;
import net.nhatle.nshop.data.api.AuthApi;
import net.nhatle.nshop.data.local.CartModel;
import net.nhatle.nshop.data.model.Customer;
import net.nhatle.nshop.data.model.Order;
import net.nhatle.nshop.data.model.OrderDetail;
import net.nhatle.nshop.data.model.Product;
import net.nhatle.nshop.presenter.order.OrderPresenter;
import net.nhatle.nshop.presenter.product.ProductPresenter;
import net.nhatle.nshop.view.home.HomeActivity;
import net.nhatle.nshop.view.login.LoginActivity;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity implements IOrderView {
    EditText editTextName, editTextAddress,editTextPhone,editTextNote;
    Button buttonSubmit;
    CheckBox checkboxAgree;

    Toolbar toolbar;
    AuthApi authApi;
    OrderPresenter orderPresenter;
    CartModel cartModel;

    Menu menu;
    MenuItem itemLogin;
    MenuItem itemLogout;
    TextView txtItemCartNumber;
    ProductPresenter productPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        orderPresenter = new OrderPresenter(this, OrderActivity.this);
        cartModel = new CartModel();
        binding();
        //        set tool bar
        toolbar.setTitle("Đặt hàng");

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

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String address = editTextAddress.getText().toString().trim();
                String phone = editTextPhone.getText().toString().trim();
                String note = editTextNote.getText().toString().trim();
                int userId = authApi.getCacheLogin(OrderActivity.this).getId();
                if(checkboxAgree.isChecked()) {
                    if(name.equals("") || address.equals("") || phone.equals("")) {
                        Toast.makeText(OrderActivity.this, "Vui lòng nhập đủ thông tin tên, địa chỉ, số điện thoại!", Toast.LENGTH_SHORT).show();
                    } else {
                        cartModel.openConnect(OrderActivity.this);
                        List<Product> productList = cartModel.selectAll();
                        int length = productList.size();

                        List<OrderDetail> orderDetailList = new ArrayList<>();
                        for (int i = 0; i < length; i++) {
                            OrderDetail orderDetail = new OrderDetail();
                            orderDetail.setIdProduct(productList.get(i).getId());
                            orderDetail.setPrice(productList.get(i).getPrice());
                            orderDetail.setQuantity(productList.get(i).getQuantityBuy());

                            orderDetailList.add(orderDetail);
                        }

                        Order order = new Order();
                        order.setReceiverName(name);
                        order.setReceiverAddress(address);
                        order.setReceiverPhone(phone);
                        order.setNote(note);
                        order.setIdUser(userId);
                        order.setDetails(orderDetailList);

                        try {
                            orderPresenter.addOrder(order);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else Toast.makeText(OrderActivity.this, "Bạn chưa xác nhận thông tin!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void binding() {
        toolbar = findViewById(R.id.toolbarOrder);
        editTextName = findViewById(R.id.editTextName);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextNote = findViewById(R.id.editTextNote);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        checkboxAgree = findViewById(R.id.checkboxAgree);
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
        return true;
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
                    Intent intent = new Intent(this, HomeActivity.class);
                    startActivity(intent);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void addOrderSuccess() {
        Toast.makeText(this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
        Intent intentHome = new Intent(this, HomeActivity.class);
        startActivity(intentHome);
    }

    @Override
    public void addOrderFail() {
        Toast.makeText(this, "Có lỗi xảy ra, vui lòng thử lại sau!", Toast.LENGTH_SHORT).show();
    }
}
