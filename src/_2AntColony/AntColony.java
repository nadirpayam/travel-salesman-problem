package _2AntColony;

public class AntColony {
    private int numAnts;
    private int numIterations;
    private double alpha;
    private double beta;
    private double evaporationRate;
    private double[][] data;
    private double[][] distances;
    private double[][] pheromone;
    private Ant bestAnt;

    public AntColony(int numAnts, int numIterations, double alpha, double beta, double evaporationRate, double[][] data) {
        this.numAnts = numAnts;
        this.numIterations = numIterations;
        this.alpha = alpha;
        this.beta = beta;
        this.evaporationRate = evaporationRate;
        this.data = data;
        int numCities = data.length;
        distances = new double[numCities][numCities];
        pheromone = new double[numCities][numCities];
        for (int i = 0; i < numCities; i++) {
            for (int j = i; j < numCities; j++) {
                if (i == j) {
                    distances[i][j] = 0.0;
                    pheromone[i][j] = 0.0;
                } else {
                    double distance = calculateDistance(data[i], data[j]);
                    distances[i][j] = distance;
                    distances[j][i] = distance;
                    pheromone[i][j] = 0.5;
                    pheromone[j][i] = 0.5;
                }
            }
        }
    }

    private double calculateDistance(double[] city1, double[] city2) {
        double x1 = city1[0];
        double y1 = city1[1];
        double x2 = city2[0];
        double y2 = city2[1];
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    public void solve() {
        int numCities = data.length;
        for (int i = 0; i < numIterations; i++) {
            Ant[] ants = new Ant[numAnts];
            for (int j = 0; j < numAnts; j++) {
                ants[j] = new Ant(data);
                ants[j].constructTour(alpha, beta, pheromone, distances);
            }
            Ant iterationBestAnt = ants[0];
            for (int j = 1; j < numAnts; j++) {
                if (ants[j].getTourLength() < iterationBestAnt.getTourLength()) {
                    iterationBestAnt = ants[j];
                }
            }
            if (bestAnt == null || iterationBestAnt.getTourLength() < bestAnt.getTourLength()) {
                bestAnt = iterationBestAnt;
            }
            updatePheromone();
        }
    }

    private void updatePheromone() {
        int numCities = data.length;
        // Tüm pheromone değerleri evaparasyona uğrar
        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                pheromone[i][j] *= (1                 - evaporationRate);
            }
        }
        // En iyi ant turu üzerinde pheromone izleri bırakılır
        int[] tour = bestAnt.getTour();
        double tourLength = bestAnt.getTourLength();
        for (int i = 0; i < numCities - 1; i++) {
            int city1 = tour[i];
            int city2 = tour[i + 1];
            pheromone[city1][city2] += 1.0 / tourLength;
            pheromone[city2][city1] += 1.0 / tourLength;
        }
        int lastCity = tour[numCities - 1];
        int firstCity = tour[0];
        pheromone[lastCity][firstCity] += 1.0 / tourLength;
        pheromone[firstCity][lastCity] += 1.0 / tourLength;
    }

    public int[] getBestTour() {
        return bestAnt.getTour();
    }

    public double getBestTourLength() {
        return bestAnt.getTourLength();
    }
}

