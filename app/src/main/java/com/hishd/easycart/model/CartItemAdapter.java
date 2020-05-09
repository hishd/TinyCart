package com.hishd.easycart.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hishd.easycart.R;
import com.hishd.tinycart.model.Cart;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {

    List<CartItem> cartItemList;
    Context context;
    CartItem cartItem;
    Cart cart;
    OnCartItemListener mOnCartItemListener;

    public CartItemAdapter(List<CartItem> cartItemList, Context context, Cart cart, OnCartItemListener onCartItemListener) {
        this.cartItemList = cartItemList;
        this.context = context;
        this.cart = cart;
        this.mOnCartItemListener = onCartItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_cart_item,parent,false);
        return new ViewHolder(view, mOnCartItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        cartItem = cartItemList.get(position);
        holder.imgItem.setImageDrawable(context.getDrawable(cartItem.getProductItem().getImgDrawable()));
        holder.txtItemName.setText(cartItem.getProductItem().getProductName());
        holder.txtItemPrice.setText(cartItem.getProductItem().getProductPrice().toString());
        holder.txtQty.setText(String.valueOf(cartItem.getQty()));
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imgItem;
        TextView txtItemName;
        TextView txtItemPrice;
        Button btnReduce;
        TextView txtQty;
        Button btnAdd;
        Button btnRemove;
        OnCartItemListener onCartItemListener;

        public ViewHolder(@NonNull View itemView, final OnCartItemListener onCartItemListener) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imgItem);
            txtItemName = itemView.findViewById(R.id.txtItemName);
            txtItemPrice = itemView.findViewById(R.id.txtItemPrice);
            btnReduce = itemView.findViewById(R.id.btnReduce);
            txtQty = itemView.findViewById(R.id.txtQty);
            btnAdd = itemView.findViewById(R.id.btnAdd);
            btnRemove = itemView.findViewById(R.id.btnRemove);
            this.onCartItemListener = onCartItemListener;
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCartItemListener.onQuantityIncreased(getAdapterPosition());
                }
            });
            btnReduce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCartItemListener.onQuantityReduced(getAdapterPosition());
                }
            });
            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCartItemListener.onItemRemoved(getAdapterPosition());
                }
            });
        }
    }

    public interface OnCartItemListener{
        void onQuantityReduced(int position);
        void onQuantityIncreased(int position);
        void onItemRemoved(int position);
    }
}
