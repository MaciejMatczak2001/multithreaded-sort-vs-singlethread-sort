package org.example;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

public class MultitreadBubbleSort {
    public MultitreadBubbleSort() {
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

        sortAndMerge(threads, data.length);

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.println("Operacja zajęła " + totalTime + " milisekund z użyciem " + numberOfThreads + " wątków");
    }

    private int[] sortAndMerge(BubbleSortThread[] threads, int size) {
        int[] sortedData = new int[size];

        Queue<Integer>[] queues = new Queue[threads.length];

        for (int i = 0; i < threads.length; i++) {
            queues[i] = Arrays.stream(threads[i].arr).boxed().collect(Collectors.toCollection(LinkedList::new));
        }

        for (int i = 0; i < size; i++) {
            int min = Integer.MAX_VALUE;
            int indexOfQueueWithMinVal = 0;
            for (int j = 0; j < threads.length; j++) {
                Integer currentValue = queues[j].peek();
                if (currentValue != null && currentValue < min) {
                    min = currentValue;
                    indexOfQueueWithMinVal = j;
                }
            }
            sortedData[i] = min;
            queues[indexOfQueueWithMinVal].poll();
        }
        return sortedData;
    }

    private void divideArrayAndAssignThreads(int lengthOfDividedArray, int numberOfThreads, int[] data, BubbleSortThread[] threads) {
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
