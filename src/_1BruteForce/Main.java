package _1BruteForce;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
        	//dosyaları oku
            File file = new File("src/Dosyalar/tsp_5_1");
            Scanner scanner = new Scanner(file);

            //5 şehir verdim ve y koordinatları
            int numberOfCities = 5;
            double[] x = new double[numberOfCities];
            double[] y = new double[numberOfCities];

            int sehirNo = 0;
            while (scanner.hasNextLine()) {
                String satır = scanner.nextLine();
                String[] koordinatlar = satır.split(" ");
                x[sehirNo] = Double.parseDouble(koordinatlar[0]);
                y[sehirNo] = Double.parseDouble(koordinatlar[1]);
                sehirNo++;
            }

            scanner.close();

            bruteForceTSP(x, y);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void bruteForceTSP(double[] x, double[] y) {
        int[] eniyiRota = new int[x.length];
        for (int i = 0; i < eniyiRota.length; i++) {
            eniyiRota[i] = i;
        }

        double shortestDistance = Double.POSITIVE_INFINITY;

        int[] route = new int[x.length];
        for (int i = 0; i < route.length; i++) {
            route[i] = i;
        }

        do {
            double distance = 0;
            for (int i = 0; i < route.length - 1; i++) {
                int city1 = route[i];
                int city2 = route[i + 1];
                distance += Math.sqrt(Math.pow(x[city1] - x[city2], 2) + Math.pow(y[city1] - y[city2], 2));
            }
            if (distance < shortestDistance) {
                shortestDistance = distance;
                for (int i = 0; i < eniyiRota.length; i++) {
                    eniyiRota[i] = route[i];
                }
            }
        } while (nextPermutation(route));

        System.out.println("Optimal maaliyet değeri: " + shortestDistance);
        System.out.println("Optimal maliyeti sağlayan yol: ");
        for (int i = 0; i < eniyiRota.length; i++) {
            System.out.print((eniyiRota[i] + 1) + " ");
        }
        System.out.println();

    }

    public static boolean nextPermutation(int[] array) {
        // Find longest non-increasing suffix
        int i = array.length - 1;
        while (i > 0 && array[i - 1] >= array[i]) {
            i--;
        }
        // Now i is the head index of the suffix

        // Are we at the last permutation already?
        if (i <= 0) {
            return false;
        }

        // Let array[i - 1] be the pivot
        // Find rightmost element that exceeds the pivot
        int j = array.length - 1;
        while (array[j] <= array[i - 1]) {
            j--;
        }
        // Now the value array[j] will become the new pivot
        // Assertion: j >= i

        // Swap the pivot with j
        int temp = array[i - 1];
        array[i - 1] = array[j];
        array[j] = temp;

        // Reverse the suffix
        j = array.length - 1;
        while (i < j) {      temp = array[i];
        array[i] = array[j];
        array[j] = temp;
        i++;
        j--;
    }

    // Successfully computed the next permutation
    return true;
}}
           
