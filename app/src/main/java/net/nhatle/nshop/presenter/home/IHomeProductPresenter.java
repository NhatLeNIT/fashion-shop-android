package net.nhatle.nshop.presenter.home;

/**
 * Created by NhatLe on 04-Jan-18.
 */

public interface IHomeProductPresenter {
    /**
     * Lấy danh sách sản phẩm khuyến mãi
     */
    void getPromotionProductList();

    /**
     * Lấy danh sách sản phẩm phổ biến
     */
    void getPopularProductList();

    /**
     * Lấy danh sách sản phẩm bán chạy
     */
    void getSalesProductList();

    /**
     * Lấy danh sách sản phẩm có thể bạn quan tâm
     */
    void getRandomProductList();

}
