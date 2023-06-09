package org.example;

import static org.example.BubbleSortThread.bubbleSort;

public class SingleThreadBubbleSort {
    int[] data;

    public SingleThreadBubbleSort(int[] data) {
        this.data = data;
    }

    public void arraySortWithoutMultiThreading(int[] data) {
        long startTime = System.currentTimeMillis();
        System.out.println("Posortowane dane bez wielowątkowości: ");
        bubbleSort(data);
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Operacja zajęła " + totalTime + " milisekund");
    }
}
