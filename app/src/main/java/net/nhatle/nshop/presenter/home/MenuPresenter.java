package net.nhatle.nshop.presenter.home;

import net.nhatle.nshop.data.api.MenuApi;
import net.nhatle.nshop.data.model.Category;
import net.nhatle.nshop.view.home.IMenuView;

import java.util.List;

/**
 * Created by NhatLe on 25-Dec-17.
 */

public class MenuPresenter {
    private IMenuView viewMenu;

    public MenuPresenter(IMenuView viewMenu) {
        this.viewMenu = viewMenu;
    }

    /**
     * Lấy và hiển thị menu root
     */
    public void getListMenuRoot() {
        MenuApi menuApi = new MenuApi();
        List<Category> categoryList = menuApi.getListWithParentId(0);
        viewMenu.DisplayMenu(categoryList);
    }
}
