package edu.luc.etl.cs313.android.simplestopwatch.model.state;
import edu.luc.etl.cs313.android.simplestopwatch.R;

class Alarming_State implements TimerState {

    public Alarming_State(final TimerSMStateView sm) {
        this.sm = sm;
    }

    private final TimerSMStateView sm;

    /*Stop tick, stop sound, switch to initial state BC*/
    @Override
    public void onClick() {
        sm.actionStop();
        sm.actionPlaySound(true);
        sm.toInitial_State();
        sm.updateUIButton("Increment");
        sm.actionResetDT();
    }

    /*Play sound on tick BC*/
    @Override
    public void onTick() {
        sm.actionPlaySound(false);
    }

    @Override
    public void updateView() {
        sm.updateUIRuntime();
    }

    @Override
    public int getId() {
        return R.string.ALARMING;
    }
}
