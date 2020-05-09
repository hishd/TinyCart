package com.hishd.easycart;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hishd.easycart.model.CartItem;
import com.hishd.easycart.model.CartItemAdapter;
import com.hishd.easycart.model.ItemOffsetDecoration;
import com.hishd.easycart.model.ProductItem;
import com.hishd.tinycart.exceptions.ProductNotFoundException;
import com.hishd.tinycart.exceptions.QuantityInvalidException;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.model.Item;
import com.hishd.tinycart.util.TinyCartHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Activity_checkout extends AppCompatActivity implements CartItemAdapter.OnCartItemListener {

    RecyclerView cartItemList;
    CartItemAdapter cartItemAdapter;
    List<CartItem> itemList;
    TextView txtTotalAmount;
    Cart cart;
    Button btnClose;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkuot);

        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_slide_right);

        cartItemList = findViewById(R.id.cartItemList);
        cartItemList.setLayoutAnimation(controller);
        txtTotalAmount = findViewById(R.id.txtTotalAmount);
        btnClose = findViewById(R.id.btnClose);
        cartItemList.setLayoutManager(new LinearLayoutManager(this));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen._10sdp);
        cartItemList.addItemDecoration(itemDecoration);
        itemList = new ArrayList<>();
        cart = TinyCartHelper.getCart();

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        itemList = getCartItems();
        cartItemAdapter = new CartItemAdapter(itemList, this, cart, this);
        cartItemList.setAdapter(cartItemAdapter);

        txtTotalAmount.setText(cart.getTotalPrice().toString());
    }

    private List<CartItem> getCartItems() {
        List<CartItem> cartItems = new ArrayList<>();
        Map<Item, Integer> cartMap = cart.getAllItemsWithQty();

        for (Map.Entry<Item, Integer> entry : cartMap.entrySet()) {
            CartItem cartItem = new CartItem((ProductItem) entry.getKey(), entry.getValue());
            cartItems.add(cartItem);
        }

        return cartItems;
    }

    @Override
    public void onQuantityReduced(int position) {
        try {
            cart.updateItem(itemList.get(position).getProductItem(), cart.getItemQty(itemList.get(position).getProductItem()) - 1);
            itemList.get(position).setQty(cart.getItemQty(itemList.get(position).getProductItem()));
            cartItemAdapter.notifyItemChanged(position);
            txtTotalAmount.setText(cart.getTotalPrice().toString());
        } catch (ProductNotFoundException ex) {
            Toast.makeText(this, "The Product is not found on the cart", Toast.LENGTH_SHORT).show();
        } catch (QuantityInvalidException ex) {
            Toast.makeText(this, "Please remove the item.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onQuantityIncreased(int position) {
        try {
            cart.updateItem(itemList.get(position).getProductItem(), cart.getItemQty(itemList.get(position).getProductItem()) + 1);
            itemList.get(position).setQty(cart.getItemQty(itemList.get(position).getProductItem()));
            cartItemAdapter.notifyItemChanged(position);
            txtTotalAmount.setText(cart.getTotalPrice().toString());
        } catch (ProductNotFoundException ex) {
            Toast.makeText(this, "The Product is not found on the cart", Toast.LENGTH_SHORT).show();
        } catch (QuantityInvalidException ex) {
            Toast.makeText(this, "Please remove the item.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemRemoved(int position) {
        try {
            cart.removeItem(itemList.get(position).getProductItem());
            itemList.remove(position);
            cartItemAdapter.notifyItemRemoved(position);
            txtTotalAmount.setText(cart.getTotalPrice().toString());
        } catch (ProductNotFoundException ex) {
            Toast.makeText(this, "The Product is not found on the cart", Toast.LENGTH_SHORT).show();
        }
    }
}
