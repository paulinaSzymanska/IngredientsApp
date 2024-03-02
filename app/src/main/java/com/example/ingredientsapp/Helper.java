package com.example.ingredientsapp;


import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Helper {
    private static int counterForFileName = 0;

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
            PdfReader reader = getPdfReader(context, number);
            int pages = reader.getNumberOfPages();
            File cacheDir = context.getCacheDir();
            File txtBreakfastCacheDir = new File(cacheDir, cacheFileName);
            deleteDirectory(txtBreakfastCacheDir);
            txtBreakfastCacheDir.mkdir();
            Log.e("MainActivity", "Wanted pages: " + wantedPages);

            for (Integer wantedPage : wantedPages) {
                if (wantedPage <= pages) {
                    String fileContent = PdfTextExtractor.getTextFromPage(reader, wantedPage);
                    if (fileContent.contains("SKŁADNIKI")
                            || fileContent.contains("składniki")
                            || fileContent.contains("Składniki")) {
                        String fileName = generateFileName(wantedPage);
                        Log.d("Breakfast", fileName);
                        File file = new File(txtBreakfastCacheDir, fileName);
                        FileWriter writer = new FileWriter(file);
                        String[] lines = fileContent.split("\\r?\\n");
                        int lineCount = 0;

                        for (String line : lines) {
                            if (lineCount < 3) {
                                lineCount++;
                                continue;
                            }
                            if (line.contains("PRZYGOTOWANIE") ||
                                    line.contains("przygotowanie") ||
                                    line.contains("Przygotowanie")) {
                                writer.close();
                                break;
                            } else if (line.contains("PRZYPRAWY") || line.contains("przyprawy") ||
                                    line.contains("Przyprawy")) {
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

    @NonNull
    private static PdfReader getPdfReader(Context context, int number){
        return getPdf();
    }

    private static String generateFileName(Integer wantedPage) {
        counterForFileName++;
        return "page_" + wantedPage + "_" + counterForFileName + ".txt";
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

    private static PdfReader getPdf() {
        PdfReader finalPdfReader = null;
        List<PdfReader> listOfPdfReaders = MainActivity.listOfPdfReaders;
        if (!listOfPdfReaders.isEmpty()) {
            PdfReader pdfReader = listOfPdfReaders.get(0);
            finalPdfReader = pdfReader;
            listOfPdfReaders.remove(pdfReader);

        }
        return finalPdfReader;
    }
}
