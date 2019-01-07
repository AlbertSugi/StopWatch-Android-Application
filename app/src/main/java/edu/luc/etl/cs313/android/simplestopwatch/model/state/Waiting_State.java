package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import edu.luc.etl.cs313.android.simplestopwatch.R;

class Waiting_State implements TimerState {

    public Waiting_State(final TimerSMStateView sm) {
        this.sm = sm;
    }

    private final TimerSMStateView sm;

    @Override
    public void onClick() { //when we receive a click
        if (sm.actiongetDT() < 99) { //if click is at max
            try { sm.actionStop(); } //stop previously started action
            catch (Exception e) {} //catch exception if no action was started
            sm.actionInc(); //increment DT
            sm.actionStart(); //start counting seconds of wait time
        }
        else {
            try {
                sm.actionStop();
            } //stop previously started action
            catch (Exception e) {
            } //catch exception if no action was started
            sm.actionPlaySound(false); //play sound
            sm.actionStart(); //begin tick for count down
            sm.toCountDown_State(); // go to count down state BC
            sm.updateUIButton("Cancel"); //update button label
        }
    }


    @Override
    public void updateView() {
        sm.updateUIRuntime();
    }


    @Override
    public void onTick() { //on a Tick
        sm.updateUIButton("Increment " + sm.actiongetCount()); //update button label
        sm.actiondecCount(); //Decrement the Count
        if (sm.actiongetCount() == 0) {//If  the Count is 0
            sm.actionResetCount(); //Reset the Count
            sm.actionPlaySound(false); //play sound
            sm.updateUIButton("Cancel");
            sm.toCountDown_State(); //Change the state to CountingDown
        }
    }



    @Override
    public int getId() {
        return R.string.WAITING;
    }
}
