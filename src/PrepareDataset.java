import java.util.*;

public class PrepareDataset {
	private Data[] trainingSet;
	private Data[] testSet;

	private static double TRAINING_SIZE_RATE = 0.7;

	public static PrepareDataset trainTestSplit(List<Data> list) {
		return new PrepareDataset(list);
	}

	private PrepareDataset(List<Data> list) {
		int trainingSize = (int) (list.size() * TRAINING_SIZE_RATE);
		this.trainingSet = new Data[trainingSize];
		this.testSet = new Data[list.size() - trainingSize];

		Map<String, Queue<Data>> map = new HashMap<>();
		for (Data data : list) {
			if (!map.containsKey(data.label())) {
				map.put(data.label(), new PriorityQueue<>());
			}
			map.get(data.label()).add(data);
		}

		int trainingSetIndex = 0;
		int testSetIndex = 0;

		while (trainingSetIndex < trainingSet.length || testSetIndex < testSet.length) {
			for (String label : map.keySet()) {
				Data current = map.get(label).poll();
				if (current == null) continue;
				if (trainingSetIndex < trainingSet.length) {
					trainingSet[trainingSetIndex++] = current;
				}  else {
					testSet[testSetIndex++] = current;
				}
			}
		}
	}

	public  Data[] getTrainingSet() {
		return trainingSet;
	}
	public Data[] getTestSet() {
		return testSet;
	}
}
