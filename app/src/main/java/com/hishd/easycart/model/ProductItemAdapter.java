package com.hishd.easycart.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hishd.easycart.R;

import java.util.List;

public class ProductItemAdapter extends RecyclerView.Adapter<ProductItemAdapter.ViewHolder> {

    List<ProductItem> productItemList;
    Context context;
    ProductItem productItem;
    OnProductItemListener mOnProductItemListener;

    public ProductItemAdapter(List<ProductItem> productItemList, Context context, OnProductItemListener onProductItemListener) {
        this.productItemList = productItemList;
        this.context = context;
        this.mOnProductItemListener = onProductItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_seller_store_item,parent,false);
        return new ViewHolder(view, mOnProductItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        productItem = productItemList.get(position);

        holder.imgItem.setImageDrawable(context.getDrawable(productItem.getImgDrawable()));
        holder.txtItemName.setText(productItem.getProductName());
        holder.txtItemPrice.setText(productItem.getProductPrice().toString());
    }

    @Override
    public int getItemCount() {
        return productItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgItem;
        TextView txtItemName;
        TextView txtItemPrice;
        OnProductItemListener onProductItemListener;

        public ViewHolder(@NonNull View itemView, OnProductItemListener onProductItemListener) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imgItem);
            txtItemName = itemView.findViewById(R.id.txtItemName);
            txtItemPrice = itemView.findViewById(R.id.txtItemPrice);
            this.onProductItemListener = onProductItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onProductItemListener.onProductClick(v, getAdapterPosition());
        }
    }

    public interface OnProductItemListener{
        void onProductClick(View v, int position);
    }
}
