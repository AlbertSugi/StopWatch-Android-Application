package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import android.content.Context;

import edu.luc.etl.cs313.android.simplestopwatch.common.TimerUIUpdateListener;
import edu.luc.etl.cs313.android.simplestopwatch.model.clock.ClockModel;
import edu.luc.etl.cs313.android.simplestopwatch.model.time.TimeModel;


/**
 * An implementation of the state machine for the stopwatch.
 *
 * @author Albert
 */
public class DefaultTimerStateMachine implements TimerStateMachine {


    public DefaultTimerStateMachine(final TimeModel timeModel, final ClockModel clockModel) {
        this.timeModel = timeModel;
        this.clockModel = clockModel;
    }

    private final TimeModel timeModel;

    private final ClockModel clockModel;

    /**
     * The internal state of this adapter component. Required for the State pattern.
     */
    private TimerState state;



    protected void setState(final TimerState state) {
        this.state = state;
        uiUpdateListener.updateState(state.getId());
    }

    private TimerUIUpdateListener uiUpdateListener;

    @Override
    public void setUIUpdateListener(final TimerUIUpdateListener uiUpdateListener) {
        this.uiUpdateListener = uiUpdateListener;
    }

    // forward event uiUpdateListener methods to the current state
    // these must be synchronized because events can come from the
    // UI thread or the timer thread



    @Override public synchronized void onClick() { state.onClick(); }
    //@Override public synchronized void onLapReset()  { state.onLapReset(); }
    @Override public synchronized void onTick()      { state.onTick(); }

    @Override public void updateUIRuntime() { uiUpdateListener.updateTime(timeModel.getDT()); }
    @Override public void updateUIButton(String s) { uiUpdateListener.updateButton(s); }
    @Override public String getUIButton() { return uiUpdateListener.getButton(); }

    // known states
    private final TimerState ALARMING  = new Alarming_State(this);
    private final TimerState COUNTDOWN = new CountDown_State(this);
    private final TimerState INITIAL   = new Initial_State(this);
    private final TimerState WAITING   = new Waiting_State(this);

    // transitions
    @Override public void toCountDown_State()    { setState(COUNTDOWN); }
    @Override public void toAlarming_State()    { setState(ALARMING); }
    @Override public void toInitial_State() { setState(INITIAL); }
    @Override public void toWaiting_State() { setState(WAITING); }

    // actions
    @Override public void actionInit()       { toInitial_State(); actionReset(); }
    @Override public void actionReset()      { timeModel.resetRuntime(); actionUpdateView(); }
    @Override public void actionStart()      { clockModel.start(); }
    @Override public void actionStop()       { clockModel.stop(); }
    @Override public void actionInc()        { timeModel.incDT(); actionUpdateView(); }
    @Override public void actionDec()        { timeModel.decDT(); actionUpdateView(); }
    @Override public int actiongetDT()       { return timeModel.getDT(); }
    @Override public void actionUpdateView() { state.updateView(); }
    @Override public void actiondecCount()   { timeModel.decCount(); }
    @Override public int  actiongetCount()   { return timeModel.getCount(); }
    @Override public void actionResetCount()   { timeModel.resetCount(); }
    @Override public void actionPlaySound(boolean off) { timeModel.playNotification(off); }
    @Override public boolean actionIsPlaying() { return timeModel.isPlaying(); }
    @Override public void actionResetDT() {timeModel.resetDT();}
}
