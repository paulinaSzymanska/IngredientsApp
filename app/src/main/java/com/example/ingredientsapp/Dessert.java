package com.example.ingredientsapp;

import static com.example.ingredientsapp.Helper.readValuesFromNumbersBoxes;
import static com.example.ingredientsapp.Helper.saveContentOfPages;

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
            saveContentOfPages(this, readValuesFromNumbersBoxes(textViewsList), R.raw.dessert, "txtDessertCache");
            Intent intent = new Intent(this, ShopList.class);
            startActivity(intent);
        });
    }
}
