import evaluation.EvaluationMetrics;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
	private static PrepareDataset prepareDataset;

	public static void main(String[] args) {
		var locales = List.of(
				new Locale("pl"),
				new Locale("en"),
				new Locale("de")
		);

		Map<Locale, String> localeToLabelMap = new HashMap<>();

		try (BufferedReader bufferedReader = new BufferedReader(new FileReader("wili-2018/labels.csv"))) {
			boolean skippedHeader = false;

			for (String line : bufferedReader.lines().toList()) {
				if (!skippedHeader) {
					skippedHeader = true;
					continue;
				}
				String[] values = line.split(";");
				String label = values[0];
				String iso3Code = values[3];

				for (Locale locale : locales) {
					if (locale.getISO3Language().equals(iso3Code)) {
						localeToLabelMap.put(locale, label);
						break;
					}
				}

				if (localeToLabelMap.size() == locales.size()) {
					break;
				}
			}

			if (localeToLabelMap.size() != locales.size()) {
				throw new RuntimeException("Could not load all locales!");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		List<String> labels = null;

		try (BufferedReader bufferedReader = new BufferedReader(new FileReader("wili-2018/y_train.txt"))) {
			labels = bufferedReader.lines().toList();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		var dataSet = new ArrayList<Data>();

		try (BufferedReader bufferedReader = new BufferedReader(new FileReader("wili-2018/x_train.txt"))) {
			var lines = bufferedReader.lines().toList();
			for (int i = 0; i < lines.size(); i++) {
				String label = labels.get(i);
				if (!localeToLabelMap.containsValue(label)) continue;
				String line = lines.get(i).toLowerCase();

				dataSet.add(new Data(label,convertLineToInput(line)));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		prepareDataset = PrepareDataset.trainTestSplit(dataSet);

		var network = new SingleLayerNeuralNetwork(
				locales.stream().map(l -> new Perceptron(26, localeToLabelMap.get(l))).toList(),
				0.001,
				0.001
		);
		network.train(prepareDataset.getTrainingSet(), 100_000);

		System.out.println(network.predict(convertLineToInput("Tests vary in style, rigor and requirements. There is no general consensus or invariable")));
	}

	private static double[] convertLineToInput(String line) {
		long[] sums = new long[26];

		for (char c : line.toCharArray()) {
			if (c < 'a' || c > 'z') continue;
			sums[c - 'a']++;
		}

		double[] vector = new double[26];

		long sum = 0;

		for (int j = 0; j < 26; j++) {
			sum += sums[j];
		}

		for (int j = 0; j < 26; j++) {
			vector[j] = (double) sums[j] / sum;
		}

		return vector;
	}
}