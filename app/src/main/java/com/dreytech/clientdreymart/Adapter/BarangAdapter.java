package com.dreytech.clientdreymart.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.dreytech.clientdreymart.Database.ModelDB.Cart;
import com.dreytech.clientdreymart.Database.ModelDB.Favorite;
import com.dreytech.clientdreymart.Interface.IItemClickListener;
import com.dreytech.clientdreymart.Model.Barang;
import com.dreytech.clientdreymart.R;
import com.dreytech.clientdreymart.Utils.Common;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BarangAdapter extends RecyclerView.Adapter<BarangViewHolder> {

    Context context;
    List<Barang> barangList;

    public BarangAdapter(Context context, List<Barang> barangList) {
        this.context = context;
        this.barangList = barangList;
    }

    @NonNull
    @Override
    public BarangViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.barang_item_layout, null);

        return new BarangViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final BarangViewHolder holder, final int position) {

        holder.txt_price.setText(new StringBuilder("Rp").append(barangList.get(position).Price).toString());
        holder.txt_barang_name.setText(barangList.get(position).Name);

        holder.btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddToCartDialog(position);
            }
        });

        Picasso.with(context)
                .load(barangList.get(position).Link)
                .into(holder.img_product);

        holder.setItemClickListener(new IItemClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
            }
        });

        //Sistem Favorite
        if (Common.favoriteRepository.isFavorite(Integer.parseInt(barangList.get(position).ID))==1)
            holder.btn_favorites.setImageResource(R.drawable.ic_favorite_red_24dp);
        else
            holder.btn_favorites.setImageResource(R.drawable.ic_favorite_border_red_24dp);

        holder.btn_favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.favoriteRepository.isFavorite(Integer.parseInt(barangList.get(position).ID))!=1)
                {
                    addOrRemoveFavorite(barangList.get(position),true);
                    holder.btn_favorites.setImageResource(R.drawable.ic_favorite_red_24dp);
                }
                else
                {

                    addOrRemoveFavorite(barangList.get(position),false);
                    holder.btn_favorites.setImageResource(R.drawable.ic_favorite_border_red_24dp);
                }
            }
        });

    }

    private void addOrRemoveFavorite(Barang barang, boolean isAdd) {
        Favorite favorite = new Favorite();
        favorite.id = barang.ID;
        favorite.link = barang.Link;
        favorite.name = barang.Name;
        favorite.price = barang.Price;
        favorite.menuId = barang.MenuId;

        if (isAdd)
            Common.favoriteRepository.insertFav(favorite);
        else
            Common.favoriteRepository.delete(favorite);

    }

    private void showAddToCartDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.add_to_cart_layout,null);

        //View
        ImageView img_product_dialog = (ImageView)itemView.findViewById(R.id.img_cart_product);
        final ElegantNumberButton txt_count = (ElegantNumberButton)itemView.findViewById(R.id.txt_count);
        TextView txt_product_dialog = (TextView)itemView.findViewById(R.id.txt_cart_product_name);

        EditText edt_comment = (EditText)itemView.findViewById(R.id.edt_comment);

        RadioButton rdi_sizeM = (RadioButton)itemView.findViewById(R.id.rdi_sizeM);
        RadioButton rdi_sizeL = (RadioButton)itemView.findViewById(R.id.rdi_sizeL);

        rdi_sizeM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    Common.sizeOfCup=0;
                }
            }
        });
        rdi_sizeL.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    Common.sizeOfCup=1;
                }
            }
        });

        RadioButton rdi_sugar_100 = (RadioButton)itemView.findViewById(R.id.rdi_sugar_100);
        RadioButton rdi_sugar_70 = (RadioButton)itemView.findViewById(R.id.rdi_sugar_70);
        RadioButton rdi_sugar_50 = (RadioButton)itemView.findViewById(R.id.rdi_sugar_50);
        RadioButton rdi_sugar_30 = (RadioButton)itemView.findViewById(R.id.rdi_sugar_30);
        RadioButton rdi_sugar_free = (RadioButton)itemView.findViewById(R.id.rdi_sugar_free);

        rdi_sugar_30.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    Common.sugar = 30;
                }
            }
        });

        rdi_sugar_50.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    Common.sugar = 50;
                }
            }
        });

        rdi_sugar_70.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    Common.sugar = 70;
                }
            }
        });

        rdi_sugar_100.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    Common.sugar = 100;
                }
            }
        });

        rdi_sugar_free.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    Common.sugar = 0;
                }
            }
        });

        RadioButton rdi_ice_100 = (RadioButton)itemView.findViewById(R.id.rdi_ice_100);
        RadioButton rdi_ice_70 = (RadioButton)itemView.findViewById(R.id.rdi_ice_70);
        RadioButton rdi_ice_50 = (RadioButton)itemView.findViewById(R.id.rdi_ice_50);
        RadioButton rdi_ice_30 = (RadioButton)itemView.findViewById(R.id.rdi_ice_30);
        RadioButton rdi_ice_free = (RadioButton)itemView.findViewById(R.id.rdi_ice_free);

        rdi_ice_30.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Common.ice = 30;
            }
        });

        rdi_ice_50.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Common.ice = 50;
            }
        });

        rdi_ice_70.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Common.ice = 70;
            }
        });

        rdi_ice_100.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Common.ice = 100;
            }
        });

        rdi_ice_free.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Common.ice = 0;
            }
        });


        RecyclerView recycler_topping = (RecyclerView)itemView.findViewById(R.id.recycler_topping);
        recycler_topping.setLayoutManager(new LinearLayoutManager(context));
        recycler_topping.setHasFixedSize(true);

        MultiChoiceAdapter adapter = new MultiChoiceAdapter(context,Common.toppingList);
        recycler_topping.setAdapter(adapter);

        //setData
        Picasso.with(context)
                .load(barangList.get(position).Link)
                .into(img_product_dialog);
        txt_product_dialog.setText(barangList.get(position).Name);

        builder.setView(itemView);
        builder.setNegativeButton("BELI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

//                if (Common.sizeOfCup == -1)
//                {
//                    Toast.makeText(context, "Tolong pilih size of cup", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if (Common.sugar == -1 )
//                {
//                    Toast.makeText(context, "Tolong pilih gula", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (Common.ice == -1)
//                {
//                    Toast.makeText(context, "Tolong pilih es", Toast.LENGTH_SHORT).show();
//                    return;
//                }


                showConfirmDialog(position,txt_count.getNumber()
                );
                dialog.dismiss();
            }
        });

        builder.show();

    }

    private void showConfirmDialog(final int position, final String number) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.confirm_add_to_cart_layout,null);

        ImageView img_product_dialog = (ImageView)itemView.findViewById(R.id.img_product);
        final TextView txt_product_dialog = (TextView)itemView.findViewById(R.id.txt_cart_product_name);
        TextView txt_product_price = (TextView)itemView.findViewById(R.id.txt_cart_product_price);
        TextView txt_sugar = (TextView)itemView.findViewById(R.id.txt_sugar);
        TextView txt_ice = (TextView)itemView.findViewById(R.id.txt_ice);
        final TextView txt_topping_extra = (TextView)itemView.findViewById(R.id.txt_topping_extra);

        //Set Data
        Picasso.with(context).load(barangList.get(position).Link).into(img_product_dialog);
        txt_product_dialog.setText(new StringBuilder(barangList.get(position).Name).append(" x")
        .append(number).toString());

        txt_ice.setText(new StringBuilder("Ice: ").append(Common.ice).append("%").toString());
        txt_sugar.setText(new StringBuilder("Sugar: ").append(Common.sugar).append("&").toString());

        double price = (Double.parseDouble(barangList.get(position).Price)* Double.parseDouble(number)) +Common.toppingPrice;

        if (Common.sizeOfCup == 1)
            price+=(3.0*Double.parseDouble(number)); // harga

        StringBuilder topping_final_comment = new StringBuilder("");
        for (String line:Common.toppingAdded)
            topping_final_comment.append(line).append("\n");

        txt_topping_extra.setText(topping_final_comment);

        final double finalPrice = Math.round(price);

        txt_product_price.setText(new StringBuilder("Rp. ").append(finalPrice));

        builder.setNegativeButton("KONFIRMASI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

                try {
                    Cart cartItem = new Cart();
                    cartItem.name = barangList.get(position).Name;
                    cartItem.amount = Integer.parseInt(number);
                    cartItem.ice = Common.ice;
                    cartItem.sugar = Common.sugar;
                    cartItem.price = finalPrice;
                    cartItem.size = Common.sizeOfCup;
                    cartItem.toppingExtras = txt_topping_extra.getText().toString();
                    cartItem.link = barangList.get(position).Link;

                    //Menambah ke Database
                    Common.cartRepository.insertToCart(cartItem);

                    Log.d("DREY_DEBUG", new Gson().toJson(cartItem));

                    Toast.makeText(context, "Penyimpanan berhasil", Toast.LENGTH_SHORT).show();
                }
                catch (Exception ex)
                {
                    Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setView(itemView);
        builder.show();


    }


    @Override
    public int getItemCount() {
        return barangList.size();
    }
}
