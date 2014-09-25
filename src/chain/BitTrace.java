package chain;

import com.carrotsearch.hppc.BitSet;
import com.carrotsearch.hppc.IntArrayList;
import com.carrotsearch.hppc.sorting.IndirectComparator;
import com.carrotsearch.hppc.sorting.IndirectSort;

/**
 * Created by adru001 on 25/09/14.
 */
public class BitTrace extends BitSet implements NumberTrace {

    int stepSize;
    String name;
    int countZeros;

    public BitTrace(int size) {
        super(size);
    }

    /**
     * Produce a sort index, for calculating median, quantiles et cetera
     */
    public void sortIndex() {
        countZeros = 0;
        for (int i = 0; i < length(); i++) {
            if (!get(i)) {
                countZeros += 1;
            }
        }
    }

    @Override
    public final int getStepSize() {
        return stepSize;
    }

    @Override
    public final void setStepSize(int stepSize) {
        this.stepSize = stepSize;
    }

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final void setName(String name) {
        this.name = name;
    }

    public final double getAscending(int i) {
        if (i < countZeros) return 0.0;
        return 1.0;
    }

    public final double doubleValue(int i) {
        return get(i) ? 1.0 : 0.0;
    }

    public final boolean booleanValue(int i) {
        return get(i);
    }

    public final int nrow() {
        return (int)size();
    }
}
