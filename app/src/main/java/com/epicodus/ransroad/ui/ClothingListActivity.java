package com.epicodus.ransroad.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
    private SharedPreferences mSharedPreferences;

    @Bind(R.id.clothingRecyclerView) RecyclerView mClothingRecyclerView;
    @Bind(R.id.wishListButton) Button mWishListButton;

    private ClothingListAdapter mAdapter;
    public ArrayList<Clothing> mClothingItems = new ArrayList<>();
    private DatabaseReference mClothingItemReference;

    @Bind(R.id.clothingTitleTextView) TextView mClothingTitleTextView;
    @Bind(R.id.wishListTitleTextView) TextView mWishListTitleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Clothing item1 = new Clothing("Front and rear lights", "Front and rear lights are important to visibility in the dark.", "http://cyclesecrets.com/wp-content/uploads/2015/10/d9d707ee781c.jpg");
        Clothing item2 = new Clothing("Fenders", "Fenders, aka mudguards, attach to your wheels to shield you from the backsplash of water on the ground.", "http://www.sheldonbrown.com/images/rambouillet_frontquarter.jpg");
        Clothing item3 = new Clothing("Water bottle", "A water bottle is a must! Even just a couple of miles can wear you out, if you hit an unexpected hill, or you're just plain dehydrated already.", "http://sulmlgnltlyql7h5ue629b1.wpengine.netdna-cdn.com/wp-content/uploads/2008/06/4383-water_bottle_lineup.jpg");
        Clothing item4 = new Clothing("Medium weight wool socks", "Your feet sweat. While it seems counterintuitive to wear wool socks in anticipation of your feet sweating, let me tell you: sweaty feet get COLD. Wool helps your feet stay warm even if your socks are wet.", "http://www.switchbacktravel.com/sites/default/files/styles/inline_790__responsive_full_width_/public/image_fields/field_imgs_inline/Hiking%20socks.jpg?itok=PFrQllB_");
        Clothing item5 = new Clothing("Synthetic top", "If you're sweating, and it's windy, you want a shirt that will dry quickly. Honestly, you can go with a shirt or ...a dress! I promise dresses work well for biking. ;)", "https://s-media-cache-ak0.pinimg.com/564x/79/b5/f0/79b5f01717f2d819ffbc80a8ad28ce0b.jpg");
        Clothing item6 = new Clothing("Stretchy jeans", "Jeans everyday! If they've got some stretch, they won't dig into your middle as much, and they'll not get so worn so quickly.", "https://momentummag.com/wp-content/uploads/2015/04/STYLE_Jeans_Photo-David-Niddrie-3646-WEB.jpg");
        Clothing item7 = new Clothing("Puffy jacket", "Ok, so puffy jackets are brilliant. They are light, warm, stowable, and many times they're water resistant. Cannot recommend enough.", "http://www.backcountry.com/wp-content/uploads/2013/11/LoveYourPuffy_Stuffed.jpg");
        Clothing item8 = new Clothing("Gloves", "Gloves are a must have, under 60 degrees. Forgetting your gloves can really make things hard on yourself. Please don't lose them!", "https://isadoreapparel.com/media/2015/10/3/3/merino-cycling-gloves-3295-w1170-h780-crop-flags1.jpg");
        Clothing item9 = new Clothing("Lightweight boots", "For cycling between 40-60 degrees, lightweight, waterproof boots are your best friend. They are amazing in the rain, out of the rain, and look great with almost any outfit.", "https://s-media-cache-ak0.pinimg.com/236x/27/35/17/273517f4a821c8e12cab8c00848db708.jpg");
        Clothing item10 = new Clothing("Ear warmers", "Under 55 degrees, ear warmers make a big difference on the bike. It's funny, you don't truly notice how much better it is to bike with them til you've got them on.", "https://img1.etsystatic.com/140/0/9842746/il_570xN.854256239_c2ki.jpg");
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
                .child(Constants.FIREBASE_CHILD_CLOTHING_ITEMS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothing);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        mWishListTitleTextView.setVisibility(View.GONE);

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
            if (mSharedPreferences.contains(Constants.PREFERENCES_AUTHENTICATED)) {
                Intent intent = new Intent(ClothingListActivity.this, WishListActivity.class);
                startActivity(intent);
            } else {
                alertUserToLogin();
            }

        }
    }

    private void alertUserToLogin() {
        LoginDialogFragment dialogFragment = new LoginDialogFragment();
        dialogFragment.show(getFragmentManager(), "login_dialog");
    }
}
