import java.util.Arrays;

public class Perceptron {
    private static double ALPHA = 0.5;
    private static double BETA = 0.5;
    private final int dimension;
    private double threshold;
    public double[] weights;
    private double alpha;
    private double beta;
    private Data[] trainingSet;


    public Perceptron(int dimension) {
        this.dimension = dimension;
        this.weights = new double[dimension];
        this.threshold = 0;
        this.alpha = ALPHA;
        this.beta = BETA;
    }

    public void resetWeights() {
        for (int i = 0; i < dimension; i++) {
            weights[i] = 0.0;
        }
        threshold = 0;
    }

    public void train(Data[] trainingSet, int maxAge) {
        double[][] inputs = Arrays.stream(trainingSet).map(Data::vector).toArray(double[][]::new);
        boolean[] labels = new boolean[trainingSet.length];
        for (int i = 0; i < trainingSet.length; i++) {
            labels[i] = trainingSet[i].label();
        }
        this.trainingSet = trainingSet;
        train(inputs, labels, maxAge);
    }

    public void train(double[][] inputs, boolean[] labels, int maxAges) {
        int ages = 0;
//        boolean mistakeWasMade = false;

        while (ages++ < maxAges /*&& !mistakeWasMade*/) {
            for (int i = 0; i < inputs.length; i++) {
                boolean result = predict(inputs[i]);
//                if (!mistakeWasMade) mistakeWasMade = result != labels[i];
                adjustWeights(inputs[i], labels[i], result);
            }
        }
    }

    private void adjustWeights(double[] input, boolean expected, boolean predicted) {
        int d = expected ? 1 : 0;
        int y = predicted ? 1 : 0;

        for (int i = 0; i < weights.length; i++) {
            weights[i] += (d - y) * input[i] * alpha;
        }

        this.threshold = this.threshold - (d - y) * beta;
    }

    public boolean predict(double[] inputs) {
        double scalarMultiplication = 0;
        for (int i = 0; i < inputs.length; i++) {
            scalarMultiplication += weights[i] * inputs[i];
        }
        return scalarMultiplication - threshold >= 0;
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
}
