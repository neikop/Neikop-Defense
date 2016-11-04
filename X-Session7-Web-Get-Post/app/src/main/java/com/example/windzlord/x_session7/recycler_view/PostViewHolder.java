package com.example.windzlord.x_session7.recycler_view;

import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.windzlord.x_session7.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WindzLord on 10/23/2016.
 */

public class PostViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.textView_title)
    public TextView textViewTitle;

    @BindView(R.id.textView_content)
    public TextView textViewContent;

    public PostViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Post post) {
        textViewTitle.setText(post.getTitle());
        textViewContent.setText(post.getContent());
    }
}
