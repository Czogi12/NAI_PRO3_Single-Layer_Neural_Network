import java.util.Arrays;

public class Perceptron {
	private static final double DEFAULT_ALPHA = 0.001;
	private static final double DEFAULT_BETA = 0.001;
	private final int dimension;
	private double threshold;
	public double[] weights;
	private final String label;

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	public void setBeta(double beta) {
		this.beta = beta;
	}

	private double alpha;
	private double beta;
	private Data[] trainingSet;


	public Perceptron(int dimension, String label) {
		this.dimension = dimension;
		this.weights = new double[dimension];
		this.threshold = 1;
		resetWeights();
		this.alpha = DEFAULT_ALPHA;
		this.beta = DEFAULT_BETA;
		this.label = label;
	}

	public void resetWeights() {
		for (int i = 0; i < dimension; i++) {
			weights[i] = 5.0;
		}
		threshold = 0;
	}

	public void train(Data[] trainingSet, int maxAge) {
		double[][] inputs = Arrays.stream(trainingSet).map(Data::vector).toArray(double[][]::new);
		var labels = new String[trainingSet.length];
		for (int i = 0; i < trainingSet.length; i++) {
			labels[i] = trainingSet[i].label();
		}
		this.trainingSet = trainingSet;
		train(inputs, labels, maxAge);
	}

	public void train(double[][] inputs, String[] labels, int maxAges) {
		int ages = 0;
		boolean mistakeWasMade = true;

		while (ages++ < maxAges && mistakeWasMade) {
//			System.out.printf("Age: %d\n", ages);
			mistakeWasMade = false;

			for (int i = 0; i < inputs.length; i++) {
				var result = predict(inputs[i]);
				if ((result && !labels[i].equals(label)) || (!result && labels[i].equals(label))) {
					mistakeWasMade = true;
				}

				adjustWeights(inputs[i], labels[i].equals(label), result);
			}
		}
		System.out.printf("Training %s complete in %d ages\n", label, ages - 1);
	}

	private void adjustWeights(double[] input, boolean expected, boolean predicted) {
		int d = expected ? 1 : 0;
		int y = predicted ? 1 : 0;

		for (int i = 0; i < weights.length; i++) {
			weights[i] += (d - y) * input[i] * alpha;
		}

		this.threshold = this.threshold - (d - y) * beta;
	}

	public double getNet(double[] inputs) {
		double scalarMultiplication = 0;
		for (int i = 0; i < inputs.length; i++) {
			scalarMultiplication += weights[i] * inputs[i];
		}
		return scalarMultiplication - threshold;
	}

	public boolean predict(double[] inputs) {
		return getNet(inputs) >= 0;
	}

	public Data[] getTrainingSet() {
		return trainingSet;
	}

	public double getThreshold() {
		return threshold;
	}

	public int getDimension() {
		return dimension;
	}

	public double[] getWeighs() {
		return weights;
	}

	public String getLabel() {
		return label;
	}
}
