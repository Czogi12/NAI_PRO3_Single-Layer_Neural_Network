import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
	private static PrepareDataset prepareDataset;

	public static void main(String[] args) {
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader("iris.csv"))) {
			ArrayList<Data> dataset = new ArrayList<>();
			boolean skippedHeader = false;
			for (String line : bufferedReader.lines().toList()) {
				if (!skippedHeader) {
					skippedHeader = true;
					continue;
				}
				String[] values = line.split(",");
				String label = values[values.length - 1];
				if ("virginica".equals(label)) {
					continue;
				}
				double[] vector = Arrays.stream(Arrays.copyOfRange(values, 0, 4)).mapToDouble(Double::parseDouble).toArray();
				dataset.add(new Data("setosa".equals(label), vector));
			}
			prepareDataset = PrepareDataset.trainTestSplit(dataset);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		var perceptron = new Perceptron(4);

		for (int ages = 0; ages < 10; ages++) {
			perceptron.resetWeights();
			var trainingSet = prepareDataset.getTrainingSet();

			perceptron.train(trainingSet, ages);

			var predictionSet = prepareDataset.getTestSet();
			Boolean[] real = new Boolean[predictionSet.length];
			Boolean[] predictions = new Boolean[predictionSet.length];
			for (int i = 0; i < predictionSet.length; i++) {
				real[i] = predictionSet[i].label();
				predictions[i] = perceptron.predict(predictionSet[i].vector());
			}

			double accuracy = EvaluationMetrics.measureAccuracy(real, predictions);
			System.out.printf("TrainedAges: %d Accuracy: %.2f\n", ages, accuracy);
		}

		SwingUtilities.invokeLater(() -> new GUI(perceptron));
	}
}