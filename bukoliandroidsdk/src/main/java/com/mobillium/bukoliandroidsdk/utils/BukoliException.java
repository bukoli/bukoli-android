package com.mobillium.bukoliandroidsdk.utils;

/**
 * Represents an error condition specific to the Bukoli SDK for Android.
 */
public class BukoliException extends RuntimeException {
    static final long serialVersionUID = 1;

    /**
     * Constructs a new BukoliException.
     */
    public BukoliException() {
        super();
    }

    /**
     * Constructs a new BukoliException.
     *
     * @param message the detail message of this exception
     */
    public BukoliException(String message) {
        super(message);
    }

    /**
     * Constructs a new BukoliException.
     *
     * @param format the format string (see {@link java.util.Formatter#format})
     * @param args   the list of arguments passed to the formatter.
     */
    public BukoliException(String format, Object... args) {
        this(String.format(format, args));
    }

    /**
     * Constructs a new BukoliException.
     *
     * @param message   the detail message of this exception
     * @param throwable the cause of this exception
     */
    public BukoliException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Constructs a new BukoliException.
     *
     * @param throwable the cause of this exception
     */
    public BukoliException(Throwable throwable) {
        super(throwable);
    }

    @Override
    public String toString() {
        // Throwable.toString() returns "BukoliException:{message}". Returning just "{message}"
        // should be fine here.
        return getMessage();
    }
}
