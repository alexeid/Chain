package chain;

import com.carrotsearch.hppc.IntArrayList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexei on 25/09/14.
 */
public class NominalTrace implements Trace {

    // the trace stores indices to the values list
    IntArrayList trace;

    // a list of the unique values in this nominal trace
    List<String> values = new ArrayList<>();

    int stepSize;
    String name;

    public NominalTrace(int size) {
        trace = new IntArrayList(size);
    }

    public void add(String value) {
        if (values.contains(value)) {
            trace.add(values.indexOf(value));
        } else {
            values.add(value);
            trace.add(values.size() - 1);
        }
    }

    public String stringValue(int i) {
        return values.get(trace.get(i));
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

    public int nrow() {
        return trace.size();
    }

    public DoubleTrace getDoubleTrace() throws NumberFormatException {
        DoubleTrace dTrace = new DoubleTrace(nrow());
        double[] d = new double[values.size()];
        for (int i = 0; i < d.length; i++) {
            d[i] = Double.parseDouble(values.get(i));
        }
        for (int i = 0; i < nrow(); i++) {
            dTrace.add(d[trace.buffer[i]]);
        }
        return dTrace;
    }

    public IntTrace getIntTrace() throws NumberFormatException {
        IntTrace iTrace = new IntTrace(nrow());
        int[] x = new int[values.size()];
        for (int i = 0; i < x.length; i++) {
            x[i] = Integer.parseInt(values.get(i));
        }
        for (int i = 0; i < nrow(); i++) {
            iTrace.add(x[trace.buffer[i]]);
        }
        return iTrace;
    }

    static List<String> booleanStrings = new ArrayList<>();

    static {
        booleanStrings.add("true");
        booleanStrings.add("false");
        booleanStrings.add("1");
        booleanStrings.add("0");
        booleanStrings.add("1.0");
        booleanStrings.add("0.0");
    }

    public boolean isBoolean() {
        if (values.size() > 2) return false;
        for (String value : values) {
            if (!booleanStrings.contains(value.toLowerCase())) {
                return false;
            }
        }
        return true;
    }

    public BitTrace getBitTrace() {
        BitTrace bTrace = new BitTrace(nrow());
        boolean[] x = new boolean[values.size()];
        for (int i = 0; i < x.length; i++) {
            // this relies on the booleanStrings array being in order true, false, true, false, ...
            x[i] = booleanStrings.indexOf(values.get(i)) % 2 == 0;
        }
        for (int i = 0; i < nrow(); i++) {
            if (x[trace.buffer[i]]) {
                bTrace.set(i);
            }
        }
        return bTrace;
    }

    public boolean isInteger() {
        for (String value : values) {
            try {
                int x = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    public boolean isDouble() {

        // quick exit if no decimal place
        if (values.get(0).indexOf('.') < 0) return false;

        for (String value : values) {
            try {
                double d = Double.parseDouble(value);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }
}
