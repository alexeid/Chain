package chain;

import java.io.*;
import java.util.*;

/**
 * @author Alexei Drummond
 */
public class TraceUtils {

    enum Type {Double, Int, Bool, String}

    public static Map<String,NumberTrace> loadTraces(File file, int burnInPercentage) throws IOException {

        Map<String,NumberTrace> traces = new HashMap<>();

        // TODO implement :)

//            //log("\nLoading " + sFile);
//            BufferedReader fin = new BufferedReader(new FileReader(file));
//            String sStr = null;
//            String sPreAmble = "";
//            String[] sLabels = null;
//            int nData = 0;
//            // first, sweep through the log file to determine size of the log
//            while (fin.ready()) {
//                sStr = fin.readLine();
//                if (sStr.indexOf('#') < 0 && sStr.matches(".*[0-9a-zA-Z].*")) {
//                    if (sLabels == null) {
//                        sLabels = sStr.split("\\s");
//                    } else {
//                        nData += 1;
//                    }
//                } else {
//                    sPreAmble += sStr + "\n";
//                }
//            }
//            int nLines = Math.max(1, nData / 80);
//            // reserve memory
//            int nItems = sLabels.length;
//            List[] ranges = new List[nItems];
//            int nBurnIn = nData * burnInPercentage / 100;
//
//            // = new Double[nItems][nData - nBurnIn];
//            fin = new BufferedReader(new FileReader(file));
//            nData = -nBurnIn - 1;
//            //logln(", burnin " + nBurnInPercentage + "%, skipping " + nBurnIn + " log lines\n\n" + BAR);
//            // grab data from the log, ignoring burn in samples
//            Type[] types = new Type[nItems];
//            //Arrays.fill(m_types, type.INTEGER);
//            while (fin.ready()) {
//                sStr = fin.readLine();
//                int i = 0;
//                if (sStr.indexOf('#') < 0 && sStr.matches("[0-9].*")) // {
//                    //nData++;
//                    if (++nData >= 0) //{
//                        for (String sStr2 : sStr.split("\\s")) {
//                            try {
//                                if (sStr2.indexOf('.') >= 0) {
//                                    types[i] = Type.Double;
//                                }
//                                m_fTraces[i][nData] = Double.parseDouble(sStr2);
//                            } catch (Exception e) {
//                                if (m_ranges[i] == null) {
//                                    m_ranges[i] = new ArrayList<String>();
//                                }
//                                if (!m_ranges[i].contains(sStr2)) {
//                                    m_ranges[i].add(sStr2);
//                                }
//                                m_fTraces[i][nData] = 1.0 * m_ranges[i].indexOf(sStr2);
//                            }
//                            i++;
//                        }
//                //}
//                //}
//                if (nData % nLines == 0) {
//                    log("*");
//                }
//            }
//            logln("");
//            // determine types
//            for (int i = 0; i < nItems; i++)
//                if (m_ranges[i] != null)
//                    if (m_ranges[i].size() == 2 && m_ranges[i].contains("true") && m_ranges[i].contains("false") ||
//                            m_ranges[i].size() == 1 && (m_ranges[i].contains("true") || m_ranges[i].contains("false")))
//                        m_types[i] = type.BOOL;
//                    else
//                        m_types[i] = type.NOMINAL;
//
//        } // readLogFile

        return traces;
    }

}
