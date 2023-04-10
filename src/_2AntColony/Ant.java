package _2AntColony;

import java.util.Random;

public class Ant {
    private int[] tour;
    private boolean[] visited;
    private double tourLength;
    private double[][] data;
    private Random random;

    public Ant(double[][] data) {
        this.data = data;
        this.random = new Random();
    }

    public void constructTour(double alpha, double beta, double[][] pheromone, double[][] distances) {
        int numCities = data.length;
        tour = new int[numCities];
        visited = new boolean[numCities];

        // Başlangıç şehri rastgele seçilir
        int startCity = random.nextInt(numCities);
        tour[0] = startCity;
        visited[startCity] = true;

        // Diğer şehirler greedy olarak seçilir
        for (int i = 1; i < numCities; i++) {
            int currentCity = tour[i - 1];
            int nextCity = selectNextCity(currentCity, alpha, beta, pheromone, distances);
            tour[i] = nextCity;
            visited[nextCity] = true;
        }

        // Turun uzunluğunu hesapla
        tourLength = calculateTourLength();
    }

    private int selectNextCity(int currentCity, double alpha, double beta, double[][] pheromone, double[][] distances) {
        int numCities = data.length;
        double[] probabilities = new double[numCities];
        double sum = 0.0;

        // Her şehir için olasılık hesapla
        for (int i = 0; i < numCities; i++) {
            if (visited[i]) {
                continue;
            }
            double pheromoneLevel = pheromone[currentCity][i];
            double distance = distances[currentCity][i];
            double probability = Math.pow(pheromoneLevel, alpha) * Math.pow(1.0 / distance, beta);
            probabilities[i] = probability;
            sum += probability;
        }

        // Olasılıklar normalleştirilir
        for (int i = 0; i < numCities; i++) {
            probabilities[i] /= sum;
        }

        // Rulet çarkı yöntemiyle rastgele bir şehir seçilir
        double randomValue = random.nextDouble();
        double cumulativeProbability = 0.0;
        for (int i = 0; i < numCities; i++) {
            if (visited[i]) {
                continue;
            }
            cumulativeProbability += probabilities[i];
            if (cumulativeProbability >= randomValue) {
                return i;
            }
        }

        // Eğer buraya kadar gelinirse, son şehir seçilir
        for (int i = 0; i < numCities; i++) {
            if (!visited[i]) {
                return i;
            }
        }
        return -1;
    }

    private double calculateTourLength() {
        double tourLength = 0.0;
        int numCities = data.length;
        for (int i = 0; i < numCities; i++) {
            int j = (i + 1) % numCities;
            double x1 = data[tour[i]][0];
            double y1 = data[tour[i]][1];
            double x2 = data[tour[j]][0];
            double y2 = data[tour[j]][1];
            tourLength += Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
                }
                return tourLength;
            }

            public int[] getTour() {
                return tour;
            }

            public double getTourLength() {
                return tourLength;
            }
        }
