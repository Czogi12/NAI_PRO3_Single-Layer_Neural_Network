package evaluation;

public class EvaluationMetrics<T> {
//	private int[][] confusionMatrix;
//
//	public EvaluationMetrics(T[] realClasses, T[] predictedClasses) {
//		int matched = 0;
//		for (int i = 0; i < realClasses.length; i++) {
//			if (realClasses[i].equals(predictedClasses[i])) {
//				matched++;
//			}
//		}
//	}
//
//	public double measureAccuracy(boolean[] realClasses, boolean[] predictedClasses) {
//		if (realClasses.length != predictedClasses.length) {
//			throw new IllegalArgumentException("Number of realClasses and number of predictedClasses must be equal");
//		}
//
//		double matched = 0;
//
//
//
//		return matched / realClasses.length;
//	}

	public static <T> double measureAccuracy(T[] realClasses, T[] predictedClasses) {
		if (realClasses.length != predictedClasses.length) {
			throw new IllegalArgumentException("Number of realClasses and number of predictedClasses must be equal");
		}

		double matched = 0;

		for (int i = 0; i < realClasses.length; i++) {
			if (realClasses[i].equals(predictedClasses[i])) {
				matched++;
			}
		}

		return matched / realClasses.length;
	}
}
