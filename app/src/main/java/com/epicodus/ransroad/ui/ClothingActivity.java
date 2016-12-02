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

import butterknife.Bind;
import butterknife.ButterKnife;

public class ClothingActivity extends AppCompatActivity{
    @Bind(R.id.clothingTitleTextView) TextView mClothingTitleTextView;
    @Bind(R.id.clothingListView) ListView mClothingListView;
    private String[] clothings = new String[] {"Front and Rear lights", "Fenders", "Water bottle", "Medium weight wool socks", "Lightweight boots",
            "Long sleeve shirt or thin sweater", "Jeans", "Puffy jacket", "Gloves"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                Toast.makeText(ClothingActivity.this, clothing, Toast.LENGTH_LONG).show();
            }
        });
    }

}
