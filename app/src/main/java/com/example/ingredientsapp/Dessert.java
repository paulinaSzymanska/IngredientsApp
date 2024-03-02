package com.example.ingredientsapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class Dessert extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dessert);
        Button generate_shop_list_button = findViewById(R.id.generate_shop_list_button);
        List<TextView> textViewsList = List.of(findViewById(R.id.provide1SiteDessert), findViewById(R.id.provide2SiteDessert),
                findViewById(R.id.provide3SiteDessert), findViewById(R.id.provide4SiteDessert),
                findViewById(R.id.provide5SiteDessert));

        generate_shop_list_button.setOnClickListener(view -> {
            Helper.saveContentOfPages(this, Helper.readValuesFromNumbersBoxes(textViewsList), "txtDessertCache");
            Intent intent = new Intent(this, ShopList.class);
            startActivity(intent);
        });
    }
}
