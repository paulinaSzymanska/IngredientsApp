package com.example.ingredientsapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Comparator;

public class ShopList extends AppCompatActivity {
    private StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        TextView shopList = findViewById(R.id.list);

        File mainDirectory = new File("/data/user/0/com.example.ingredientsapp/cache/");

        File[] txtDirectories = mainDirectory.listFiles((dir, name) -> name.startsWith("txt") && new File(dir, name).isDirectory());
        readSavedTxtFiles(txtDirectories, shopList);
    }

    private void readSavedTxtFiles(File[] files, TextView shopList) {
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line);
                            sb.append("\n");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (file.isDirectory()) {
                    readSavedTxtFiles(file.listFiles(), shopList);
                }
            }
        }
        String sortedAlphabeticallyIngredients = sortIngredients(sb.toString());
        shopList.setText(sortedAlphabeticallyIngredients);
    }

    private String sortIngredients(String string) {
        if (!string.isEmpty()) {
            String[] strings = string.split("\\n");

            Arrays.sort(strings, (s1, s2) -> {
                String str1 = s1.replaceAll("\\d", "");
                String str2 = s2.replaceAll("\\d", "");

                int result = str1.compareTo(str2);

                if (result != 0) {
                    return result;
                } else {
                    return s1.compareTo(s2);
                }
            });

            StringBuilder sortedString = new StringBuilder();
            for (String element : strings) {
                sortedString.append(element).append("\n");
            }
            return sortedString.toString();
        } else return "";
    }
}
