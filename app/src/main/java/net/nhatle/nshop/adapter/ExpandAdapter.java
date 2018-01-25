package net.nhatle.nshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import net.nhatle.nshop.R;
import net.nhatle.nshop.data.api.MenuApi;
import net.nhatle.nshop.data.model.Category;
import net.nhatle.nshop.view.category.CategoryActivity;


import java.util.List;

/**
 * Created by NhatLe on 25-Dec-17.
 */

public class ExpandAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<Category> categoryList;
    ViewHolderMenu viewHolderMenu;

    public ExpandAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
        MenuApi menuApi = new MenuApi();
        int count = categoryList.size();
        for (int i = 0; i < count; i++) {
            int parentId = categoryList.get(i).getId();
            categoryList.get(i).setCategoryListChild(menuApi.getListWithParentId(parentId));
        }
    }

    @Override
    public int getGroupCount() {
        return categoryList.size();
    }

    @Override
    public int getChildrenCount(int positionParent) {
        if (categoryList.get(positionParent).getCategoryListChild().size() != 0)
            return 1;
        return 0;
    }

    @Override
    public Object getGroup(int positionParent) {
        return categoryList.get(positionParent);
    }

    @Override
    public Object getChild(int positionParent, int positionChild) {
        return categoryList.get(positionParent).getCategoryListChild().get(positionChild);
    }

    @Override
    public long getGroupId(int positionParent) {
        return categoryList.get(positionParent).getId();
    }

    @Override
    public long getChildId(int positionParent, int positionChildren) {
        return categoryList.get(positionParent).getCategoryListChild().get(positionChildren).getId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int positionParent, boolean isExpended, View view, ViewGroup viewGroup) {
        View viewGroupParent = view;
        if (viewGroupParent == null) {
            viewHolderMenu = new ViewHolderMenu();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            viewGroupParent = layoutInflater.inflate(R.layout.custom_layout_group_parent, viewGroup, false);

            viewHolderMenu.txtCategoryName = viewGroupParent.findViewById(R.id.textViewName);
            viewHolderMenu.imgPlus = viewGroupParent.findViewById(R.id.imageViewPlus);

            viewGroupParent.setTag(viewHolderMenu);
        } else {
            viewHolderMenu = (ViewHolderMenu) viewGroupParent.getTag();
        }


        viewHolderMenu.txtCategoryName.setText(categoryList.get(positionParent).getName());

//        set an hien nut expand and collapse
        int countCategoryChild = categoryList.get(positionParent).getCategoryListChild().size();
        if (countCategoryChild > 0)
            viewHolderMenu.imgPlus.setVisibility(View.VISIBLE);
        else viewHolderMenu.imgPlus.setVisibility(View.INVISIBLE);

//        set hinh khi expand va collapse
        if (isExpended) {
            viewHolderMenu.imgPlus.setImageResource(R.drawable.ic_remove_black_24dp);
            viewGroupParent.setBackgroundResource(R.color.colorGray);
        } else {
            viewHolderMenu.imgPlus.setImageResource(R.drawable.ic_add_black_24dp);
            viewGroupParent.setBackgroundResource(R.color.colorWhite);
        }

//        set event khi click item
        viewGroupParent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int categoryId = categoryList.get(positionParent).getId();
                String categoryName = categoryList.get(positionParent).getName();
//                Log.d("111", categoryId + " - " + categoryName);
//                Intent intentCategory = new Intent(context, CategoryActivity.class);
//                intentCategory.putExtra("categoryId", categoryId);
//                intentCategory.putExtra("categoryName", categoryName);
//                context.startActivity(intentCategory);
                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                CategoryActivity categoryActivity = new CategoryActivity();

                Bundle bundle = new Bundle();
                bundle.putInt("categoryId", categoryId);
                bundle.putString("categoryName", categoryName);

                categoryActivity.setArguments(bundle);
                fragmentTransaction.addToBackStack("HomeActivity");
                fragmentTransaction.replace(R.id.addFragmentCategory, categoryActivity);
                fragmentTransaction.commit();


                return false;
            }
        });
        return viewGroupParent;
    }

    @Override
    public View getChildView(int positionParent, int positionChildren, boolean isExpended, View view, ViewGroup viewGroup) {
//        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
//        View viewGroupChild = layoutInflater.inflate(R.layout.custom_layout_group_child, viewGroup, false);
//        ExpandableListView expandableListView = (ExpandableListView) viewGroupChild.findViewById(R.id.expandableMenuChild);
        SecondExpand secondExpand = new SecondExpand(context);
        ExpandAdapter secondExpandAdapter = new ExpandAdapter(context, categoryList.get(positionParent).getCategoryListChild());
        secondExpand.setAdapter(secondExpandAdapter);
        secondExpand.setGroupIndicator(null);
        notifyDataSetChanged();

        return secondExpand;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    public class SecondExpand extends ExpandableListView {

        public SecondExpand(Context context) {
            super(context);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            WindowManager windowManager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;
//            widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public class ViewHolderMenu {
        TextView txtCategoryName;
        ImageView imgPlus;
    }
}
