package com.example.windzlord.x_session7.recycler_view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.windzlord.x_session7.R;

/**
 * Created by WindzLord on 10/23/2016.
 */

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {

    // create new
    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_post, parent, false);

        PostViewHolder postViewHolder = new PostViewHolder(itemView);
        return postViewHolder;
    }

    // update
    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        holder.bind(Post.LIST.get(position));
    }

    // get count
    @Override
    public int getItemCount() {
        return Post.LIST.size();
    }
}
