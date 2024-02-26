package com.example.ingredientsapp;


import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Helper {
    public static void deleteDirectory(File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteDirectory(file);
                }
            }
        }
        directory.delete();
    }

    public static void saveContentOfPages(Context context, List<Integer> wantedPages, int number, String cacheFileName) {
        try {
            InputStream inputStream = context.getResources().openRawResource(number);
            String fileContent;
            PdfReader reader;
            reader = new PdfReader(inputStream);
            int pages = reader.getNumberOfPages();

            File cacheDir = context.getCacheDir();
            File txtBreakfastCacheDir = new File(cacheDir, cacheFileName);
            deleteDirectory(txtBreakfastCacheDir);
            txtBreakfastCacheDir.mkdir();
            Log.e("MainActivity", "Wanted pages: " + wantedPages);

            for (Integer wantedPage : wantedPages) {
                if (wantedPage <= pages) {
                    fileContent = PdfTextExtractor.getTextFromPage(reader, wantedPage);
                    if (fileContent.contains("SKŁADNIKI")) {
                        String fileName = "page_" + wantedPage + ".txt";
                        File file = new File(txtBreakfastCacheDir, fileName);
                        FileWriter writer = new FileWriter(file);
                        String[] lines = fileContent.split("\\r?\\n");
                        int lineCount = 0;

                        for (String line : lines) {
                            if (lineCount < 3) {
                                lineCount++;
                                continue;
                            }
                            if (line.contains("PRZYGOTOWANIE")) {
                                writer.close();
                                break;
                            } else if (line.contains("PRZYPRAWY")) {

                            } else {
                                writer.write(line + "\n");
                            }
                        }
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static List<Integer> readValuesFromNumbersBoxes(List<TextView> providedSites) {
        ArrayList<Integer> finalList = new ArrayList<>();

        for (TextView textView : providedSites) {
            String string = textView.getText().toString();
            if (string.isEmpty() || string.equals("0")) {
            } else {
                finalList.add(Integer.valueOf(textView.getText().toString()));
            }
        }
        return finalList;
    }
}
