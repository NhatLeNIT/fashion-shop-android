package net.nhatle.nshop.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import net.nhatle.nshop.view.home.fragment.FragmentFeature;
import net.nhatle.nshop.view.home.fragment.FragmentInterest;
import net.nhatle.nshop.view.home.fragment.FragmentPopular;
import net.nhatle.nshop.view.home.fragment.FragmentPromotion;
import net.nhatle.nshop.view.home.fragment.FragmentSales;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NhatLe on 24-Dec-17.
 */

public class HomeViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private List<String> titleFragment;
    public HomeViewPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
        fragments.add(new FragmentFeature());
        fragments.add(new FragmentPromotion());
        fragments.add(new FragmentPopular());
        fragments.add(new FragmentSales());
        fragments.add(new FragmentInterest());

        titleFragment = new ArrayList<>();
        titleFragment.add("Nổi bật");
        titleFragment.add("Khuyến mãi");
        titleFragment.add("Phổ biến");
        titleFragment.add("Sản phẩm bán chạy");
        titleFragment.add("Có thể bạn quan tâm");
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleFragment.get(position);
    }
}
