package _2AntColony;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Dosyadan verileri okuyarak bir ArrayList oluştur
        ArrayList<double[]> cityList = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File("src/Dosyalar/tsp_124_1"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" ");
                double[] city = new double[2];
                city[0] = Double.parseDouble(parts[0]);
                city[1] = Double.parseDouble(parts[1]);
                cityList.add(city);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.format("FileNotFoundException: %s%n", e);
        }

        // ArrayList'teki verileri 2 boyutlu bir diziye aktar
        double[][] data = new double[cityList.size()][2];
        for (int i = 0; i < cityList.size(); i++) {
            data[i] = cityList.get(i);
        }

        // Ant Colony algoritmasıyla en kısa turu hesapla
        int numAnts = 80; //0-100 daha fazla için 200 üstü orta
        int numIterations = 150; // 500-1500 orta
        double alpha = 2.0; // 1-5 artmalı
        double beta = 6.0; // 5-15 artmalı
        double evaporationRate = 0.2; // 0.1 - 0.5 azalmalı
        AntColony antColony = new AntColony(numAnts, numIterations, alpha, beta, evaporationRate, data);
        antColony.solve();

        // En kısa turun uzunluğunu ve turun şehirlerini yazdır
        System.out.println("Optimal maaliyet değeri: " + antColony.getBestTourLength());
        int[] bestTour = antColony.getBestTour();
        System.out.print("Optimal maliyeti sağlayan yol: ");
        for (int i = 0; i < bestTour.length; i++) {
            System.out.print(bestTour[i] + " ");
        }
    }
}
