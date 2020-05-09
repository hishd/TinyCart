package com.hishd.easycart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hishd.easycart.model.ItemOffsetDecoration;
import com.hishd.easycart.model.ProductItem;
import com.hishd.easycart.model.ProductItemAdapter;
import com.hishd.easycart.views.BottomDialogQtyPicker;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.util.TinyCartHelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ProductItemAdapter.OnProductItemListener, BottomDialogQtyPicker.BottomQtyListener {

    RecyclerView productList;
    ProductItemAdapter productItemAdapter;
    List<ProductItem> productItemList;

    TextView txtItemName, txtItemPrice;
    BottomDialogQtyPicker bottomDialogQtyPicker;

    Button btnViewCart;

    Cart cart;
    int selectedItemPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_fall_down);

        productList = findViewById(R.id.productList);
        productList.setLayoutAnimation(controller);
        btnViewCart = findViewById(R.id.btnViewCart);

        productList.setLayoutManager(new GridLayoutManager(this, 2));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen._10sdp);
        productList.addItemDecoration(itemDecoration);
        productItemList = new ArrayList<>();

        bottomDialogQtyPicker = new BottomDialogQtyPicker();

        cart = TinyCartHelper.getCart();

        final int[] productImages = {R.drawable.dummy_store_item, R.drawable.dummy_store_item_2, R.drawable.dummy_store_item_3, R.drawable.dummy_store_item_4, R.drawable.dummy_store_item_5, R.drawable.dummy_store_item_6};
        final String[] productNames = {"Macbook Pro 2018", "Amazom Alexa", "Ticwatch S", " Applewatch Series 4", "Apple Homepod", "Mi Mix 2"};
        final double[] productPrice = {250000, 20000, 18000, 54000, 55000, 85000};

        for (int i = 0; i < productNames.length; i++) {
            productItemList.add(new ProductItem(productNames[i], BigDecimal.valueOf(productPrice[i]), "", productImages[i]));
        }

        productItemAdapter = new ProductItemAdapter(productItemList, this, this);
        productList.setAdapter(productItemAdapter);

        btnViewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Activity_checkout.class));
            }
        });
    }

    @Override
    public void onProductClick(View v, int position) {
//        cart.addItem(productItemList.get(position),10);
//        Toast.makeText(this,String.valueOf(cart.getTotalPrice()),Toast.LENGTH_SHORT).show();
        selectedItemPosition = position;
        bottomDialogQtyPicker.setItemData(this,productItemList.get(position).getItemName(),productItemList.get(position).getItemPrice().doubleValue(),productItemList.get(position).getImgDrawable());
        bottomDialogQtyPicker.show(getSupportFragmentManager(),"Bottom Dialog Fragment");
    }

    @Override
    public void onAddToCartClicked(int qty) {
        cart.addItem(productItemList.get(selectedItemPosition),qty);
        Toast.makeText(this,"Item added to the Cart",Toast.LENGTH_SHORT).show();
    }
}
