package com.example.memomate.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memomate.Models.Member;
import com.example.memomate.R;

import java.util.ArrayList;


public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder>
{
    Context context;
    ArrayList<Member> listMember;

    public MemberAdapter(Context context) {
        this.context = context;
    }
    public void setData(ArrayList<Member> list)
    {
        this.listMember = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_class_members, parent, false);
        return new MemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        Member members = listMember.get(position);
        if (members == null)
        {
            return;
        }
        holder.avatar.setImageResource(members.getAvatar());
        holder.userName.setText(members.getUserName());

    }

    @Override
    public int getItemCount() {
        if (listMember != null) {
            return listMember.size();
        }
        return 0;
    }

    public class MemberViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView userName;
        public MemberViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar);
            userName = itemView.findViewById(R.id.userName);
        }
    }
}