package core.schedulers;

import core.MRAITimer;
import core.Message;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Implements some of the common operations of the scheduler that are the same for most scheduler
 * implementations.
 */
public abstract class AbstractScheduler implements Scheduler {

    // The priority queue is used to schedule the messages using their arrival times to
    // define the priority of the messages. Messages with lower arrival times have higher priority.
    private final PriorityQueue<Message> queue = new PriorityQueue<>();

    // priority queue to schedule timers - timers are scheduled based on their expiration time
    private final PriorityQueue<MRAITimer> timerQueue = new PriorityQueue<>((o1, o2) -> {
        int comparison = o1.getExpirationTime() - o2.getExpirationTime();
        if (comparison == 0) {
            comparison = o1.getOwner().getId() - o2.getOwner().getId();
        }

        return comparison;
    });

    /**
     * Adds a new message to the scheduler. The time of the message should correspond to the time at which
     * the router sent the message. The scheduler will add to this time some delay corresponding to the
     * time between the message is sent and the message reaches the target. The scheduler will always take
     * into account the time associated with the message's link, which means that the order of the messages
     * at the receiving end will be the same as at the sending point.
     *
     * @param message message to schedule.
     */
    @Override
    public void schedule(Message message) {
        int arrivalTimeWithDelay = message.getArrivalTime() + delay();
        int lastMessageArrivalTime = message.getTraversedLink().getLastArrivalTime();

        // the message must arrive after the last message sent through the same link
        int messagesArrivalTime = Integer.max(arrivalTimeWithDelay, lastMessageArrivalTime + 1);
        message.setArrivalTime(messagesArrivalTime);
        message.getTraversedLink().setLastArrivalTime(messagesArrivalTime);

        queue.offer(message);
    }

    /**
     * Schedules an MRAI timer in the scheduler. When the MRAI timer expires it stored in a list
     * of expired timers that can be obtained when calling the getExpiredTimers() method.
     *
     * @param timer timer to schedule.
     */
    @Override
    public void schedule(MRAITimer timer) {
        timerQueue.offer(timer);
    }

    /**
     * Returns the next message in the scheduler. If the scheduler contains no more message then null is
     * returned.
     *
     * @return the next message in the scheduler or null if there is no messages.
     */
    @Override
    public Message nextMessage() {
        return queue.poll();
    }

    /**
     * Checks if the scheduler has any messages left.
     *
     * @return true if the scheduler contains messages or false if the scheduler is empty.
     */
    @Override
    public boolean hasMessages() {
        return !queue.isEmpty();
    }

    /**
     * Returns a collection with all the timers that have expired since the construction of the
     * scheduler or the last time this method was called.
     * <p>
     * The scheduler keeps a list of all timers that expire while time progresses. When the
     * current time reaches the expiration time of a timer, this timer is put on a list. This
     * list is the one returned by this method. After calling this method the list is cleared.
     *
     * @return collection with all the timers that have expired since the construction of the
     * scheduler or the last time this method was called.
     */
    @Override
    public Collection<MRAITimer> getExpiredTimers() {
        List<MRAITimer> expiredTimers = new ArrayList<>();

        int currentTime = getTime();

        MRAITimer timer = timerQueue.peek();
        while (timer != null && timer.hasExpired(currentTime)) {
            expiredTimers.add(timer);
            timerQueue.poll();  // remove the expired timer from the queue
            timer = timerQueue.peek();  // move to the next timer in the queue
        }

        return expiredTimers;
    }

    /**
     * Returns the current scheduler time. The scheduler's time is given by the time of the next message in
     * the scheduler.
     *
     * @return the current time of the scheduler.
     */
    @Override
    public int getTime() {

        // look into the the next message without removing it from the queue
        Message message = queue.peek();

        if (message == null) {
            // the scheduler is empty
            return Integer.MAX_VALUE;
        } else {
            return message.getArrivalTime();
        }

    }

    /**
     * Clears all messages from the scheduler.
     */
    @Override
    public void clear() {
        queue.clear();
    }

    /**
     * Clears all messages from the scheduler.
     */
    @Override
    public void reset() {
        clear();
    }

    /**
     * Method used to introduce delay in the messages. All subclasses should implement this method to
     * provide delay to the messages. The method is called every time a new message is added to the scheduler.
     *
     * @return delay value.
     */
    protected abstract int delay();

}
