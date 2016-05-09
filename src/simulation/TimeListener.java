package simulation;

@FunctionalInterface
public interface TimeListener {

    void onTimeChange(long newTime);
}
