package com.example.ingredientsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;


public class ShopList extends AppCompatActivity {
    private StringBuilder sb = new StringBuilder();
    File[] files;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        TextView shopList = findViewById(R.id.list);
        Button saveShopListButton = findViewById(R.id.saveShopList);

        File mainDirectory = new File("/data/user/0/com.example.ingredientsapp/cache/");
        File[] txtDirectories = mainDirectory.listFiles((dir, name) -> name.startsWith("txt") && new File(dir, name).isDirectory());

        saveShopListButton.setOnClickListener(view -> generatePdf(this, sb));
        files = txtDirectories;
        readSavedTxtFiles(shopList, files);
    }

    private void readSavedTxtFiles(TextView shopList, File[] files) {
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    Boolean moreThanOneSameFile = areMoreThanOneSameFile(file, files);
                    String s = "";
                    if (moreThanOneSameFile) {
                        s = counter + " x ";
                    }

                    if (skipThisFile(file)) {
                        continue;
                    }

                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                        String line;
                        counter = 0;
                        while ((line = br.readLine()) != null) {
                            sb.append(s);
                            sb.append(line);
                            sb.append("\n");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (file.isDirectory()) {
                    readSavedTxtFiles(shopList, file.listFiles());
                }
            }
        }
        String sortedAlphabeticallyIngredients = sortIngredients(sb.toString());
        shopList.setText(sortedAlphabeticallyIngredients);
    }

    private boolean skipThisFile(File file) {
        String fileNumber = file.getName().split("_")[2];
        String fileNum = fileNumber.replace(".txt", "");

        return Integer.parseInt(fileNum) != 1;
    }

    private Boolean areMoreThanOneSameFile(File file, File[] files) {
        String secondNumber = file.getName().split("_")[2];
        String numbers = secondNumber.replace(".txt", "");
        if (Integer.parseInt(numbers) != 1) return false;

        for (File myFile : files) {
            String numberOfPage = file.getName().split("_")[1];
            String myFileNumberOfPage = myFile.getName().split("_")[1];

            if (numberOfPage.equals(myFileNumberOfPage)) {
                counter++;
            }
        }
        if (counter > 1) {
            return true;
        } else return false;
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


    private void generatePdf(Context context, StringBuilder string) {
        PdfDocument pdf = null;
        try {
            File pdfFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "shopList.pdf");
            PdfWriter writer = new PdfWriter(pdfFile);
            pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            document.add(new Paragraph(string.toString()));

            System.out.println("Plik PDF został utworzony pomyślnie.");
            openPdf(context, Uri.fromFile(pdfFile));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pdf != null) {
                pdf.close();
            }
        }
    }

    private void openPdf(Context context, Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(intent);
    }
}
