import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
	public GUI(Perceptron perceptron) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Perceptron");
		setSize(800, 600);
		setLayout(new BorderLayout());

		PerceptronView view = new PerceptronView(perceptron);
		add(view, BorderLayout.CENTER);

		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());

		int maxIndex = perceptron.getDimension() - 1;

		controlPanel.add(new JLabel("X Index:"));
		SpinnerNumberModel xModel = new SpinnerNumberModel(view.getXAxisIndex(), 0, maxIndex, 1);
		JSpinner xSpinner = new JSpinner(xModel);
		controlPanel.add(xSpinner);

		controlPanel.add(new JLabel("Y Index:"));
		SpinnerNumberModel yModel = new SpinnerNumberModel(view.getYAxisIndex(), 0, maxIndex, 1);
		JSpinner ySpinner = new JSpinner(yModel);
		controlPanel.add(ySpinner);

		xSpinner.addChangeListener(e -> {
			view.setXAxisIndex((int) xSpinner.getValue());
		});
		ySpinner.addChangeListener(e -> {
			view.setYAxisIndex((int) ySpinner.getValue());
		});

		add(controlPanel, BorderLayout.SOUTH);

		setVisible(true);
	}
}