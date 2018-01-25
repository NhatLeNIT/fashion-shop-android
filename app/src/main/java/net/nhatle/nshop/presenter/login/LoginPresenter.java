package net.nhatle.nshop.presenter.login;

import android.content.Context;
import android.util.Log;

import net.nhatle.nshop.data.api.AuthApi;
import net.nhatle.nshop.data.model.Customer;
import net.nhatle.nshop.view.login.IAuthView;

/**
 * Created by NhatLe on 26-Dec-17.
 */

public class LoginPresenter implements ILoginPresenter {
    private IAuthView authView;
    private AuthApi authApi;

    public LoginPresenter(IAuthView authView) {
        this.authView = authView;
        authApi = new AuthApi();
    }


    @Override
    public void ActionLogin(Context context, String email, String password) {
        Customer customer = authApi.Login(context, email, password);
        if (customer != null) {
            this.authView.success();
        } else this.authView.fail();
    }
}
