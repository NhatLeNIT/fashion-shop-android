package net.nhatle.nshop.presenter.reset_password;

import org.json.JSONException;

/**
 * Created by NhatLe on 05-Jan-18.
 */

public interface IResetPasswordPresenter {
    void actionResetPassword(String email) throws JSONException;
}
