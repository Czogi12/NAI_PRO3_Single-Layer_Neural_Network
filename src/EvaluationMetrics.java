public class EvaluationMetrics {
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
