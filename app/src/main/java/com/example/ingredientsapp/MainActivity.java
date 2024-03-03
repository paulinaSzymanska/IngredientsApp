package com.example.ingredientsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.itextpdf.text.pdf.PdfReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int PICK_PDF_FILE = 1;

    static List<PdfReader> listOfPdfReaders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button load_recipes = findViewById(R.id.load_recipes);
        Button pdfBreakfastButton = findViewById(R.id.pdfBreakfast);
        Button pdfDinnerButton = findViewById(R.id.pdfDinner);
        Button pdfSupperButton = findViewById(R.id.pdfSupper);
        Button pdfDessertButton = findViewById(R.id.pdfDessert);
        load_recipes.setEnabled(false);

        load_recipes.setOnClickListener(view -> {
            Intent intent = new Intent(this, Breakfast.class);
            startActivity(intent);
        });

        pdfBreakfastButton.setOnClickListener(view -> {
            openFilePicker();
            pdfBreakfastButton.setEnabled(false);
            load_recipes.setEnabled(true);
        });

        pdfDinnerButton.setOnClickListener(view -> {
            openFilePicker();
            pdfDinnerButton.setEnabled(false);
            load_recipes.setEnabled(true);
        });

        pdfSupperButton.setOnClickListener(view -> {
            openFilePicker();
            pdfSupperButton.setEnabled(false);
            load_recipes.setEnabled(true);
        });

        pdfDessertButton.setOnClickListener(view -> {
            openFilePicker();
            pdfDessertButton.setEnabled(false);
            load_recipes.setEnabled(true);
        });

        Helper.deleteDirectory(getCacheDir());
        getCacheDir().mkdirs();
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, PICK_PDF_FILE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PDF_FILE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    PdfReader pdfReader = getPdfReader(uri);
                    listOfPdfReaders.add(pdfReader);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private PdfReader getPdfReader(Uri uri) throws IOException {
        return new PdfReader(getContentResolver().openInputStream(uri));
    }

    public static void switchOfButtonWhenNoPdfElemInList(Button button) {
        if (listOfPdfReaders.size() == 1) {
            button.setEnabled(false);
        }
    }
}