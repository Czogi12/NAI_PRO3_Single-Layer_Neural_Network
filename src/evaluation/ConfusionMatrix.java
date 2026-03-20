package evaluation;

public class ConfusionMatrix {
    private int tp;
    private int fp;
    private int tn;
    private int fn;

    public static EvaluationMetrics calculateMatrix() {
        return null;
    }

    private ConfusionMatrix(int tp, int fp, int tn, int fn) {
        this.tp = tp;
        this.fp = fp;
        this.tn = tn;
        this.fn = fn;
    }

    public int getTp() {
        return tp;
    }

    public int getFp() {
        return fp;
    }

    public int getTn() {
        return tn;
    }

    public int getFn() {
        return fn;
    }
}
