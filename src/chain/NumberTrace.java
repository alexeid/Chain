package chain;

import static java.lang.Math.*;

/**
 * Created by adru001 on 25/09/14.
 */
public interface NumberTrace extends Trace {

    public void sortIndex();

    public double doubleValue(int i);

    public double getAscending(int i);

    default double mean() {
        double m = 0.0;
        int count = nrow();
        for (int i = 0; i < count; i++) {
            m += doubleValue(i);
        }
        return m / (double) count;
    }

    default double geometricMean() {
        double gm = 0;
        int count = nrow();
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
    default double quantile(double q) {
        if (q <= 0.0 || q > 1.0) throw new IllegalArgumentException("Quantile out of range");
        return getAscending((int) Math.ceil(q * nrow()) - 1);
    }

    /**
     * compute median
     *
     * @return median
     */
    default double median() {
        int pos = nrow() / 2;
        if (nrow() % 2 == 1) {
            return getAscending(pos);
        } else {
            return (getAscending(pos-1) + getAscending(pos)) / 2.0;
        }
    }

    default double min() {
        return getAscending(0);
    }

    default double max() {
        return getAscending(nrow() - 1);
    }

    default CorrelationStatistics computeCorrelationStatistics() {

        final int samples = nrow();
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

        CorrelationStatistics cs = new CorrelationStatistics();
        cs.stdErrorOfMean = Math.sqrt(varStat / samples);
        cs.ACT = getStepSize() * varStat / gammaStat[0];
        cs.ESS = (getStepSize() * samples) / cs.ACT;
        cs.stdErrOfACT = (2.0 * Math.sqrt(2.0 * (2.0 * (double) (maxLag + 1)) / samples) * (varStat / gammaStat[0]) * getStepSize());
        return cs;
    }


    class CorrelationStatistics {
        double stdErrorOfMean;   // standard error of mean
        double ACT;              // auto correlation time
        double ESS;              // effective sample size
        double stdErrOfACT;      // standard deviation of autocorrelation time
    }
}
