package net.nhatle.nshop.presenter.login;

import net.nhatle.nshop.data.model.Customer;

/**
 * Created by NhatLe on 26-Dec-17.
 */

public interface IRegisterPresenter {
    /**
     * Thực hiện đăng ký
     * @param customer
     */
    void ActionRegister(Customer customer);
}
