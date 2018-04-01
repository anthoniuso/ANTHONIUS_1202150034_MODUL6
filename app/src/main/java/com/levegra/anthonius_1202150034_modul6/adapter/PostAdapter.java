package com.levegra.anthonius_1202150034_modul6.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.levegra.anthonius_1202150034_modul6.CommentPostActivity;
import com.levegra.anthonius_1202150034_modul6.R;
import com.levegra.anthonius_1202150034_modul6.model.Post;


import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    Context context;
    ArrayList<Post> postsList;

    public PostAdapter(Context context, ArrayList<Post> postsList) {
        this.context = context;
        this.postsList = postsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Post post = postsList.get(position);

        holder.mUsername.setText(post.getUsername());
        Glide.with(context).load(post.getImage()).into(holder.mImagePost);
        holder.mTitlePost.setText(post.getTitlePost());
        holder.mPost.setText(post.getPost());

        holder.cardViewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, CommentPostActivity.class);
                i.putExtra("id", post.getId());
                i.putExtra("username", post.getUsername());
                i.putExtra("image", post.getImage());
                i.putExtra("title", post.getTitlePost());
                i.putExtra("id", post.getTitlePost());
                i.putExtra("desc", post.getPost());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mUsername;
        TextView mTitlePost;
        TextView mPost;
        ImageView mImagePost;
        CardView cardViewPost;

        public ViewHolder(View itemView) {
            super(itemView);

            mUsername = itemView.findViewById(R.id.tv_username);
            mTitlePost = itemView.findViewById(R.id.tv_title_post);
            mPost = itemView.findViewById(R.id.tv_post);
            mImagePost = itemView.findViewById(R.id.img_post);
            cardViewPost = itemView.findViewById(R.id.cvPost);
        }
    }
}
