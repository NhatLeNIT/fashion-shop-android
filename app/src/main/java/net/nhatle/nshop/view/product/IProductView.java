package net.nhatle.nshop.view.product;

import net.nhatle.nshop.data.model.Comment;
import net.nhatle.nshop.data.model.Image;
import net.nhatle.nshop.data.model.Product;

import java.util.List;

/**
 * Created by NhatLe on 27-Dec-17.
 */

public interface IProductView {
    void displayProduct(Product product);
    void displayImage(List<Image> imageList);
    void displayComment(List<Comment> commentList);
    void displayProductSameCategory(List<Product> productList);
    void addCartSuccess();
    void addCartFail();
}
