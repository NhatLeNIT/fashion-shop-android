package net.nhatle.nshop.view.login;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import net.nhatle.nshop.R;
import net.nhatle.nshop.adapter.LoginViewPagerAdapter;

public class LoginActivity extends AppCompatActivity {

    TabLayout tabLayoutLogin;
    ViewPager viewPagerLogin;
    LoginViewPagerAdapter loginViewPagerAdapter;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        binding();

        // set toolbar
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
        // set adapter
        loginViewPagerAdapter = new LoginViewPagerAdapter(getSupportFragmentManager());
        viewPagerLogin.setAdapter(loginViewPagerAdapter);
        loginViewPagerAdapter.notifyDataSetChanged();
        tabLayoutLogin.setupWithViewPager(viewPagerLogin);
    }

    private void binding() {
        tabLayoutLogin = findViewById(R.id.tabLayoutLogin);
        viewPagerLogin = findViewById(R.id.viewPagerLogin);
        toolbar = findViewById(R.id.toolbarLogin);
    }
}
