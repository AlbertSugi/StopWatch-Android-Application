package edu.luc.etl.cs313.android.simplestopwatch.common;

import android.content.Context;

/**
 * A listener for UI update notifications.
 * This interface is typically implemented by the adapter, with the
 * notifications coming from the model.
 *
 */
public interface TimerUIUpdateListener {
    void updateTime(int timeValue);
    void updateState(int stateId);
    void updateButton(String s);
    String getButton();
}
