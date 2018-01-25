package net.nhatle.nshop.view.reset_password;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.nhatle.nshop.R;
import net.nhatle.nshop.presenter.reset_password.ResetPasswordPresenter;

import org.json.JSONException;

public class ResetPasswordActivity extends AppCompatActivity implements IResetPasswordView {

    EditText editTextEmail;
    Button btnResetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        binding();

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                if (email.trim().equals("")) {
                    Toast.makeText(ResetPasswordActivity.this, "Bạn chưa nhập email", Toast.LENGTH_SHORT).show();
                } else {
                    ResetPasswordPresenter resetPasswordPresenter = new ResetPasswordPresenter(ResetPasswordActivity.this);
                    try {
                        resetPasswordPresenter.actionResetPassword(email);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void binding() {
        editTextEmail  = findViewById(R.id.editTextEmail);
        btnResetPassword  = findViewById(R.id.buttonResetPassword);
    }

    @Override
    public void success() {
        Toast.makeText(this, "Mật khẩu đã được khổi phục.\nVui lòng kiểm tra email của bạn", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void error() {
        Toast.makeText(this, "Email của bạn không tồn tại trong hệ thống", Toast.LENGTH_SHORT).show();
    }
}
