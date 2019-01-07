package edu.luc.etl.cs313.android.simplestopwatch.android;

import android.test.ActivityInstrumentationTestCase2;

import edu.luc.etl.cs313.android.simplestopwatch.test.android.AbstractTimerActivityTest;


/**
 * Concrete Android test subclass. Has to inherit from framework class
 * and uses delegation to concrete subclass of abstract test superclass.
 * IMPORTANT: project must export JUnit 4 to make it available on the
 * device.
 *
 * @author Albert
 * @see http://developer.android.com/tools/testing/activity_testing.html
 */
public class StopwatchActivityTest extends ActivityInstrumentationTestCase2<TimerAdapter> {

    /**
     * Creates an {@link ActivityInstrumentationTestCase2} for the
     * {@link SkeletonActivity} activity.
     */
    public StopwatchActivityTest() {
        super(TimerAdapter.class);
        actualTest = new AbstractTimerActivityTest() {
            @Override
            protected TimerAdapter getActivity() {
                // return activity instance provided by instrumentation test
                return StopwatchActivityTest.this.getActivity();
            }
        };
    }

    private AbstractTimerActivityTest actualTest;

    public void testActivityCheckTestCaseSetUpProperly() {
        actualTest.testActivityCheckTestCaseSetUpProperly();
    }

    public void testInitialRotate() throws Throwable {
        actualTest.testInitialRotate();
    }
    public void testWaitingRotate() throws Throwable {
        actualTest.testWaitingRotate();
    }
    public void testCountDownRotate() throws Throwable {
        actualTest.testCountDownRotate();
    }
    public void testAlarmingRotate() throws Throwable {
        actualTest.testAlarmingRotate();
    }

}
