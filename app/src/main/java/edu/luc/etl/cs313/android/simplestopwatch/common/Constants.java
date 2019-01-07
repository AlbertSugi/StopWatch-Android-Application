package edu.luc.etl.cs313.android.simplestopwatch.common;

/**
 * Constants for the time calculations used by the stopwatch.
 */
public final class Constants {

    public static int SEC_PER_TICK = 1;
    public static int SEC_PER_MIN  = 100; //changed to 100 to see if this is why our app couldn't count to 100 BC
    public static int SEC_PER_HOUR = 3600;

    private Constants() { }
}