package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import edu.luc.etl.cs313.android.simplestopwatch.R;

class CountDown_State implements TimerState {

    public CountDown_State(final TimerSMStateView sm) {
        this.sm = sm;
    }

    private final TimerSMStateView sm;

    @Override
    public void onClick() {
        sm.actionStop();
        sm.toInitial_State(); // This should go to Initial State
        sm.updateUIButton("Increment");
        sm.actionResetDT();
    }



    @Override
    public void onTick() {
        sm.actionDec(); //Decrement the DT
        if (sm.actiongetDT() == 0){//If  the DT is 0
            sm.toAlarming_State(); //Change the state to Alarming
            sm.updateUIButton("Stop");
        }
    }

    @Override
    public void updateView() {
        sm.updateUIRuntime();
    }

    @Override
    public int getId() {
        return R.string.COUNTDOWN;
    }
}
