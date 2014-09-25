package chain;

/**
 * An abstract base class for a trace
 */
public interface Trace {

    public int getStepSize();

    public void setStepSize(int stepSize);

    public String getName();

    public  void setName(String name);

    public abstract int nrow();
}
