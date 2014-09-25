package chain;

import com.carrotsearch.hppc.sorting.IndirectComparator;
import com.carrotsearch.hppc.sorting.IndirectSort;

/**
 * Created by adru001 on 25/09/14.
 */
public class IntTrace extends NumberTrace {

    int[] val;
    int[] sortIndex;

    public IntTrace(String name, int stepSize, int size) {
        super(name, stepSize);
        val = new int[size];
    }

    /**
     * Produce a sort index, for calculating median, quantiles et cetera
     */
    public void sortIndex() {
        sortIndex = IndirectSort.mergesort(0, val.length, new IndirectComparator.AscendingIntComparator(val));
    }

    public final int size() {
        return val.length;
    }

    public final double getAscending(int i) {
        return val[sortIndex[i]];
    }

    public double doubleValue(int i) {
        return (double)val[i];
    }
}
