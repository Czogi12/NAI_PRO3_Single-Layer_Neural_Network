import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class PerceptronView extends JPanel {
	private final Perceptron perceptron;
	private int yAxisIndex = 0;
	private int xAxisIndex = 1;

	private final double[] maxDimensionLength;
	private final double[] minDimensionLength;

	public PerceptronView(Perceptron perceptron) {
		this.perceptron = perceptron;

		maxDimensionLength = new double[perceptron.getDimension()];
		minDimensionLength = new double[perceptron.getDimension()];

		Arrays.fill(minDimensionLength, Double.MAX_VALUE);

		for (Data data : perceptron.getTrainingSet()) {
			for (int i = 0; i < perceptron.getDimension(); i++) {
				if (maxDimensionLength[i] < data.vector()[i]) {
					maxDimensionLength[i] = data.vector()[i];
				}
				if (minDimensionLength[i] > data.vector()[i]) {
					minDimensionLength[i] = data.vector()[i];
				}
			}
		}

		for (int i = 0; i < perceptron.getDimension(); i++) {
			double margin = (maxDimensionLength[i] - minDimensionLength[i]) * 0.05;
			maxDimensionLength[i] += margin;
			minDimensionLength[i] -= margin;
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.translate(0, getHeight());
		g2.scale(1, -1);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		for (Data data : perceptron.getTrainingSet()) {
			g2.setColor(data.label() ? Color.BLUE : Color.RED);

			int[] pos = getPointPos(data);
			g2.fillOval(pos[0], pos[1], 10, 10);
		}

		double minX = minDimensionLength[xAxisIndex];
		double maxX = maxDimensionLength[xAxisIndex];

		int[] from = getPointPos(minX, getY(minX));
		int[] to = getPointPos(maxX, getY(maxX));

		System.out.printf("from: %s to: %s\n", Arrays.toString(from), Arrays.toString(to));

		g2.setColor(Color.BLACK);
		g2.drawLine(from[0], from[1], to[0], to[1]);
	}

	private double getY(double x) {
		double[] weights = perceptron.getWeighs();
		double threshold = perceptron.getThreshold();

		double wX = weights[xAxisIndex];
		double wY = weights[yAxisIndex];

		if (wY == 0.0) {
			wY = 0.000001;
		}

		return (threshold - wX * x) / wY;
	}

	private int[] getPointPos(double x, double y) {
		double xRatio = (x - minDimensionLength[xAxisIndex])
				/ (maxDimensionLength[xAxisIndex] - minDimensionLength[xAxisIndex]);
		double yRatio = (y - minDimensionLength[yAxisIndex])
				/ (maxDimensionLength[yAxisIndex] - minDimensionLength[yAxisIndex]);

		return new int[]{(int) (getWidth() * xRatio), (int) (getHeight() * yRatio)};
	}

	private int[] getPointPos(Data data) {
		double x = data.vector()[xAxisIndex];
		double y = data.vector()[yAxisIndex];
		return getPointPos(x, y);
	}

	public void setXAxisIndex(int xAxisIndex) {
		this.xAxisIndex = xAxisIndex;
		repaint();
	}

	public void setYAxisIndex(int yAxisIndex) {
		this.yAxisIndex = yAxisIndex;
		repaint();
	}

	public int getXAxisIndex() {
		return xAxisIndex;
	}

	public int getYAxisIndex() {
		return yAxisIndex;
	}
}
