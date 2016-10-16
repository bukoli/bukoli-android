package com.mobillium.bukoliandroidsdk.utils;

/**
 * An Exception indicating that the Bukoli SDK has not been correctly initialized.
 */
public class BukoliSdkNotInitializedException extends BukoliException {
    static final long serialVersionUID = 1;

    /**
     * Constructs a BukoliSdkNotInitializedException with no additional information.
     */
    public BukoliSdkNotInitializedException() {
        super();
    }

    /**
     * Constructs a BukoliSdkNotInitializedException with a message.
     *
     * @param message A String to be returned from getMessage.
     */
    public BukoliSdkNotInitializedException(String message) {
        super(message);
    }

    /**
     * Constructs a BukoliSdkNotInitializedException with a message and inner error.
     *
     * @param message   A String to be returned from getMessage.
     * @param throwable A Throwable to be returned from getCause.
     */
    public BukoliSdkNotInitializedException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Constructs a BukoliSdkNotInitializedException with an inner error.
     *
     * @param throwable A Throwable to be returned from getCause.
     */
    public BukoliSdkNotInitializedException(Throwable throwable) {
        super(throwable);
    }
}
