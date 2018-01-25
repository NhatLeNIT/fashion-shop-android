package net.nhatle.nshop.view.comment;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.nhatle.nshop.R;
import net.nhatle.nshop.data.model.Comment;
import net.nhatle.nshop.data.model.Customer;
import net.nhatle.nshop.presenter.product.CommentPresenter;

public class CommentActivity extends AppCompatActivity  implements ICommentView, View.OnClickListener{

    EditText editTextContent;
    Button btnSubmit;
    TextInputLayout inputLayoutContent;
    int productId;
    int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        binding();

        Intent intent = getIntent();
         productId = intent.getIntExtra("productId", 0);
          userId = intent.getIntExtra("userId", 0);
//        Log.d("111", productId + " - " + userId);
        btnSubmit.setOnClickListener(this);
    }

    private void binding() {
        editTextContent = findViewById(R.id.editTextContent);
        btnSubmit = findViewById(R.id.buttonSubmit);
        inputLayoutContent = findViewById(R.id.textInputLayoutContent);
    }

    @Override
    public void commentSuccess() {
        Toast.makeText(this, "Đăng bình luận thành công!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void commentFail() {
        Toast.makeText(this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.buttonSubmit) {
            String content = editTextContent.getText().toString();
            if(content.trim().length() > 0) {
                Comment comment = new Comment();
                comment.setIdUser(userId);
                comment.setIdProduct(productId);
                comment.setContent(content);
                CommentPresenter commentPresenter = new CommentPresenter(this);
                commentPresenter.postComment(comment);
                finish();
            }
            else {
                inputLayoutContent.setErrorEnabled(true);
                inputLayoutContent.setError("Bạn chưa nhập nội dung");
            }

        }
    }
}
