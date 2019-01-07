package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import android.content.Context;

/**
 * The restricted view states have of their surrounding state machine.
 * This is a client-specific interface in Peter Coad's terminology.
 *
 * @author laufer
 */
interface TimerSMStateView {

    // transitions

    // No longer used, left here as a model
    //void toRunningState();
    //void toStoppedState();

    // The following are the new transitions
    void toCountDown_State();
    void toAlarming_State();
    void toInitial_State();
    void toWaiting_State();


    // actions

    void actionInit();
    void actionReset();
    void actionStart();
    void actionStop();
    void actionInc();
    void actionDec();
    int actiongetDT();
    void actionUpdateView();
    void actiondecCount();
    int  actiongetCount();
    void actionResetCount();
    void actionPlaySound(boolean off);
    boolean actionIsPlaying();
    void actionResetDT();


    // state-dependent UI updates
    void updateUIRuntime();
    void updateUIButton(String s);
    String getUIButton();
}
