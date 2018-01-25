package net.nhatle.nshop.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import net.nhatle.nshop.view.login.fragment.FragmentLogin;
import net.nhatle.nshop.view.login.fragment.FragmentRegister;

/**
 * Created by NhatLe on 25-Dec-17.
 */

public class LoginViewPagerAdapter extends FragmentPagerAdapter {

    public LoginViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentLogin();
            case 1:
                return new FragmentRegister();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Đăng nhập";
            case 1:
                return "Đăng ký";
            default:
                return null;
        }
    }
}
