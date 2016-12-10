package com.epicodus.ransroad.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.epicodus.ransroad.models.Clothing;
import com.epicodus.ransroad.ui.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jensese on 12/9/16.
 */

public class WishListItemAdapter extends RecyclerView.Adapter<WishListItemAdapter.WishListItemViewHolder> {
    private ArrayList<Clothing> mClothingItems = new ArrayList<>();
    private Context mContext;

    public WishListItemAdapter(Context context, ArrayList<Clothing> clothingItems) {
        mContext = context;
        mClothingItems = clothingItems;
    }

    @Override
    public WishListItemAdapter.WishListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wish_list_item, parent, false);
        WishListItemViewHolder viewHolder = new WishListItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WishListItemAdapter.WishListItemViewHolder holder, int position) {
        holder.bindWishListItems(mClothingItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mClothingItems.size();
    }

    public class WishListItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.wishListItemTextView) TextView mWishListItemTextView;

        private Context mContext;

        public WishListItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        public void bindWishListItems(Clothing clothing) {
            mWishListItemTextView.setText(clothing.getItem());
        }
    }
}
