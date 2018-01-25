package net.nhatle.nshop.view.product;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.nhatle.nshop.R;
import net.nhatle.nshop.adapter.CommentRecyclerVIewAdapter;
import net.nhatle.nshop.adapter.ProductRecyclerViewAdapter;
import net.nhatle.nshop.adapter.SliderViewPagerAdapter;
import net.nhatle.nshop.data.api.AuthApi;
import net.nhatle.nshop.data.model.Comment;
import net.nhatle.nshop.data.model.Customer;
import net.nhatle.nshop.data.model.Image;
import net.nhatle.nshop.data.model.Product;
import net.nhatle.nshop.presenter.product.FragmentSlider;
import net.nhatle.nshop.presenter.product.ProductPresenter;
import net.nhatle.nshop.view.cart.CartActivity;
import net.nhatle.nshop.view.comment.CommentActivity;
import net.nhatle.nshop.view.home.HomeActivity;
import net.nhatle.nshop.view.login.LoginActivity;

import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity implements IProductView {

    ViewPager viewPager;
    ProductPresenter productPresenter;
    TextView[] txtDots;
    LinearLayout linearLayoutDot;
    List<Fragment> fragmentList;
    TextView txtName, txtPrice, txtDescription, txtComment, txtItemCartNumber;
    RecyclerView recyclerViewComment;
    RecyclerView recyclerViewProductSameCategory;

    Toolbar toolbar;
    AuthApi authApi;
    Menu menu;
    MenuItem itemLogin;
    MenuItem itemLogout;
    int productId, userId;
    Button btnAddCart;
    ImageButton imgButtonAddWishList;

    Product product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        binding();

        productId = getIntent().getIntExtra("productId", 0);
        productPresenter = new ProductPresenter(this);
        productPresenter.getProduct(productId);

        //        set tool bar
        toolbar.setTitle("Thông tin sản phẩm");
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

        // Set alert comment
        txtComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if(userId != 0) {
                    Intent intentComment = new Intent(view.getContext(), CommentActivity.class);
                    intentComment.putExtra("productId", productId);
                    intentComment.putExtra("userId", userId);
                    startActivity(intentComment);
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext(), R.style.AlertDialogCustom);
                    builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.setPositiveButton("Đăng nhập", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(view.getContext(), LoginActivity.class);
                            startActivity(intent);
                        }
                    });

                    builder.setTitle("Thông báo");

                    builder.setMessage("Bạn cần đăng nhập để bình luận về sản phẩm");

                    AlertDialog ad = builder.create();
                    ad.show();
                }
            }
        });

        // set add cart
        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment= fragmentList.get(0);
                View view1 = fragment.getView();
                ImageView imageView = view1.findViewById(R.id.imageViewSlider);
                Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] imageCart = byteArrayOutputStream.toByteArray();

                product.setImageCart(imageCart);
                productPresenter.addCart(product, view.getContext());
            }
        });
    }

    private void binding() {
        linearLayoutDot = findViewById(R.id.lnDots);
        viewPager = findViewById(R.id.viewpagerSlider);
        toolbar = findViewById(R.id.toolbarProduct);
        txtName = findViewById(R.id.textViewName);
        txtPrice = findViewById(R.id.textViewPrice);
        txtDescription = findViewById(R.id.textViewDescription);
        txtComment = findViewById(R.id.textViewComment);
        recyclerViewComment = findViewById(R.id.recyclerViewComment);
        recyclerViewProductSameCategory = findViewById(R.id.recyclerViewProductSameCategory);
        btnAddCart = findViewById(R.id.buttonAddCart);
        imgButtonAddWishList = findViewById(R.id.imageButtonWishList);
    }

    @Override
    public void displayProduct(Product product) {
        this.product = product;

        productId = product.getId();
        txtName.setText(product.getName());

        txtPrice.setText(NumberFormat.getInstance().format(product.getPrice()) + "đ");
        txtDescription.setText(Html.fromHtml(product.getContent()));

        productPresenter.getProductSameCategory(product.getIdCate());
    }

    @Override
    public void displayImage(List<Image> imageList) {
        fragmentList = new ArrayList<>();
        int length = imageList.size();

        for (int i = 0; i < length; i++) {
            FragmentSlider fragmentSlider  = new FragmentSlider();
            Bundle bundle = new Bundle();
            bundle.putString("imageUrl", imageList.get(i).getImage());
            fragmentSlider.setArguments(bundle);

            fragmentList.add(fragmentSlider);
        }
//
        SliderViewPagerAdapter sliderViewPagerAdapter = new SliderViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(sliderViewPagerAdapter);
        sliderViewPagerAdapter.notifyDataSetChanged();
        addDotSlider(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addDotSlider(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void displayComment(List<Comment> commentList) {
        CommentRecyclerVIewAdapter commentRecyclerVIewAdapter = new CommentRecyclerVIewAdapter(this, commentList, 10);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewComment.setLayoutManager(layoutManager);
        recyclerViewComment.setAdapter(commentRecyclerVIewAdapter);
        commentRecyclerVIewAdapter.notifyDataSetChanged();
    }

    @Override
    public void displayProductSameCategory(List<Product> productList) {
        Log.d("111", productList.size() + "size");
        ProductRecyclerViewAdapter productRecyclerViewAdapter = new ProductRecyclerViewAdapter(this, productList, R.layout.custom_recycler_view_fragment_product_4_column);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 4);
        recyclerViewProductSameCategory.setLayoutManager(layoutManager);
        recyclerViewProductSameCategory.setAdapter(productRecyclerViewAdapter);
        productRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void addCartSuccess() {
        Toast.makeText(this, "Thêm sản phẩm vào giỏ hàng thành công!", Toast.LENGTH_SHORT).show();
        txtItemCartNumber.setText(productPresenter.countItemInCart(this) + "");
    }

    @Override
    public void addCartFail() {
        Toast.makeText(this, "Xin lỗi! Số lượng tồn kho không đủ.", Toast.LENGTH_SHORT).show();
    }

    private void addDotSlider(int position){
        txtDots = new TextView[fragmentList.size()];
        linearLayoutDot.removeAllViews();
        for (int i = 0; i < fragmentList.size() ; i++){
            txtDots[i] = new TextView(this);
            txtDots[i].setText(Html.fromHtml("&#8226"));
            txtDots[i].setTextSize(30);
            txtDots[i].setTextColor(getIdColor(R.color.colorSliderInActive));
            txtDots[i].setGravity(0);

            linearLayoutDot.addView(txtDots[i]);
        }
        txtDots[position].setTextColor(getIdColor(R.color.bgToolbar));
    }
    private int getIdColor(int idcolor){
        int color = 0;
        if(Build.VERSION.SDK_INT > 21) {
            color = ContextCompat.getColor(this , idcolor);
        }else{
            color = getResources().getColor(idcolor);

        }
        return color;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        this.menu = menu;
        itemLogin = menu.findItem(R.id.itemLogin);
        itemLogout = menu.findItem(R.id.itemLogout);
        Customer customer = authApi.getCacheLogin(this);
        userId = customer.getId();
//        Log.d("111", userId + "user id");
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
                Intent intentCart = new Intent(ProductActivity.this, CartActivity.class);
                startActivity(intentCart);
            }
        });
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
}
