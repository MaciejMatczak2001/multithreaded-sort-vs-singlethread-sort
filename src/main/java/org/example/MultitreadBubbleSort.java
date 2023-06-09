package org.example;

import java.util.Arrays;

import static org.example.BubbleSortThread.bubbleSort;

public class MultitreadBubbleSort {
    int size;
    int numberOfThreads;
    int[] data;

    public MultitreadBubbleSort(int size, int numberOfThreads, int[] data) {
        this.size = size;
        this.numberOfThreads = numberOfThreads;
        this.data = data;
    }

    public void arraySortUsingMultithreading(int numberOfThreads, int[] data) {
        BubbleSortThread[] threads = new BubbleSortThread[numberOfThreads];

        int lengthOfDividedArray = data.length / numberOfThreads;

        long startTime = System.currentTimeMillis();

        divideArrayAndAssignThreads(lengthOfDividedArray, numberOfThreads, data, threads);

        for (BubbleSortThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        mergeArrays(threads);

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.println("Posortowane dane za pomocą wielowątkowości: ");
        System.out.println("Operacja zajęła " + totalTime + " milisekund");
    }

    private int[] mergeArrays(BubbleSortThread[] threads) {
        int[] mergedArray = new int[size];
        int index = 0;
        for (BubbleSortThread thread : threads) {
            System.arraycopy(thread.arr, 0, mergedArray, index, thread.arr.length);
            index += thread.arr.length;
        }
        bubbleSort(mergedArray);
        return mergedArray;
    }

    public void divideArrayAndAssignThreads(int lengthOfDividedArray, int numberOfThreads, int[] data, BubbleSortThread[] threads) {
        for (int i = 0; i < numberOfThreads; i++) {
            int startIndex = i * lengthOfDividedArray;
            int endIndex;
            if (i == numberOfThreads - 1) {
                endIndex = data.length;
            } else {
                endIndex = startIndex + lengthOfDividedArray;
            }
            int[] dividedArray = Arrays.copyOfRange(data, startIndex, endIndex);
            threads[i] = new BubbleSortThread(dividedArray);
            threads[i].start();
        }
    }
}
