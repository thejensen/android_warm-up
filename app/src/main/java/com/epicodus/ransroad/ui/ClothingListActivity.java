package com.epicodus.ransroad.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.epicodus.ransroad.Constants;
import com.epicodus.ransroad.adapter.ClothingListAdapter;
import com.epicodus.ransroad.models.Clothing;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ClothingListActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.clothingRecyclerView) RecyclerView mClothingRecyclerView;
    @Bind(R.id.wishListButton) Button mWishListButton;

    private ClothingListAdapter mAdapter;

    public ArrayList<Clothing> mClothingItems = new ArrayList<>();

    private DatabaseReference mClothingItemReference;

    @Bind(R.id.clothingTitleTextView) TextView mClothingTitleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Clothing item1 = new Clothing("Front and rear lights", "Front and rear lights are important to visibility in the dark.", "http://cyclesecrets.com/wp-content/uploads/2015/10/d9d707ee781c.jpg");
        Clothing item2 = new Clothing("Fenders", "Fenders, aka mudguards, attach to your wheels to shield you from the backsplash of water on the ground.", "http://www.sheldonbrown.com/images/rambouillet_frontquarter.jpg");
        Clothing item3 = new Clothing("Water bottle", "", "");
        Clothing item4 = new Clothing("Medium weight wool socks", "", "");
        Clothing item5 = new Clothing("Long sleeve shirt", "", "");
        Clothing item6 = new Clothing("Stretchy jeans", "", "");
        Clothing item7 = new Clothing("Puffy jacket", "", "");
        Clothing item8 = new Clothing("gloves", "", "");
        Clothing item9 = new Clothing("Lightweight boots", "", "");
        Clothing item10 = new Clothing("Ear warmer", "", "");
        mClothingItems.add(item1);
        mClothingItems.add(item2);
        mClothingItems.add(item3);
        mClothingItems.add(item4);
        mClothingItems.add(item5);
        mClothingItems.add(item6);
        mClothingItems.add(item7);
        mClothingItems.add(item8);
        mClothingItems.add(item9);
        mClothingItems.add(item10);

        mClothingItemReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_CLOTHING_ITEM);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothing);
        ButterKnife.bind(this);

        Typeface seasideFont = Typeface.createFromAsset(getAssets(), "fonts/seaside_font.ttf");
        mClothingTitleTextView.setTypeface(seasideFont);

        mAdapter = new ClothingListAdapter(getApplicationContext(), mClothingItems);
        mClothingRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(ClothingListActivity.this);
        mClothingRecyclerView.setLayoutManager(layoutManager);
        mClothingRecyclerView.setHasFixedSize(true);

        mWishListButton.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v == mWishListButton) {
            Intent intent = new Intent(ClothingListActivity.this, WishListActivity.class);
            startActivity(intent);
        }
////        How do I get a single object from the recyclerView? Is this something I can do? You can implement the onClickListener in the clothingListAdapter, and start an intent to send to this page with the individual clothing object in an arraylist???
//        if (v == ???) {
//            DatabaseReference restaurantRef = FirebaseDatabase
//                    .getInstance()
//                    .getReference(Constants.FIREBASE_CHILD_CLOTHING_ITEM);
//            restaurantRef.push().setValue(???);
//            String clothing = (???).getText().toString();
//            Toast.makeText(ClothingListActivity.this, clothing + " saved to 'Wish List'", Toast.LENGTH_LONG).show();
//            saveClothingItemToFirebase(clothing);
//        }
    }

    public void saveClothingItemToFirebase(String clothing) {
        mClothingItemReference.push().setValue(clothing);
    }

}
