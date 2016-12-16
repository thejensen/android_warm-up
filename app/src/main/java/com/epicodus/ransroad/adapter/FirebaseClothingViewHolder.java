package com.epicodus.ransroad.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.epicodus.ransroad.models.Clothing;
import com.epicodus.ransroad.ui.R;

/**
 * Created by jensese on 12/9/16.
 */

public class FirebaseClothingViewHolder extends RecyclerView.ViewHolder {
    View mView;
    Context mContext;

    public FirebaseClothingViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
    }

    public void bindClothingItem(Clothing clothingItem) {
        TextView clothingItemTextView = (TextView) mView.findViewById(R.id.clothingItemTextView);
        clothingItemTextView.setText(clothingItem.getItem());
    }
}
