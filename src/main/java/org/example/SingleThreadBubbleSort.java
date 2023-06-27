package org.example;

import static org.example.BubbleSortThread.bubbleSort;

public class SingleThreadBubbleSort {
    public void arraySortWithoutMultiThreading(int[] data) {
        long startTime = System.currentTimeMillis();
        bubbleSort(data);
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Operacja zajęła " + totalTime + " milisekund bez użycia wielowątkowości");
    }
}
