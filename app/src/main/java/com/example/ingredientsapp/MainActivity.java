package com.example.ingredientsapp;

import static com.example.ingredientsapp.Helper.deleteDirectory;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button load_recipes = findViewById(R.id.load_recipes);
        Button chooseBreakFastFile = findViewById(R.id.breakfastButton);

        load_recipes.setOnClickListener(view -> {
            Intent intent = new Intent(this, Breakfast.class);
            startActivity(intent);
        });

        chooseBreakFastFile.setOnClickListener(view -> {
            //  android.content.ActivityNotFoundException: No Activity found to handle Intent {
            //  act=android.intent.action.GET_CONTENT }
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            startActivity(intent);
        });

        deleteDirectory(getCacheDir());
        getCacheDir().mkdirs();
    }
}