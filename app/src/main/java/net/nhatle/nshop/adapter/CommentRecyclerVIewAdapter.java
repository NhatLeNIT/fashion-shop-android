package net.nhatle.nshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.nhatle.nshop.R;
import net.nhatle.nshop.data.model.Comment;
import net.nhatle.nshop.data.model.Product;

import java.text.DateFormat;
import java.util.List;

/**
 * Created by NhatLe on 29-Dec-17.
 */

public class CommentRecyclerVIewAdapter extends RecyclerView.Adapter<CommentRecyclerVIewAdapter.ViewHolderLinearComment> {
    private Context context;
    private List<Comment> commentList;
    private int limit;
    public CommentRecyclerVIewAdapter(Context context, List<Comment> commentList, int limit) {
        this.context = context;
        this.commentList = commentList;
        this.limit = limit;
    }


    public class ViewHolderLinearComment extends RecyclerView.ViewHolder {
        TextView txtName;
        TextView txtContent;
        public ViewHolderLinearComment(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.textViewName);
            txtContent = itemView.findViewById(R.id.textViewContent);
        }
    }
    @Override
    public ViewHolderLinearComment onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_recycler_view_comment, parent , false);
        ViewHolderLinearComment viewHolderLinearComment = new ViewHolderLinearComment(view);
        return viewHolderLinearComment;
    }

    @Override
    public void onBindViewHolder(ViewHolderLinearComment holder, int position) {
        Comment comment = commentList.get(position);
        holder.txtName.setText(comment.getName());
        holder.txtContent.setText(comment.getContent());
    }

    @Override
    public int getItemCount() {
        int countItem = 0;
        if(commentList.size() < limit)
            countItem = commentList.size();
        else {
            if(limit != 0 )
                countItem = limit;
            else countItem = commentList.size();
        }
        return countItem;
    }


}
