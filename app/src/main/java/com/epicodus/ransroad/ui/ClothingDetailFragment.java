package com.epicodus.ransroad.ui;


import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.ransroad.Constants;
import com.epicodus.ransroad.models.Clothing;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClothingDetailFragment extends Fragment implements View.OnClickListener {
    private static final int MAX_WIDTH = 400;
    private static final int MAX_HEIGHT = 300;

    @Bind(R.id.addToWishListButton) Button mAddToWishListButton;
    @Bind(R.id.clothingImageView) ImageView mImageLabel;
    @Bind(R.id.clothingNameTextView) TextView mNameLabel;
    @Bind(R.id.clothingDescriptionTextView) TextView mDescriptionLabel;

    private Clothing mClothing;

    public static ClothingDetailFragment newInstance(Clothing clothing) {
        ClothingDetailFragment clothingDetailFragment = new ClothingDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("clothing", Parcels.wrap(clothing));
        clothingDetailFragment.setArguments(args);
        return clothingDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClothing = Parcels.unwrap(getArguments().getParcelable("clothing"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clothing_detail, container, false);
        ButterKnife.bind(this, view);

        Picasso.with(view.getContext())
                .load(mClothing.getImageURL())
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .centerCrop()
                .into(mImageLabel);

        mNameLabel.setText(mClothing.getItem());
        mDescriptionLabel.setText(mClothing.getDescription());

        mAddToWishListButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == mAddToWishListButton) {
            DatabaseReference clothingItemRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_CLOTHING_ITEMS);
            clothingItemRef.push().setValue(mClothing);
            Toast.makeText(getContext(), "Saved to Wish List", Toast.LENGTH_SHORT).show();
        }
    }


}
