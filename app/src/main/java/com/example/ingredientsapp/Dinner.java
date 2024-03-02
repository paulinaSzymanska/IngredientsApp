package com.example.ingredientsapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;


public class Dinner extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinner);
        Button go_to_supper = findViewById(R.id.confirm_button);
        Button generate_shop_list_button = findViewById(R.id.generate_shop_list_button);
        List<TextView> textViewsList = List.of(findViewById(R.id.provide1SiteDinner), findViewById(R.id.provide2SiteDinner),
                findViewById(R.id.provide3SiteDinner), findViewById(R.id.provide4SiteDinner),
                findViewById(R.id.provide5SiteDinner));

        MainActivity.switchOfButtonWhenNoPdfElemInList(go_to_supper);

        go_to_supper.setOnClickListener(view -> {
            Helper.saveContentOfPages(this, Helper.readValuesFromNumbersBoxes(textViewsList), "txtDinnerCache");
            Intent intent = new Intent(this, Supper.class);
            startActivity(intent);
        });

        generate_shop_list_button.setOnClickListener(view -> {
            Helper.saveContentOfPages(this, Helper.readValuesFromNumbersBoxes(textViewsList), "txtDinnerCache");
            Intent intent = new Intent(this, ShopList.class);
            startActivity(intent);
        });
    }
}
