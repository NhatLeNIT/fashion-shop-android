package net.nhatle.nshop.presenter.reset_password;

import android.util.Log;

import net.nhatle.nshop.data.api.AuthApi;
import net.nhatle.nshop.view.reset_password.IResetPasswordView;

import org.json.JSONException;

/**
 * Created by NhatLe on 05-Jan-18.
 */

public class ResetPasswordPresenter implements IResetPasswordPresenter {
    private IResetPasswordView resetPasswordView;
    private AuthApi authApi;

    public ResetPasswordPresenter(IResetPasswordView resetPasswordView) {
        this.resetPasswordView = resetPasswordView;
        this.authApi = new AuthApi();
    }

    @Override
    public void actionResetPassword(String email) throws JSONException {
        if(authApi.resetPassword(email)) {
            resetPasswordView.success();
        } else {
            resetPasswordView.error();
        }
    }
}
