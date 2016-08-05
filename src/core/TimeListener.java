package core;

@FunctionalInterface
public interface TimeListener {

    void onTimeChange(long newTime);
}
