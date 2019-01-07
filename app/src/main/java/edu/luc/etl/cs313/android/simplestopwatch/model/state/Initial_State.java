package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import edu.luc.etl.cs313.android.simplestopwatch.R;

class Initial_State implements TimerState {

    public Initial_State(final TimerSMStateView sm) {
        this.sm = sm;
    }

    private final TimerSMStateView sm;

    @Override
    public void onClick() { //when a click happens
        sm.toWaiting_State(); //move to waiting state
        sm.actionResetCount(); // reset the Count to 0 JiC
        sm.actionInc(); // Increment DT
    }



    @Override
    public void onTick() {// There shouldn't be a clock running for this state so this method throws an exception
        throw new UnsupportedOperationException("onTick");
    }



    @Override
    public void updateView() {
        sm.updateUIRuntime();
    }

    @Override
    public int getId() {
        return R.string.INITIAL;
    }
}
