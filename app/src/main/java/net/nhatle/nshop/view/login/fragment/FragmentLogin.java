package net.nhatle.nshop.view.login.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.nhatle.nshop.R;
import net.nhatle.nshop.presenter.login.LoginPresenter;
import net.nhatle.nshop.view.home.HomeActivity;
import net.nhatle.nshop.view.login.IAuthView;
import net.nhatle.nshop.view.reset_password.ResetPasswordActivity;

/**
 * Created by NhatLe on 25-Dec-17.
 */

public class FragmentLogin extends Fragment implements IAuthView{
    Button btnLogin;
    EditText txtEmail;
    EditText txtPassword;
    TextView txtResetPassword;

    LoginPresenter loginPresenter;
    TextInputLayout inputLayoutEmail;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        binding(view);
        loginPresenter = new LoginPresenter(this);

        btnLogin.setEnabled(false);

        txtEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    String email = txtEmail.getText().toString();
                    if (email.trim().equals("")) {
                        inputLayoutEmail.setErrorEnabled(true);
                        inputLayoutEmail.setError("Không được để trống!");
                    } else {
                        Boolean checkEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches();
                        if (!checkEmail) {
                            inputLayoutEmail.setErrorEnabled(true);
                            inputLayoutEmail.setError("Email không đúng định dạng");
                            btnLogin.setEnabled(false);
                        } else {
                            inputLayoutEmail.setErrorEnabled(false);
                            inputLayoutEmail.setError("");
                            btnLogin.setEnabled(true);
                        }

                    }
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();
                loginPresenter.ActionLogin(getActivity(), email, password);
            }
        });

        txtResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ResetPasswordActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void binding(View view) {
        btnLogin = view.findViewById(R.id.buttonLogin);
        txtEmail = view.findViewById(R.id.editTextEmail);
        txtPassword = view.findViewById(R.id.editTextPassword);
        inputLayoutEmail = view.findViewById(R.id.inputLayoutEmail);
        txtResetPassword = view.findViewById(R.id.textViewResetPassword);
    }

    @Override
    public void success() {
        Intent intentHome = new Intent(getActivity(), HomeActivity.class);
        startActivity(intentHome);
    }

    @Override
    public void fail() {
        Toast.makeText(getActivity(), "Đăng nhập thất bại!\nEmail hoặc mật khẩu nhập sai.", Toast.LENGTH_SHORT).show();
        txtPassword.setText("");
    }
}
