package chain;

import static java.lang.Math.*;

/**
 * Created by adru001 on 25/09/14.
 */
public abstract class NumberTrace {

    private String name;
    private int stepSize;

    public NumberTrace(String name, int stepSize) {
        this.name = name;
        this.stepSize = stepSize;
    }

    public abstract void sortIndex();

    public abstract double doubleValue(int i);

    public abstract int size();

    public abstract double getAscending(int i);

    public final int getStepSize() {
        return stepSize;
    }

    public final String getName() {
        return name;
    }

    public final double mean() {
        double m = 0.0;
        int count = size();
        for (int i = 0; i < count; i++) {
            m += doubleValue(i);
        }
        return m / (double) count;
    }

    public final double geometricMean() {
        double gm = 0;
        int count = size();
        for (int i = 0; i < count; i++) {
            gm += Math.log(doubleValue(i));
        }
        return exp(gm / (double) count);
    }

    /**
     * compute the q-th quantile for a chain of comparable objects
     * (= inverse cdf)
     *
     * @param q quantile (0 < q <= 1)
     * @return q-th quantile
     */
    public final double quantile(double q) {
        if (q <= 0.0 || q > 1.0) throw new IllegalArgumentException("Quantile out of range");
        return getAscending((int) Math.ceil(q * size()) - 1);
    }

    /**
     * compute median
     *
     * @return median
     */
    public final double median() {
        int pos = size() / 2;
        if (size() % 2 == 1) {
            return getAscending(pos);
        } else {
            return (getAscending(pos-1) + getAscending(pos)) / 2.0;
        }
    }

    public final double min() {
        return getAscending(0);
    }

    public final double max() {
        return getAscending(size() - 1);
    }

    public double ESS() {
        return ESS;
    }

    public double ACT() {
        return ACT;
    }

    public double stdErrorOfMean() {
        return stdErrorOfMean;
    }

    public void computeCorrelationStatistics() {

        final int samples = size();
        int maxLag = samples - 1;

        double[] gammaStat = new double[maxLag];
        double varStat = 0.0;
        double mean = mean();

        for (int lag = 0; lag < maxLag; lag++) {
            for (int j = 0; j < samples - lag; j++) {
                final double del1 = doubleValue(j) - mean;
                final double del2 = doubleValue(j + lag) - mean;
                gammaStat[lag] += (del1 * del2);
            }

            gammaStat[lag] /= ((double) (samples - lag));

            if (lag == 0) {
                varStat = gammaStat[0];
            } else if (lag % 2 == 0) {
                // stopping criterion :)
                if (gammaStat[lag - 1] + gammaStat[lag] > 0) {
                    varStat += 2.0 * (gammaStat[lag - 1] + gammaStat[lag]);
                }
                // stop
                else {
                    maxLag = lag;
                }
            }
        }

        stdErrorOfMean = Math.sqrt(varStat / samples);
        ACT = stepSize * varStat / gammaStat[0];
        ESS = (stepSize * samples) / ACT;
        stdErrOfACT = (2.0 * Math.sqrt(2.0 * (2.0 * (double) (maxLag + 1)) / samples) * (varStat / gammaStat[0]) * stepSize);
    }

    private double stdErrorOfMean;   // standard error of mean
    private double ACT;              // auto correlation time
    private double ESS;              // effective sample size
    private double stdErrOfACT;      // standard deviation of autocorrelation time
}
