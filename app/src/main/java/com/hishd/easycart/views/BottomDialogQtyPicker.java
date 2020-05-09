package com.hishd.easycart.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.hishd.easycart.R;


public class BottomDialogQtyPicker extends BottomSheetDialogFragment {

    TextView txtProductName;
    ImageView imgProduct;
    Button btnReduce;
    Button btnAdd;
    TextView txtQty;
    TextView txtPrice;
    TextView txtTotal;
    Button btnAddToCart;
    double itemPrice = 0;
    String itemName;
    int imgDrawable = 0;
    double total = 0;
    int qty = 1;
    Context context;

    BottomQtyListener mBottomQtyListner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_bottom_pop_picker, container, false);
        txtProductName = view.findViewById(R.id.txtProductName);
        imgProduct = view.findViewById(R.id.imgProduct);
        btnReduce = view.findViewById(R.id.btnReduce);
        btnAdd = view.findViewById(R.id.btnAdd);
        txtQty = view.findViewById(R.id.txtQty);
        btnAddToCart = view.findViewById(R.id.btnAddToCart);
        txtPrice = view.findViewById(R.id.txtPrice);
        txtTotal = view.findViewById(R.id.txtTotal);

        if (itemName != null)
            txtProductName.setText(itemName);
        if (itemPrice != 0) {
            txtPrice.setText(String.valueOf(itemPrice));
            total = itemPrice;
            txtTotal.setText(String.valueOf(total));
        }
        if (imgDrawable != 0)
            imgProduct.setImageDrawable(context.getDrawable(imgDrawable));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (qty == 100)
                    return;
                qty += 1;
                txtQty.setText(String.valueOf(qty));
                total = total + itemPrice;
                txtTotal.setText(String.valueOf(total));
            }
        });

        btnReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (qty == 1)
                    return;
                qty -= 1;
                txtQty.setText(String.valueOf(qty));
                total = total - itemPrice;
                txtTotal.setText(String.valueOf(total));
            }
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomQtyListner.onAddToCartClicked(qty);
                dismiss();
            }
        });

        return view;
    }

    public void setItemData(Context context, String itemName, double itemPrice, int imgDrawable) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.imgDrawable = imgDrawable;
        this.context = context;
        total = 0;
        qty = 1;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mBottomQtyListner = (BottomQtyListener) context;
        } catch (ClassCastException ex) {
            throw new ClassCastException(context.toString() + " must be implemented from BottomQtyListener");
        }

    }

    public interface BottomQtyListener {
        void onAddToCartClicked(int qty);
    }
}
