package _3Greedy;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class TSPGreedy {
	public static void main(String[] args) throws Exception {
		String filename = "src/Dosyalar/tsp_85900_1";
		double[][] coords = readCoordsFromFile(filename);
		int[] route = tspGreedy(coords);
		printTour(coords, route);
	}

	public static double[][] readCoordsFromFile(String filename) throws Exception {
		ArrayList<double[]> coords = new ArrayList<>();
		try (Scanner scanner = new Scanner(new File(filename))) {
			while (scanner.hasNextLine()) {
				String[] line = scanner.nextLine().split(" ");
				double x = Double.parseDouble(line[0]);
				double y = Double.parseDouble(line[1]);
				double[] coord = new double[] { x, y };
				coords.add(coord);
			}
		}
		return coords.toArray(new double[coords.size()][]);
	}

	public static int[] tspGreedy(double[][] coords) {
		int n = coords.length;
		boolean[] visited = new boolean[n];
		int[] route = new int[n];
		int curr = 0;
		route[0] = curr;
		visited[curr] = true;
		for (int i = 1; i < n; i++) {
			int next = -1;
			double minDist = Double.POSITIVE_INFINITY;
			for (int j = 0; j < n; j++) {
				if (!visited[j]) {
					double dist = distance(coords[curr], coords[j]);
					if (dist < minDist) {
						minDist = dist;
						next = j;
					}
				}
			}
			visited[next] = true;
			route[i] = next;
			curr = next;
		}
		return route;
	}

	public static double distance(double[] a, double[] b) {
		return Math.sqrt(Math.pow(a[0] - b[0], 2) + Math.pow(a[1] - b[1], 2));
	}

	public static void printTour(double[][] coords, int[] route) {
		int n = coords.length;
		double totalDist = 0;
		System.out.println("Optimal maliyeti sağlayan yol: ");
		for (int i = 0; i < n; i++) {

			int from = route[i];
			int to = route[(i + 1) % n];

			double dist = distance(coords[from], coords[to]);
			totalDist += dist;

			System.out.print(from + " ");
		}

		System.out.println(route[0]);
		System.out.println("Optimal maaliyet değeri: " + totalDist);

	}
}
