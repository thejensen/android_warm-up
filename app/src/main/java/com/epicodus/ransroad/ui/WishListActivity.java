package com.epicodus.ransroad.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.epicodus.ransroad.Constants;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WishListActivity extends AppCompatActivity {
    private static final String TAG = WishListActivity.class.getSimpleName();
    private DatabaseReference mClothingItemReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    @Bind(R.id.itemListRecyclerView) RecyclerView mItemListRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);
        ButterKnife.bind(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        mClothingItemReference = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_CLOTHING_ITEM)
                .child(uid);
        Log.v(TAG, "Link to database id for clothing item: " + mClothingItemReference);
    }

}
