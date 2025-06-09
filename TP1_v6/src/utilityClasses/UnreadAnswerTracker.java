package utilityClasses;

import java.util.HashMap;
import java.util.Map;

import entityClasses.Question;

/**
 * Utility class used to track the number of answers a student has already viewed
 * for each question. This allows the UI to indicate how many new answers have
 * been received since the last time the student viewed the responses.
 */
public class UnreadAnswerTracker {

    private final Map<Question, Integer> lastSeenCounts = new HashMap<>();

    /**
     * Return the number of unread answers for the given question.
     *
     * @param q the question to check
     * @return the number of new answers since the question was last viewed
     */
    public int getUnreadCount(Question q) {
        int lastSeen = lastSeenCounts.getOrDefault(q, 0);
        int current = q.getNumAnswer();
        return Math.max(0, current - lastSeen);
    }

    /**
     * Mark all current answers for the given question as read.
     *
     * @param q the question that has just been viewed
     */
    public void markAsRead(Question q) {
        lastSeenCounts.put(q, q.getNumAnswer());
    }
}