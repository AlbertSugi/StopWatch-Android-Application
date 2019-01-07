package edu.luc.etl.cs313.android.simplestopwatch.model.time;

import android.content.Context;

/**
 * The passive data model of the stopwatch.
 * It does not emit any events.
 *
 * @author laufer
 */
public interface TimeModel {
    void resetRuntime();
    void incDT();
    void decDT();
    int getDT();
    void resetDT();
    void resetCount();
    int getCount();
    void decCount();
    void playNotification(boolean off);
    boolean isPlaying();
}