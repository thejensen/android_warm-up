package com.epicodus.ransroad.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.epicodus.ransroad.models.Clothing;
import com.epicodus.ransroad.ui.ClothingListActivity;
import com.epicodus.ransroad.ui.R;

import org.parceler.Parcels;

import java.util.ArrayList;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jensese on 12/9/16.
 */

public class ClothingListAdapter extends RecyclerView.Adapter<ClothingListAdapter.ClothingListViewHolder> {
    public static final String TAG = ClothingListAdapter.class.getSimpleName();
    private ArrayList<Clothing> mClothingItems = new ArrayList<>();
    private Context mContext;

    public ClothingListAdapter(Context context, ArrayList<Clothing> clothingItems) {
        mContext = context;
        mClothingItems = clothingItems;
    }

    @Override
    public ClothingListAdapter.ClothingListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.clothing_list_item, parent, false);
        ClothingListViewHolder viewHolder = new ClothingListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ClothingListAdapter.ClothingListViewHolder holder, int position) {
        holder.bindClothingItem(mClothingItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mClothingItems.size();
    }


    public class ClothingListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.clothingItemTextView) TextView mClothingItemTextView;

        private Context mContext;

        public ClothingListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, ClothingListActivity.class);
            intent.putExtra("position", itemPosition);
            intent.putExtra("restaurants", Parcels.wrap(mClothingItems));
            mContext.startActivity(intent);
        }

        public void bindClothingItem(Clothing clothing) {
            mClothingItemTextView.setText(clothing.getItem());
        }
    }
}
