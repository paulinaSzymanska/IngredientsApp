package com.example.ingredientsapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;


public class Breakfast extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast);
        Button go_to_dinner_button = findViewById(R.id.confirm_button);
        Button generate_shop_list_button = findViewById(R.id.generate_shop_list_button);

        List<TextView> textViewsList = List.of(
                findViewById(R.id.provide1SiteBreakfast), findViewById(R.id.provide2SiteBreakfast),
                findViewById(R.id.provide3SiteBreakfast), findViewById(R.id.provide4SiteBreakfast),
                findViewById(R.id.provide5SiteBreakfast));

        go_to_dinner_button.setOnClickListener(view -> {
            Helper.saveContentOfPages(this, Helper.readValuesFromNumbersBoxes(textViewsList), R.raw.breakfast, "txtBreakfastCache");
            Intent intent = new Intent(this, Dinner.class);
            startActivity(intent);
        });

        generate_shop_list_button.setOnClickListener(view -> {
            Helper.saveContentOfPages(this, Helper.readValuesFromNumbersBoxes(textViewsList), R.raw.breakfast, "txtBreakfastCache");
            Intent intent = new Intent(this, ShopList.class);
            startActivity(intent);
        });
    }
}
