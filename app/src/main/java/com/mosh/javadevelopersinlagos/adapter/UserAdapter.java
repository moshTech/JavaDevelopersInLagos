package com.mosh.javadevelopersinlagos.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mosh.javadevelopersinlagos.R;
import com.mosh.javadevelopersinlagos.UserDetails;
import com.mosh.javadevelopersinlagos.model.User;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private static final String TAG = "UserAdapter";
    private List<User> userList;
    private Context mContext;

     public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView user_profile_avatar; //To use circle images
        TextView username, user_state;


        MyViewHolder(View view) {
            super(view);
            user_profile_avatar = view.findViewById(R.id.profile_image);
            username =  view.findViewById(R.id.user_name);
            user_state = view.findViewById(R.id.userstate);

        }
    }


    public UserAdapter(Context mContext, List<User> userList) {
        this.userList = userList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.userlist_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder:  called.");
        final User user = userList.get(position);

        holder.username.setText(user.getLogin());
        holder.user_state.setText(R.string.state);


        //Loading the image using Glide
         mContext = holder.user_profile_avatar.getContext();
        Glide.with(mContext).load(user.getAvatarUrl()).into(holder.user_profile_avatar);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext  = holder.itemView.getContext();
                Intent intent = new Intent(mContext, UserDetails.class);
                intent.putExtra("user", user);
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
