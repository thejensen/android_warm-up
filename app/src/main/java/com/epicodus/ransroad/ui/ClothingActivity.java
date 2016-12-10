package com.epicodus.ransroad.ui;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.ransroad.Constants;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ClothingActivity extends AppCompatActivity{
    private DatabaseReference mClothingItemReference;

    @Bind(R.id.clothingTitleTextView) TextView mClothingTitleTextView;
    @Bind(R.id.clothingListView) ListView mClothingListView;
    private String[] clothings = new String[] {"Front and Rear lights", "Fenders", "Water bottle", "Medium weight wool socks", "Lightweight boots",
            "Long sleeve shirt or thin sweater", "Jeans", "Puffy jacket", "Gloves"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mClothingItemReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_CLOTHING_ITEM);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothing);
        ButterKnife.bind(this);

        Typeface seasideFont = Typeface.createFromAsset(getAssets(), "fonts/seaside_font.ttf");
        mClothingTitleTextView.setTypeface(seasideFont);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, clothings);
        mClothingListView.setAdapter(adapter);

        mClothingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String clothing = ((TextView)view).getText().toString();
                Toast.makeText(ClothingActivity.this, clothing + " saved to 'Wish List'", Toast.LENGTH_LONG).show();
                saveClothingItemToFirebase(clothing);
            }
        });
    }

    public void saveClothingItemToFirebase(String clothing) {
        mClothingItemReference.push().setValue(clothing);
    }

}
