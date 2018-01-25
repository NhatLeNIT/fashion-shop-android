package net.nhatle.nshop.presenter.product;

import net.nhatle.nshop.data.api.CommentApi;
import net.nhatle.nshop.data.model.Comment;
import net.nhatle.nshop.view.comment.ICommentView;

/**
 * Created by NhatLe on 29-Dec-17.
 */

public class CommentPresenter implements ICommentPresenter {
    private ICommentView commentView;
    private CommentApi commentApi;

    public CommentPresenter(ICommentView commentView) {
        this.commentView = commentView;
        commentApi = new CommentApi();
    }

    @Override
    public void postComment(Comment comment) {
        boolean commentResult = commentApi.postComment(comment);
        if (commentResult) this.commentView.commentSuccess();
        else this.commentView.commentFail();
    }
}
