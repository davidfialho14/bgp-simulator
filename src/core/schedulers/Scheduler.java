package core.schedulers;


import core.Message;

public interface Scheduler {

    /**
     * Adds a new message to the scheduler. The message should contain the current time and the scheduler
     * might add extra time to the message. The scheduler will always take into account the time associated
     * with the message's link, which means that the order of the messages at the receiving end will be
     * the same as at the sending point.
     *
     * @param message   message to schedule.
     */
    void schedule(Message message);

    /**
     * Returns the next message in the scheduler. If the scheduler contains no more message then null is
     * returned.
     *
     * @return the next message in the scheduler or null if there is no messages.
     */
    Message nextMessage();

    /**
     * Checks if the scheduler has any messages left.
     *
     * @return true if the scheduler contains messages or false if the scheduler is empty.
     */
    boolean hasMessages();

    /**
     * Returns the current scheduler time. The scheduler's time is given by the time of the next message in
     * the scheduler.
     *
     * @return the current time of the scheduler.
     */
    int getTime();

    /**
     * Returns the seed used to generate the delays.
     *
     * @return the seed used to generate the delays.
     */
    long getSeed();

    /**
     * Returns the minimum delay (inclusive).
     *
     * @return the minimum delay (inclusive).
     */
    int getMinDelay();

    /**
     * Returns the maximum delay (inclusive).
     *
     * @return the maximum delay (inclusive).
     */
    int getMaxDelay();

    /**
     * Clears all messages from the scheduler.
     */
    void clear();

    /**
     * Resets the scheduler. Although the scheduler should not have state, it stores the messages to be
     * scheduled and might have some information to generate the delays that needs to be refreshed for
     * every new simulation. Therefore, this method should be called once before each simulation and should
     * be used to clear all messages from the scheduler and refresh any fields used to generate the delays.
     */
    void reset();

}
