package edu.luc.etl.cs313.android.simplestopwatch.model.time;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import java.io.IOException;

import edu.luc.etl.cs313.android.simplestopwatch.R;
import edu.luc.etl.cs313.android.simplestopwatch.model.clock.ClockModel;

import static edu.luc.etl.cs313.android.simplestopwatch.common.Constants.*;

/**
 * An implementation of the stopwatch data model.
 */
public class DefaultTimeModel implements TimeModel {

    private int DT = 0;
    private int Count = 3;
    private Context context;
    private boolean playing = true;

    public DefaultTimeModel(final Context context) {
        this.context = context;
    }

    @Override
    public void resetCount(){
        Count = 3;
    }

    @Override
    public int getCount(){
        return Count;
    }

    @Override
    public void decCount(){
        Count--;
    }

    @Override
    public void resetRuntime() {
        DT = 0;
    }

    @Override
    public void incDT() {
        DT = (DT + SEC_PER_TICK) % SEC_PER_HOUR;
    }

    @Override
    public void decDT() { DT--;}

    @Override
    public int getDT() {
        return DT;
    }

    @Override
    public void resetDT(){DT = 0;};

    /*play or stop playing an audio file BC*/
    @Override
    public void playNotification(boolean off) {
        final MediaPlayer alarmPlayer = new MediaPlayer().create(context, R.raw.tone);
        if (!off) {
            alarmPlayer.setLooping(false);
            alarmPlayer.start();
            this.playing = true;
        }
        else {
            alarmPlayer.release();
            this.playing = false;
        }
    }

    @Override
    public boolean isPlaying() {
        return this.playing;
    }
}