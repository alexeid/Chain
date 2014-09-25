package chain;

import com.carrotsearch.hppc.DoubleArrayList;
import com.carrotsearch.hppc.sorting.IndirectComparator;
import com.carrotsearch.hppc.sorting.IndirectSort;

/**
 * Created by adru001 on 25/09/14.
 */
public class DoubleTrace extends DoubleArrayList implements NumberTrace {

    int[] sortIndex;
    int stepSize;
    String name;

    public DoubleTrace(int size) {
        super(size);
    }

    public DoubleTrace(String name, int stepSize, int size) {
        super(size);
        this.name = name;
        this.stepSize = stepSize;
    }

    /**
     * Produce a sort index, for calculating median, quantiles et cetera
     */
    public void sortIndex() {
        sortIndex = IndirectSort.mergesort(0,size(), new IndirectComparator.AscendingDoubleComparator(buffer));
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
        return buffer[sortIndex[i]];
    }

    public final double doubleValue(int i) {
        return buffer[i];
    }

    public int nrow() {
        return size();
    }
}
