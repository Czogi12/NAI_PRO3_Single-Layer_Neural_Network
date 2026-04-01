import java.util.Arrays;
import java.util.List;

public class SingleLayerNeuralNetwork {
	private List<Perceptron> neurons;
	private double alpha;
	private double beta;

	public SingleLayerNeuralNetwork(List<Perceptron> neurons, double alpha, double beta) {
		this.neurons = neurons;
		this.alpha = alpha;
		this.beta = beta;
	}

	public void train(Data[] trainingSet, int maxAge) {
		double[][] inputs = Arrays.stream(trainingSet).map(Data::vector).toArray(double[][]::new);
		var labels = new String[trainingSet.length];
		for (int i = 0; i < trainingSet.length; i++) {
			labels[i] = trainingSet[i].label();
		}
		trainLayer(inputs, labels, maxAge);
	}

	public void trainLayer(double[][] inputs, String[] labels, int maxAge) {
		for (var perceptron : neurons) {
			perceptron.resetWeights();
			perceptron.setAlpha(alpha);
			perceptron.setBeta(beta);

			perceptron.train(inputs, labels, maxAge);
		}
	}

	public String predict(double[] input) {
		Perceptron activePerceptron = null;
		boolean multiActivation = false;
		for (var perceptron : neurons) {
			if (perceptron.predict(input)) {
				if (activePerceptron != null) {
					multiActivation = true;
					break;
				}
				activePerceptron = perceptron;
			}
		}

		if (!multiActivation && activePerceptron != null) {
			return activePerceptron.getLabel();
		}

		double maxNet = Double.NEGATIVE_INFINITY;
		Perceptron predictedPerceptron = null;

		for (var perceptron : neurons) {
			double net = perceptron.getNet(input);
			if (maxNet < net) {
				maxNet = net;
				predictedPerceptron = perceptron;
			}
		}

		if (predictedPerceptron == null) {
			throw new RuntimeException("No active perceptron");
		}

		return predictedPerceptron.getLabel();
	}
}
