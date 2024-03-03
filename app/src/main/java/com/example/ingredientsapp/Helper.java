package com.example.ingredientsapp;


import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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

    public static void saveContentOfPages(Context context, List<Integer> wantedPages, String cacheFileName) {
        try {
            PdfReader reader = getPdfReader();
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
                        String fileName = generateFileName(wantedPage, txtBreakfastCacheDir);
                        Log.e("MainActivity", "File names: " + fileName);

                        File file = new File(txtBreakfastCacheDir, fileName);
                        FileWriter writer = new FileWriter(file);
                        String[] lines = fileContent.split("\n");
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
    private static PdfReader getPdfReader() {
        PdfReader finalPdfReader = null;
        List<PdfReader> listOfPdfReaders = MainActivity.listOfPdfReaders;
        if (!listOfPdfReaders.isEmpty()) {
            PdfReader pdfReader = listOfPdfReaders.get(0);
            finalPdfReader = pdfReader;
            listOfPdfReaders.remove(pdfReader);

        }
        return finalPdfReader;
    }

    private static String generateFileName(Integer wantedPage, File currentFiles) {
        String result = "page_" + wantedPage;
        File[] files = currentFiles.listFiles();
        int length = files.length;
        if (length == 0) {
            return result + "_1.txt";
        } else {
            for (File file : files) {
                String alreadyGotFileNumber = file.getName().split("_")[1];
                if (Integer.parseInt(alreadyGotFileNumber) == wantedPage) {
                    int highestNumber = findHighestNumberAfterUnderscore(wantedPage, files);
                    return result + "_" + (highestNumber + 1) + ".txt";
                }

            }
        }
        return result + "_1.txt";
    }

    private static int findHighestNumberAfterUnderscore(Integer wantedPage, File[] files) {
        File file = Arrays.stream(files)
                .filter(el -> Integer.parseInt(el.getName().split("_")[1]) == wantedPage)
                .max(Comparator.comparingInt(
                        el -> Integer.parseInt((el.getName().split("_")[2]).replace(".txt", ""))))
                .get();

        String splitted = file.getName().split("_")[2];
        String fileNum = splitted.replace(".txt", "");
        return Integer.parseInt(fileNum);
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
