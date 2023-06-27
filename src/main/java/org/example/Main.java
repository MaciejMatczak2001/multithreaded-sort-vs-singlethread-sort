package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        int size = 10000;
        File dataFile = createFile("data.txt", size);
        int[] data1 = loadValuesFromFileToArray(dataFile, size);
        int[] data2 = loadValuesFromFileToArray(dataFile, size);
        int[] data3 = loadValuesFromFileToArray(dataFile, size);
        int[] data4 = loadValuesFromFileToArray(dataFile, size);

        SingleThreadBubbleSort singleThreadBubbleSort = new SingleThreadBubbleSort();
        MultitreadBubbleSort multitreadBubbleSort = new MultitreadBubbleSort();

        System.out.println();
        System.out.println("Wykonywanie sortowania dla tablicy wielkości " + size + " elementów:");
        System.out.println();
        singleThreadBubbleSort.arraySortWithoutMultiThreading(data1);
        System.out.println();
        multitreadBubbleSort.arraySortUsingMultithreading(2, data2);
        System.out.println();
        multitreadBubbleSort.arraySortUsingMultithreading(3, data3);
        System.out.println();
        multitreadBubbleSort.arraySortUsingMultithreading(4, data4);

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
            int randomValue = random.nextInt(100);
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
