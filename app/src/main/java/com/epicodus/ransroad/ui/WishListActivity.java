package com.epicodus.ransroad.ui;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.epicodus.ransroad.Constants;
import com.epicodus.ransroad.adapter.FirebaseClothingViewHolder;
import com.epicodus.ransroad.models.Clothing;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WishListActivity extends AppCompatActivity {
    private DatabaseReference mClothingItemReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    @Bind(R.id.clothingRecyclerView) RecyclerView mClothingRecyclerView;
    @Bind(R.id.clothingTitleTextView) TextView mClothingTitleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_clothing);
        ButterKnife.bind(this);

        Typeface seasideFont = Typeface.createFromAsset(getAssets(), "fonts/seaside_font.ttf");
        mClothingTitleTextView.setTypeface(seasideFont);

        mClothingItemReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_CLOTHING_ITEMS);
        setUpFirebaseAdapter();
    }

    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Clothing, FirebaseClothingViewHolder>
                (Clothing.class, R.layout.clothing_list_item, FirebaseClothingViewHolder.class,
                        mClothingItemReference) {

            @Override
            protected void populateViewHolder(FirebaseClothingViewHolder viewHolder,
                                              Clothing model, int position) {
                viewHolder.bindClothingItem(model);
            }
        };
        mClothingRecyclerView.setHasFixedSize(true);
        mClothingRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mClothingRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }
}
