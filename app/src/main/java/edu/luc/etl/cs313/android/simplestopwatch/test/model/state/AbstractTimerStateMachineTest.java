package edu.luc.etl.cs313.android.simplestopwatch.test.model.state;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.luc.etl.cs313.android.simplestopwatch.R;
import edu.luc.etl.cs313.android.simplestopwatch.common.TimerUIUpdateListener;
import edu.luc.etl.cs313.android.simplestopwatch.model.clock.ClockModel;
import edu.luc.etl.cs313.android.simplestopwatch.model.clock.OnTickListener;
import edu.luc.etl.cs313.android.simplestopwatch.model.state.TimerStateMachine;
import edu.luc.etl.cs313.android.simplestopwatch.model.time.TimeModel;

/**
 * Testcase superclass for the stopwatch state machine model. Unit-tests the state
 * machine in fast-forward mode by directly triggering successive tick events
 * without the presence of a pseudo-real-time clock. Uses a single unified mock
 * object for all dependencies of the state machine model.
 *
 * @author Albert
 * xunitpatterns.com/Testcase%20Superclass.html
 */
public abstract class AbstractTimerStateMachineTest {

    private TimerStateMachine model;

    private UnifiedMockDependency dependency;

    @Before
    public void setUp() throws Exception {
        dependency = new UnifiedMockDependency();
    }

    @After
    public void tearDown() {
        dependency = null;
    }

    /**
     * Setter for dependency injection. Usually invoked by concrete testcase
     * subclass.
     *
     * @param model
     */
    protected void setModel(final TimerStateMachine model) {
        this.model = model;
        if (model == null)
            return;
        this.model.setUIUpdateListener(dependency);
        this.model.actionInit();
    }

    protected UnifiedMockDependency getDependency() {
        return dependency;
    }

    /**
     * On launch, app launches in Initial state with DT set to 0
     */
    @Test
    public void testInitialStateDT0l(){
        assertEquals(R.string.INITIAL, dependency.getState()); //app starts in initial state
        assertEquals(0, dependency.getDT()); //app starts with DT at 0
    }

    /**
     * When in Initial state button label == Increment
     */
    @Test
    public void testInitialButtonLabel() {
        assertEquals("Increment", model.getUIButton());
    }

    /**
     * When in Initial state, a click event increments DT and changes the state to Waiting
     */
    @Test
    public void testInitialClickIncrementsDTStateWaiting(){
        assertEquals(R.string.INITIAL, dependency.getState()); //Before is run app starts awaits input
        assertEquals(0, dependency.getDT()); //Check to see that DT == 0
        model.onClick(); // set state to waiting and increment DT
        assertEquals(R.string.WAITING, dependency.getState()); // confirm changes
        assertEquals(1, dependency.getDT());

    }

    /**
     * When in Waiting state button label == Increment
     */
    @Test
    public void testWaitingButtonLabel(){
        model.toWaiting_State(); // change to a waiting state
        assertEquals("Increment", model.getUIButton()); // test the button label text
    }


    /**
     * When in Waiting a click event increments DT and changes state to Waiting
     */
    @Test
    public void testWaitingClickIncrementsDTStateWaiting(){
        //we start in initial
        model.onClick(); // This should move us to Waiting, DT==1
        assertEquals(1, dependency.getDT());
        assertEquals(R.string.WAITING, dependency.getState());
        model.onClick(); // DT is incremented but still in a waiting state
        assertEquals(2, dependency.getDT());
        assertEquals(R.string.WAITING, dependency.getState());
    }

    /**
     * When in Waiting clicking up to 97 times results in a waiting state
     */
    @Test
    public void testWaitingClick97WaitingState(){
        //Before is run app starts awaits input
        assertEquals(R.string.INITIAL, dependency.getState());
        model.onClick(); // should set state to waiting and DT to 1
        assertEquals(R.string.WAITING, dependency.getState());
        for (int x = 0; x < 96; x++) {
            model.onClick(); //click 96 times
        }
        assertEquals(97, dependency.getDT()); //confirm the DT is now 97
        assertEquals(R.string.WAITING,dependency.getState()); //confirm the state is still waiting
    }

    /**
     * Clicking above 99 times results in CountDown
     */
    @Test
    public void testWaitingClick100CountDown(){
        //Before is run app starts awaits input
        assertEquals(R.string.INITIAL, dependency.getState());
        model.onClick(); // should set state to waiting and DT to 1
        assertEquals(R.string.WAITING, dependency.getState());
        for (int x = 0; x < 99; x++) {
            model.onClick(); //click 99 times
        }
        assertEquals(R.string.COUNTDOWN,dependency.getState()); //check that state was changed to count down
    }

    /**
     * When in Waiting a tick event decreases count by 1 and updates the button display
     */
    @Test
    public void testWaitingDecreaseCountUpdateDisplay(){
        model.onClick(); //set state to waiting
        assertEquals(3,dependency.getCount());// confirm count is 3
        model.onTick();
        assertEquals("Increment 3", model.getUIButton()); //test the button label
        model.onTick();
        assertEquals(2,dependency.getCount());
        assertEquals("Increment 2", model.getUIButton()); //test the button label
    }

    /**
     * When in Waiting if count ==0 then beep once and change state to  CountDown
     */
    @Test
    public void testWaitingCountDown(){
        model.onClick(); //set state to waiting
        assertEquals(3,dependency.getCount()); //confirm count is 3
        model.onTick();
        assertEquals(2,dependency.getCount()); //confirm count is 2
        model.onTick();
        assertEquals(1,dependency.getCount()); //confirm count is 1
        model.onTick();
        assertTrue(model.actionIsPlaying());
        assertEquals(R.string.COUNTDOWN, dependency.getState()); //confirm state is CountDown
    }

    /**
     * When in Waiting if DT == 100 then beep once and change state to  CountDown
     */
    @Test
    public void testWaitingDT100CountDown(){
        for (int x = 1; x <= 100; x++) {
            model.onClick(); //click 100 times
        }
        assertTrue(model.actionIsPlaying());
        assertEquals(R.string.COUNTDOWN, dependency.getState()); //confirm state is CountDown
    }

    /**
     * When in CountDown state: button label == Cancel
     */
    @Test
    public void testCountDownButtonLabel(){
        model.toCountDown_State(); // change to alarming state
        assertEquals("Cancel", model.getUIButton()); //test the button label
    }

    /**
     * When in CountDown on tick event decrease DT by 1
     */
    @Test
    public void testCountDownTickDecreases1(){
        model.onClick(); //increment by 1
        model.onClick(); //increment by 2
        assertEquals(2, dependency.getDT());
        assertEquals(R.string.WAITING, dependency.getState());//check count down state
        model.onTick(); //wait 1 tick
        model.onTick(); //wait 2 ticks
        model.onTick(); //wait 3 ticks
        model.onTick(); //DT begins to decrease
        assertEquals(R.string.COUNTDOWN, dependency.getState());//check count down state
        assertEquals(1, dependency.getDT());
    }

    /**
     * When in CountDown after 15 ticks, DT is decremented 15 times
     */
    @Test
    public void testCountDownTickDecreases15(){
        assertEquals(R.string.INITIAL, dependency.getState());
        model.onClick(); // should set state to waiting
        assertEquals(R.string.WAITING, dependency.getState());

        for (int x = 0; x < 20; x++) {
            model.onClick(); //click 20 times
        }
        model.onTick();
        model.onTick();
        model.onTick();
        assertEquals(21,dependency.getDT());
        for (int i = 0; i < 15; i++) {
            model.onTick();
        }
        assertEquals(6,dependency.getDT());

    }

    /**
     * When in CountDown on click event ends the clock and sets state to Initial
     */
    @Test
    public void testCountDownClickStateInitial(){
        model.onClick(); //increment by 1
        model.onClick(); //increment by 2
        assertEquals(2, dependency.getDT());
        assertEquals(R.string.WAITING, dependency.getState());//check count down state
        model.onTick(); //wait 1 tick
        model.onTick(); //wait 2 ticks
        model.onTick(); //wait 3 ticks
        model.onTick(); //DT begins to decrease
        assertEquals(R.string.COUNTDOWN, dependency.getState());//check count down state
        model.onClick(); // click returns state to initial
        assertEquals(R.string.INITIAL, dependency.getState());//check initial state
    }

    /**
     * When in CountDown if DT==0 then change state to Alarming
     */
    @Test
    public void testCountDownDT0Alarming(){

        //Before is run app starts awaits input
        assertEquals(R.string.INITIAL, dependency.getState());
        model.onClick(); // should set state to waiting
        assertEquals(R.string.WAITING, dependency.getState());



        for (int x = 0; x < 99; x++) {
            model.onClick(); //click 98 times
        }
        assertEquals(R.string.COUNTDOWN,dependency.getState());

        for (int x = 0; x < 100; x++) {
            model.onTick(); //tick 99 times
        }

        assertEquals(0, dependency.getDT());
        model.toAlarming_State();

    }

    /**
     * When in Alarming state: button label == Stop
     */
    @Test
    public void testAlarmingButtonLabel(){
        model.toAlarming_State(); // change to alarming state
        assertEquals("Stop", model.getUIButton()); //test the button label
    }

    /**
     * When in Alarming a sound is played every tick.
     */
    @Test
    public void testAlarmingSound(){
        model.onClick(); //set state to waiting
        assertEquals(1, dependency.getDT());
        assertEquals(3,dependency.getCount()); //confirm count is 3
        model.onTick();
        assertEquals(2,dependency.getCount()); //confirm count is 2
        model.onTick();
        assertEquals(1,dependency.getCount()); //confirm count is 1
        model.onTick();
        assertEquals(3,dependency.getCount()); //confirm count has reset to 3
        model.onTick();
        assertEquals(0, dependency.getDT());
        assertEquals(R.string.ALARMING, dependency.getState()); //confirm state is Alarming
        assertTrue(model.actionIsPlaying()); //check alarm is playing
        model.onTick();
        assertTrue(model.actionIsPlaying()); //check alarm is still playing
        model.onClick();
        assertFalse(model.actionIsPlaying()); //check that alarm turned off
    }

    /**
     * When in Alarming on click event will end the Alarm, end the clock, and change state to Initial
     */
    @Test
    public void testAlarmingClickEndStateInitial(){
        model.onClick(); //set state to waiting
        assertEquals(1, dependency.getDT());
        assertEquals(3,dependency.getCount()); //confirm count is 3
        model.onTick();
        assertEquals(2,dependency.getCount()); //confirm count is 2
        model.onTick();
        assertEquals(1,dependency.getCount()); //confirm count is 1
        model.onTick();
        assertEquals(3,dependency.getCount()); //confirm count has reset to 3
        model.onTick();
        assertEquals(0, dependency.getDT());
        assertEquals(R.string.ALARMING, dependency.getState()); //confirm state is Alarming
        model.onClick(); //turn off alarm
        assertEquals(R.string.INITIAL, dependency.getState()); //confirm state is Initial
    }

    /**
     * Sends the given number of tick events to the model.
     *  @param n the number of tick events
     */
    protected void onTickRepeat(final int n) {
        for (int i = 0; i < n; i++)
            model.onTick();
    }

    /**
     * Checks whether the model has invoked the expected time-keeping
     * methods on the mock object.
     */
    protected void assertTimeEquals(final int t) {
        assertEquals(t, dependency.getTime());
    }
}

/**
 * Manually implemented mock object that unifies the three dependencies of the
 * stopwatch state machine model. The three dependencies correspond to the three
 * interfaces this mock object implements.
 *
 * @author Albert
 */
class UnifiedMockDependency implements TimeModel, ClockModel, TimerUIUpdateListener {

    private int timeValue = 0, stateId = -1;
    private int DT = 0, Count =3;
    private String buttonLabel = "None";
    private boolean started = false;
    private boolean playing;


    public int getTime() {
        return timeValue;
    }

    public int getState() {
        return stateId;
    }

    public boolean isStarted() {
        return started;
    }
    @Override
    public void resetDT(){DT = 0;};
    @Override
    public void updateTime(final int timeValue) {
        this.timeValue = timeValue;
    }

    @Override
    public void updateState(final int stateId) {
        this.stateId = stateId;
    }

    @Override
    public void updateButton(String s) {
        this.buttonLabel = s;
    }

    @Override
    public void setOnTickListener(OnTickListener listener) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void start() {
        started = true;
    }

    @Override
    public void stop() {
        started = false;
    }

    @Override
    public void resetRuntime() {
        DT = 0;
    }

    @Override
    public void incDT() {
        DT++;
    }

    @Override
    public void decDT() { DT--; }

    @Override
    public int getDT() {
        return DT;
    }

    @Override
    public void resetCount(){
        Count =3;
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
    public boolean isPlaying() { return playing; }

    @Override
    public void playNotification(boolean off) {
        if (!off) {
            playing = true;
        }
        else {
            playing = false;
        }
    }

    @Override
    public String getButton() {
        return buttonLabel;
    }

}
