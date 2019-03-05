package com.dreytech.clientdreymart.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.dreytech.clientdreymart.Model.Barang;
import com.dreytech.clientdreymart.R;
import com.dreytech.clientdreymart.Utils.Common;

import java.util.List;

public class MultiChoiceAdapter extends RecyclerView.Adapter<MultiChoiceAdapter.MultiChoiceViewHolder>{

    Context context;
    List<Barang> optionList;

    public MultiChoiceAdapter(Context context, List<Barang> optionList) {
        this.context = context;
        this.optionList = optionList;
    }

    @NonNull
    @Override
    public MultiChoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.multi_check_layout,null);
        return new MultiChoiceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MultiChoiceViewHolder holder, final int position) {

        holder.checkBox.setText(optionList.get(position).Name);
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    Common.toppingAdded.add(buttonView.getText().toString());
                    Common.toppingPrice+=Double.parseDouble(optionList.get(position).Price);
                }
                else
                {
                    Common.toppingAdded.remove(buttonView.getText().toString());
                    Common.toppingPrice-=Double.parseDouble(optionList.get(position).Price);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return optionList.size();
    }

    class MultiChoiceViewHolder extends RecyclerView.ViewHolder{

        CheckBox checkBox;

        public MultiChoiceViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = (CheckBox)itemView.findViewById(R.id.ckb_topping);
        }
    }
}
