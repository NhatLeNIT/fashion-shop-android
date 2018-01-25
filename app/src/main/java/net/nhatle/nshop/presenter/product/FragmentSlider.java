package net.nhatle.nshop.presenter.product;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import net.nhatle.nshop.R;
import net.nhatle.nshop.connect.Config;

/**
 * Created by NhatLe on 27-Dec-17.
 */

public class FragmentSlider extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slider, container, false);
        Bundle bundle = getArguments();
        String imageUrl = bundle.getString("imageUrl");
        ImageView imageView = view.findViewById(R.id.imageViewSlider);

        String imageUrlFull = Config.URL_IMAGE + "uploads/products/" + imageUrl;
        Picasso.with(getContext()).load(imageUrlFull).resize(300, 400).into(imageView);

        return view;
    }
}
