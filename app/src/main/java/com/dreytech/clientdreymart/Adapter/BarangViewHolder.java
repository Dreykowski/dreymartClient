package com.dreytech.clientdreymart.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreytech.clientdreymart.Interface.IItemClickListener;
import com.dreytech.clientdreymart.R;

public class BarangViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView img_product;
    TextView txt_barang_name, txt_price;

    IItemClickListener itemClickListener;

    ImageView btn_add_to_cart, btn_favorites;

    public void setItemClickListener(IItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public BarangViewHolder(@NonNull View itemView) {
        super(itemView);

        img_product = (ImageView)itemView.findViewById(R.id.image_product);
        txt_barang_name = (TextView)itemView.findViewById(R.id.txt_barang_name);
        txt_price = (TextView)itemView.findViewById(R.id.txt_price);
        btn_add_to_cart = (ImageView) itemView.findViewById(R.id.btn_add_cart);
        btn_favorites = (ImageView) itemView.findViewById(R.id.btn_favorite);


        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        itemClickListener.onClick(v);

    }
}
