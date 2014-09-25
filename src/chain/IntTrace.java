package chain;

import com.carrotsearch.hppc.IntArrayList;
import com.carrotsearch.hppc.sorting.IndirectComparator;
import com.carrotsearch.hppc.sorting.IndirectSort;

/**
 * Created by adru001 on 25/09/14.
 */
public class IntTrace extends IntArrayList implements NumberTrace {

    int[] sortIndex;
    int stepSize;
    String name;

    public IntTrace(int size) {
        super(size);
    }

    /**
     * Produce a sort index, for calculating median, quantiles et cetera
     */
    public void sortIndex() {
        sortIndex = IndirectSort.mergesort(0, size(), new IndirectComparator.AscendingIntComparator(buffer));
    }

    @Override
    public int getStepSize() {
        return stepSize;
    }

    @Override
    public void setStepSize(int stepSize) {
        this.stepSize = stepSize;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public final double getAscending(int i) {
        return (double)buffer[sortIndex[i]];
    }

    public double doubleValue(int i) {
        return (double)buffer[i];
    }

    public int nrow() {
        return size();
    }
}
