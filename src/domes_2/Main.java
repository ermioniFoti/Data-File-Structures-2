package domes_2;

import java.util.Random;

public class Main {
	static final int[] NUM_OF_RECORDS = { 200, 500, 1000,

			10000, 30000, 50000, 70000, 100000,

			// my tests
			150000, 200000, 250000

	};
	static final int NUM_OF_SEARCES = 100;
	static final int N = 1 << 16; // 2^16
	static final Random RAND = new Random();

	static Point randomPoint() {
		Point p = new Point();
		p.setX(RAND.nextInt(N));
		p.setY(RAND.nextInt(N));
		return p;
	}

	public static void main(String[] args) {
		System.out.println("Results for TwoDTree");
		runTestsForTwoDTree();
		System.out.println("Results for QuadTree");
		runTestsForQuadTree();
	}

	static void runTestsForTwoDTree() {

		for (final int M : NUM_OF_RECORDS) {
			System.out.printf("M = %d\n", M);
			// Create and populate a new 2-D tree
			TwoDTree tree = new TwoDTree();
			Point[] points = new Point[M];
			for (int i = 0; i < M; i++) {
				Point p = randomPoint();
				points[i] = p; // for testing successful search
				tree.insert(p);
			}

			long totalTime = 0;
			SearchStats stats = tree.getStats();
			// Test successful search
			for (int k = 0; k < NUM_OF_SEARCES; ++k) {
				int j = RAND.nextInt(M); // random index for existing points
				Point p = points[j];
				stats.setStartTime(System.nanoTime());
				boolean found = tree.search(p);
				stats.setEndTime(System.nanoTime());
				totalTime += stats.getTimeElapsed();
				if (!found) {
					throw new IllegalStateException("did not find a point previously inserted!");
				}
			}

			double avgDepthForSuccessfulSearch = stats.avgDepthForSuccessfulSearch();
			long successCount = stats.getSuccessCount();
			System.out.printf(" - Successful search: avgDepth=%.2f count=%d, searchTime = %d ns\n",
					avgDepthForSuccessfulSearch, successCount, totalTime);

			// Test failed search
			totalTime = 0;
			for (int k = 0; k < NUM_OF_SEARCES; ++k) {
				Point p = randomPoint();
				stats.setStartTime(System.nanoTime());
				tree.search(p);
				stats.setEndTime(System.nanoTime());
				totalTime += stats.getTimeElapsed();
			}

			double avgDepthForFailedSearch = stats.avgDepthForFailedSearch();
			long failureCount = stats.getFailureCount();
			System.out.printf(" - Failed search: avgDepth=%.2f count=%d , searchTime = %d ns\n",
					avgDepthForFailedSearch, failureCount, totalTime);
		}

	}

	static void runTestsForQuadTree() {
		for (final int M : NUM_OF_RECORDS) {
			System.out.printf("M = %d\n", M);
			// Create and populate a new 2-D tree
			QuadTree tree = new QuadTree(N);
			Point[] points = new Point[M];
			for (int i = 0; i < M; i++) {
				Point p = randomPoint();
				points[i] = p; // for testing successful search
				tree.insert(p);
			}
			SearchStats stats = tree.getStats();
			long totalTime = 0;

			// Test successful search
			for (int k = 0; k < NUM_OF_SEARCES; ++k) {
				int j = RAND.nextInt(M); // random index for existing points
				Point p = points[j];
				stats.setStartTime(System.nanoTime());
				boolean found = tree.search(p);
				stats.setEndTime(System.nanoTime());
				totalTime += stats.getTimeElapsed();
				if (!found) {
					throw new IllegalStateException("did not find a point previously inserted!");
				}
			}
			double avgDepthForSuccessfulSearch = stats.avgDepthForSuccessfulSearch();
			long successCount = stats.getSuccessCount();
			System.out.printf(" - Successful search: avgDepth=%.2f count=%d, searchTime = %d ns\n",
					avgDepthForSuccessfulSearch, successCount, totalTime);

			totalTime = 0;
			// Test failed search
			for (int k = 0; k < NUM_OF_SEARCES; ++k) {
				Point p = randomPoint();
				stats.setStartTime(System.nanoTime());
				tree.search(p);
				stats.setEndTime(System.nanoTime());
				totalTime += stats.getTimeElapsed();
			}

			double avgDepthForFailedSearch = stats.avgDepthForFailedSearch();
			long failureCount = stats.getFailureCount();
			System.out.printf(" - Failed search: avgDepth=%.2f count=%d , searchTime = %d ns\n",
					avgDepthForFailedSearch, failureCount, totalTime);
		}

	}
}