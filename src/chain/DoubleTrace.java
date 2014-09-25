package chain;

import com.carrotsearch.hppc.sorting.IndirectComparator;
import com.carrotsearch.hppc.sorting.IndirectSort;

/**
 * Created by adru001 on 25/09/14.
 */
public class DoubleTrace extends NumberTrace {

    double[] val;
    int[] sortIndex;

    public DoubleTrace(String name, int stepSize, int size) {
        super(name, stepSize);
        val = new double[size];
    }

    /**
     * Produce a sort index, for calculating median, quantiles et cetera
     */
    public void sortIndex() {
        sortIndex = IndirectSort.mergesort(0,val.length, new IndirectComparator.AscendingDoubleComparator(val));
    }

    public final int size() {
        return val.length;
    }

    public final double getAscending(int i) {
        return val[sortIndex[i]];
    }

    public final double doubleValue(int i) {
        return val[i];
    }
}
