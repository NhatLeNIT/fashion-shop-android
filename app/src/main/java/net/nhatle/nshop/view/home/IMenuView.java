package net.nhatle.nshop.view.home;

import net.nhatle.nshop.data.model.Category;

import java.util.List;

/**
 * Created by NhatLe on 25-Dec-17.
 */

public interface IMenuView {
    public void  DisplayMenu(List<Category> categoryList);
}
