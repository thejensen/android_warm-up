package com.epicodus.ransroad.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.epicodus.ransroad.Constants;
import com.epicodus.ransroad.models.Clothing;
import com.epicodus.ransroad.ui.R;
import com.epicodus.ransroad.ui.WishListActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * Created by jensese on 12/9/16.
 */

public class FirebaseClothingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    View mView;
    Context mContext;

    public FirebaseClothingViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
//        itemView.setOnClickListener(this);
    }

    public void bindClothingItem(Clothing clothingItem) {
        TextView clothingItemTextView = (TextView) mView.findViewById(R.id.clothingItemTextView);
        clothingItemTextView.setText(clothingItem.getItem());
    }

    @Override
    public void onClick(View view) {
        final ArrayList<Clothing> clothingItems = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_CLOTHING_ITEMS);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    clothingItems.add(snapshot.getValue(Clothing.class));
                }

                int itemPosition = getLayoutPosition();

                Intent intent = new Intent(mContext, WishListActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("clothingItems", Parcels.wrap(clothingItems));

                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
