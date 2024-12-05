package com.example.rest;

import java.io.IOException;
import java.net.URI;
import java.util.Comparator;
import java.util.Random;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.example.controls.tda.list.LinkedList;

/**
 * Main class.
 *
 */
public class Main {
    public static final String BASE_URI = "http://localhost:8099/myapp/";

    public static HttpServer startServer() {
        final ResourceConfig rc = new ResourceConfig().packages("com.example.rest");
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }




    private static Integer[] generateRandomArray(int size) {
        Random random = new Random();
        Integer[] array = new Integer[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(100000); 
        }
        return array;
    }

    private static Integer[] copyArray(Integer[] original) {
        return java.util.Arrays.copyOf(original, original.length);
    }

    private static void testSorting(LinkedList<Integer> list, String algorithmName,
            Comparator<Integer> comparator) {
        long startTime = System.nanoTime();

        switch (algorithmName) {
            case "QuickSort":
                list.quickSort(comparator);
                break;
            case "MergeSort":
                list.mergeSort(comparator);
                break;
            case "ShellSort":
                list.shellSort(comparator);
                break;
        }

        long endTime = System.nanoTime();
        double timeInMs = (endTime - startTime) / 1_000_000.0;

        System.out.printf("%-10s Con %6d elemnentos demora %10.2f ms%n",
                algorithmName, list.getSize(), timeInMs);
    }

    private static void testSearching(LinkedList<Integer> list, String algorithmName,
            Integer target, Comparator<Integer> comparator) {
        long startTime = System.nanoTime();
        Integer result;

        if (algorithmName.equals("LinearSearch")) {
            result = list.linearSearch(target, comparator);
        } else {
            result = list.binarySearch(target, comparator);
        }

        long endTime = System.nanoTime();
        double timeInMs = (endTime - startTime) / 1_000_000.0;

        System.out.printf("%-13s Con %6d elementos demoro %10.2f ms%n",
                algorithmName, list.getSize(), timeInMs, result);
    }

    public static void main(String[] args) throws IOException {
        int[] sizes = { 10000, 20000, 25000 };

        Comparator<Integer> comparator = Integer::compareTo;

        System.out.println("\n=== Prueba de rendimiento con metodos de ordenacion y busqueda ===\n");

        for (int size : sizes) {
            System.out.println("Probado con " + size + " elementos:");
            System.out.println("-----------------------------------------");

            Integer[] originalArray = generateRandomArray(size);

            System.out.println("\nAlgoritmos de ordenacion:");
            System.out.println("-----------------------------------------");

            LinkedList<Integer> quickSortList = new LinkedList<>();
            quickSortList.toList(copyArray(originalArray));
            testSorting(quickSortList, "QuickSort", comparator);

            LinkedList<Integer> mergeSortList = new LinkedList<>();
            mergeSortList.toList(copyArray(originalArray));
            testSorting(mergeSortList, "MergeSort", comparator);

            LinkedList<Integer> shellSortList = new LinkedList<>();
            shellSortList.toList(copyArray(originalArray));
            testSorting(shellSortList, "ShellSort", comparator);


            System.out.println("\nAlgoritmos de Busqueda:");
            System.out.println("------------------");

            Integer targetValue = originalArray[size / 2]; 
            LinkedList<Integer> linearSearchList = new LinkedList<>();
            linearSearchList.toList(copyArray(originalArray));
            testSearching(linearSearchList, "LinearSearch", targetValue, comparator);

            LinkedList<Integer> binarySearchList = new LinkedList<>();
            binarySearchList.toList(copyArray(originalArray));
            binarySearchList.quickSort(comparator); 
            testSearching(binarySearchList, "BinarySearch", targetValue, comparator);

            System.out.println("\n");
        }


        
        // Start the server
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.stop();
    }
}

