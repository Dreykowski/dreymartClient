package com.dreytech.clientdreymart.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreytech.clientdreymart.BarangActivity;
import com.dreytech.clientdreymart.Interface.IItemClickListener;
import com.dreytech.clientdreymart.Model.Category;
import com.dreytech.clientdreymart.R;
import com.dreytech.clientdreymart.Utils.Common;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    Context context;
    List<Category> categories;

    public CategoryAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.menu_item_layout, null);
        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, final int position) {

        //Load Image
        Picasso.with(context)
                .load(categories.get(position).Link)
                .into(holder.img_product);

        holder.txt_menu_name.setText(categories.get(position).Name);

        holder.setItemClickListener(new IItemClickListener() {
            @Override
            public void onClick(View v) {
                Common.currentCategory = categories.get(position);

                //Start New Activity
                context.startActivity(new Intent(context, BarangActivity.class));

            }
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
