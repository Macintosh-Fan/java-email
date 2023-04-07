package com.macintoshfan.oldmail;

/**
 * A similar class to {@link IllegalArgumentException}, but for ignored arguments.
 *
 * @author Macintosh-Fan
 */
public class IgnoredArgumentException extends RuntimeException {
    /**
     * Instantiates the exception.
     *
     * @param message the message to display
     */
    public IgnoredArgumentException(String message) {
        super(message);
    }
}
