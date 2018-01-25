package net.nhatle.nshop.presenter.login;

import android.content.Context;

/**
 * Created by NhatLe on 26-Dec-17.
 */

public interface ILoginPresenter {
    void ActionLogin(Context context, String email, String password);
}
