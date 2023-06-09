package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        int size = 100000;
        File dataFile = createFile("data.txt", size);
        int[] data = loadValuesFromFileToArray(dataFile, size);

        SingleThreadBubbleSort singleThreadBubbleSort = new SingleThreadBubbleSort(data);
        MultitreadBubbleSort multi = new MultitreadBubbleSort(size, Runtime.getRuntime().availableProcessors(), data);

        multi.arraySortUsingMultithreading(multi.numberOfThreads, multi.data);
        System.out.println();
        singleThreadBubbleSort.arraySortWithoutMultiThreading(singleThreadBubbleSort.data);

        try {
            Files.delete(Path.of(dataFile.getPath()));
        } catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    public static File createFile(String filename, int size) {
        try {
            File myFile = new File(filename);
            if (myFile.createNewFile()) {
                generateValuesToFile(filename, size);
                System.out.println("Utworzono plik");
            } else {
                System.out.println("Plik już istnieje");
            }
        } catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        return new File(filename);
    }

    private static void generateValuesToFile(String filename, int size) throws IOException {
        Random random = new Random();
        FileWriter fileWriter = new FileWriter(filename, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for (int i = 0; i < size; i++) {
            int randomValue = random.nextInt(1000);
            bufferedWriter.write(randomValue + " ");
        }
        bufferedWriter.close();
    }

    public static int[] loadValuesFromFileToArray(File file, int size) {
        int[] data = new int[size];
        try {
            Scanner scanner = new Scanner(file);
            int i = 0;
            while (scanner.hasNext()) {
                data[i] = scanner.nextInt();
                i++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        return data;
    }
}
