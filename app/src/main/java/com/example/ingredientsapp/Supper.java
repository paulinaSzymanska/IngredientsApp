package com.example.ingredientsapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;


public class Supper extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supper);
        Button go_to_dessert = findViewById(R.id.go_to_dessert);
        Button generate_shop_list_button = findViewById(R.id.generate_shop_list_button);
        List<TextView> textViewsList = List.of(findViewById(R.id.provide1SiteSupper), findViewById(R.id.provide2SiteSupper),
                findViewById(R.id.provide3SiteSupper), findViewById(R.id.provide4SiteSupper),
                findViewById(R.id.provide5SiteSupper));

        MainActivity.switchOfButtonWhenNoPdfElemInList(go_to_dessert);

        go_to_dessert.setOnClickListener(view -> {
            Helper.saveContentOfPages(this, Helper.readValuesFromNumbersBoxes(textViewsList), R.raw.supper, "txtSupperCache");
            Intent intent = new Intent(this, Dessert.class);
            startActivity(intent);
        });

        generate_shop_list_button.setOnClickListener(view -> {
            Helper.saveContentOfPages(this, Helper.readValuesFromNumbersBoxes(textViewsList), R.raw.supper, "txtSupperCache");
            Intent intent = new Intent(this, ShopList.class);
            startActivity(intent);
        });
    }

}
