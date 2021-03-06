package edu.luc.etl.cs313.android.simplestopwatch.common;
import android.content.Context;
/**
 * A source of UI update events for the stopwatch.
 * This interface is typically implemented by the model.
 *
 */
public interface TimerUIUpdateSource {
    void setUIUpdateListener(TimerUIUpdateListener listener);



}
