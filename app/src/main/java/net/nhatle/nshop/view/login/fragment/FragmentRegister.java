package net.nhatle.nshop.view.login.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.nhatle.nshop.R;
import net.nhatle.nshop.data.model.Customer;
import net.nhatle.nshop.presenter.login.RegisterPresenter;
import net.nhatle.nshop.view.login.IAuthView;

/**
 * Created by NhatLe on 25-Dec-17.
 */

public class FragmentRegister extends Fragment implements IAuthView, View.OnFocusChangeListener {
    Button btnRegister;
    EditText txtName;
    EditText txtPassword;
    EditText txtEmail;
    EditText txtPasswordConfirm;
    TextInputLayout inputLayoutName, inputLayoutPassword, inputLayoutEmail, inputLayoutPasswordConfirm;

    RegisterPresenter registerPresenter;
    Boolean validate = false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        binding(view);

        registerPresenter = new RegisterPresenter(this);
        final Customer customer = new Customer();
        txtName.setOnFocusChangeListener(this);
        txtEmail.setOnFocusChangeListener(this);
//        txtPassword.setOnFocusChangeListener(this);
        txtPasswordConfirm.setOnFocusChangeListener(this);
//        button register
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txtName.getText().toString();
                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();
//                String passwordConfirm = txtPasswordConfirm.getText().toString();
                if (validate)
                {
                    customer.setName(name);
                    customer.setEmail(email);
                    customer.setPassword(password);
                    registerPresenter.ActionRegister(customer);
                }

            }
        });




        return view;
    }

    private void binding(View view) {
        btnRegister = view.findViewById(R.id.buttonRegister);
        txtName = view.findViewById(R.id.editTextName);
        txtPassword = view.findViewById(R.id.editTextPassword);
        txtEmail = view.findViewById(R.id.editTextEmail);
        txtPasswordConfirm = view.findViewById(R.id.editTextPasswordConfirm);
        inputLayoutName = view.findViewById(R.id.inputLayoutName);
        inputLayoutEmail = view.findViewById(R.id.inputLayoutEmail);
        inputLayoutPassword = view.findViewById(R.id.inputLayoutPassword);
        inputLayoutPasswordConfirm = view.findViewById(R.id.inputLayoutPasswordConfirm);
    }

    @Override
    public void success() {
        Toast.makeText(getActivity(), "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void fail() {
        Toast.makeText(getActivity(), "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        int idView = view.getId();
        String s = ((EditText) view).getText().toString();
        switch (idView) {
            case R.id.editTextName:
                if (!b) {

                    if (s.trim().equals("") || s.equals(null)) {
                        inputLayoutName.setErrorEnabled(true);
                        inputLayoutName.setError("Không được để trống!");
                        validate = false;
                    } else {
                        inputLayoutName.setErrorEnabled(false);
                        inputLayoutName.setError("");
                        validate = true;
                    }
                }
                break;
            case R.id.editTextEmail:
                if (!b) {
                    if (s.trim().equals("")) {
                        inputLayoutEmail.setErrorEnabled(true);
                        inputLayoutEmail.setError("Không được để trống!");
                    } else {
                        Boolean checkEmail = Patterns.EMAIL_ADDRESS.matcher(s).matches();
                        if (!checkEmail) {
                            inputLayoutEmail.setErrorEnabled(true);
                            inputLayoutEmail.setError("Email không đúng định dạng");
                            validate = false;
                        } else {
                            inputLayoutEmail.setErrorEnabled(false);
                            inputLayoutEmail.setError("");
                            validate = true;
                        }

                    }
                }
                break;
            case R.id.editTextPassword:
                break;
            case R.id.editTextPasswordConfirm:

                if (!b) {
                    String password = txtPassword.getText().toString();

                    if (s.equals(password)) {
                        inputLayoutPasswordConfirm.setErrorEnabled(false);
                        inputLayoutPasswordConfirm.setError("");
                        validate = true;
                    } else {
                        inputLayoutPasswordConfirm.setErrorEnabled(true);
                        inputLayoutPasswordConfirm.setError("Mật khẩu nhập lại không trùng khớp");
                        validate = false;
                    }
                }
                break;
        }
    }
}
