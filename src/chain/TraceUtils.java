package chain;

import java.io.*;
import java.util.*;

/**
 * Partially based on beast.utils.LogAnalyzer by Remco Bouckaert
 *
 * @author Alexei Drummond
 */
public class TraceUtils {

    enum Type {Double, Int, Bool, String}

    final protected static String BAR = "|---------|---------|---------|---------|---------|---------|---------|---------|";

    public static Map<String, Trace> loadTraces(File file, int burnInPercentage) throws IOException {

        Trace[] traces;

        log("\nLoading " + file);
        BufferedReader fin = new BufferedReader(new FileReader(file));
        String line;
        String preamble = "";
        String[] names = null;
        int nData = 0;
        // first, sweep through the log file to determine size of the log
        while (fin.ready()) {
            line = fin.readLine();
            if (line.indexOf('#') < 0 && line.matches(".*[0-9a-zA-Z].*")) {
                if (names == null) {
                    names = line.split("\\s");
                } else {
                    nData += 1;
                }
            } else {
                preamble += line + "\n";
            }
        }
        int nLines = Math.max(1, nData / 80);

        int traceCount = names.length;

        int nBurnIn = nData * burnInPercentage / 100;

        traces = new Trace[traceCount];
        int traceSize = nData - nBurnIn;

        // use first 100 lines to autodetect the column types
        int autodetect = Math.min(traceSize, 100);
        for (int i = 0; i < traceCount; i++) {
            traces[i] = new NominalTrace(autodetect);
        }

        fin = new BufferedReader(new FileReader(file));
        nData = -nBurnIn - 1;
        log(", burnin " + burnInPercentage + "%, skipping " + nBurnIn + " log lines\n\n" + BAR + "\n");
        // grab data from the log, ignoring burn in samples
        Type[] types = new Type[traceCount];

        int count = 0;
        while (fin.ready()) {
            line = fin.readLine();
            if (!line.matches("[ ]*#") && line.matches("[0-9].*")) {
                if (++nData >= 0) {
                    String[] tokens = line.split("\\s");
                    if (count < autodetect) {
                        for (int i = 0; i < tokens.length; i++) {
                            ((NominalTrace) traces[i]).add(tokens[i]);
                        }
                    } else {
                        if (count == autodetect) {
                            // use first autodetect states to convert traces to correct types
                            types = convertTraces(traces);
                        }
                        for (int i = 0; i < tokens.length; i++) {
                            addValue(traces[i], types[i], tokens[i]);
                        }
                    }
                }
            }
            if (nData % nLines == 0) {
                log("*");
            }
        }

        Map<String, Trace> tracesMap = new HashMap<>();
        for (int i = 0; i < names.length; i++) {
            traces[i].setName(names[i]);
            tracesMap.put(names[i], traces[i]);
        }

        return tracesMap;
    }

    private static void log(String s) {
        System.out.print(s);
        System.out.flush();
    }

    private static Type[] convertTraces(Trace[] traces) {
        Type[] types = new Type[traces.length];
        for (int i = 0; i < traces.length; i++) {
            NominalTrace n = (NominalTrace) traces[i];
            if (n.isBoolean()) {
                traces[i] = n.getBitTrace();
                types[i] = Type.Bool;
            } else if (n.isInteger()) {
                traces[i] = n.getIntTrace();
                types[i] = Type.Int;
            } else if (n.isDouble()) {
                traces[i] = n.getDoubleTrace();
                types[i] = Type.Double;
            } else {
                types[i] = Type.String;
            }
        }
        return types;
    }

    private static void addValue(Trace trace, Type type, String value) {
        switch (type) {
            case Double:
                ((DoubleTrace) trace).add(Double.parseDouble(value));
                break;
            case Int:
                ((IntTrace) trace).add(Integer.parseInt(value));
                break;
            case String:
                ((NominalTrace) trace).add(value);
                break;
        }

    }
}
