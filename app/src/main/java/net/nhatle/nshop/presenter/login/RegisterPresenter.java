package net.nhatle.nshop.presenter.login;

import net.nhatle.nshop.data.api.AuthApi;
import net.nhatle.nshop.data.model.Customer;
import net.nhatle.nshop.view.login.IAuthView;

/**
 * Created by NhatLe on 26-Dec-17.
 */

public class RegisterPresenter implements IRegisterPresenter {
    private IAuthView authView;
    private AuthApi authApi;

    public RegisterPresenter(IAuthView authView) {
        this.authView = authView;
        authApi = new AuthApi();
    }

    @Override
    public void ActionRegister(Customer customer) {
        Boolean registerResult = authApi.Register(customer);
        if (registerResult) this.authView.success();
        else this.authView.fail();
    }
}
