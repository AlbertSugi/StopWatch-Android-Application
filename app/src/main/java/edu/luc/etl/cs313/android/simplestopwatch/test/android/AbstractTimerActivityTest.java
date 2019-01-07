package edu.luc.etl.cs313.android.simplestopwatch.test.android;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import android.app.Activity;

import org.junit.Test;

import android.content.pm.ActivityInfo;
import android.widget.Button;
import android.widget.TextView;
import edu.luc.etl.cs313.android.simplestopwatch.R;
import edu.luc.etl.cs313.android.simplestopwatch.android.TimerAdapter;

import static edu.luc.etl.cs313.android.simplestopwatch.common.Constants.SEC_PER_MIN;

/**
 * Abstract GUI-level test superclass of several essential stopwatch scenarios.
 *
 * @author Albert
 *
 * non-instrumentation unit tests properly.
 */
public abstract class AbstractTimerActivityTest {

    /**
     * Verifies that the activity under test can be launched.
     */
    @Test
    public void testActivityCheckTestCaseSetUpProperly() {
        assertNotNull("activity should be launched successfully", getActivity());
    }

    /**
     * Verifies the following scenario: time is 0.
     *
     * @throws Throwable
     */
    @Test
    public void testActivityScenarioInit() throws Throwable {
        getActivity().runOnUiThread(() -> assertEquals(0, getDisplayedValue()));
    }


    /**
     * When in Initial state, when rotated display remains the same
     *
     */
    @Test
    public void testInitialRotate() throws Throwable {

        //UiThread way
        getActivity().runOnUiThread(() -> {
            //How we check the DT value
            assertEquals(0, getDisplayedValue());
        });
        runUiThreadTasks();
        //Rotation
        getActivity().runOnUiThread(() -> {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        });
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            //How we check the DT value
            assertEquals(0, getDisplayedValue());
            //How we perform a click
        });
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        });
        //Confirmation
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            //How we check the DT value
            assertEquals(0, getDisplayedValue());
            //How we perform a click
        });







    }
    /**
     * When in Waiting state, when rotated display remains the same
     *
     */
    @Test
    public void testWaitingRotate() throws Throwable {
        //UiThread way
        getActivity().runOnUiThread(() -> {
            //How we check the DT value
            assertEquals(0, getDisplayedValue());
            assertTrue(getStartStopButton().performClick());
        });
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            //How we check the DT value
            assertEquals(1, getDisplayedValue());

        });

        runUiThreadTasks();
        //Rotation
        getActivity().runOnUiThread(() -> {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        });
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            //How we check the DT value
            assertEquals(1, getDisplayedValue());
            //How we perform a click
        });
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        });
        //Confirmation
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            //How we check the DT value
            assertEquals(1, getDisplayedValue());
            //How we perform a click
        });

    }/**
     * When in CountDown state, when rotated display remains the same
     *
     */
    @Test
    public void testCountDownRotate() throws Throwable {
        //UiThread way
        getActivity().runOnUiThread(() -> {
            //How we check the DT value
            assertEquals(0, getDisplayedValue());
            for (int i=0; i < 8; i++) {
                assertTrue(getStartStopButton().performClick());
            }
        });
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            //How we check the DT value
            assertEquals(8, getDisplayedValue());

        });
        Thread.sleep(4000);
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            //How we check the DT value
            assertEquals(8, getDisplayedValue());

        });

        runUiThreadTasks();
        //Rotation
        getActivity().runOnUiThread(() -> {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        });
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            //How we check the DT value
            assertEquals(8, getDisplayedValue());
            //How we perform a click
        });
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        });
        //Confirmation
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            //How we check the DT value
            assertEquals(8, getDisplayedValue());
            //How we perform a click
        });





    }/**
     * When in Alarming state, when rotated display remains the same
     *
     */
    @Test
    public void testAlarmingRotate() throws Throwable {
        //UiThread way
        getActivity().runOnUiThread(() -> {
            //How we check the DT value
            assertEquals(0, getDisplayedValue());
            for (int i=0; i < 8; i++) {
                assertTrue(getStartStopButton().performClick());
            }
        });
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            //How we check the DT value
            assertEquals(8, getDisplayedValue());

        });
        Thread.sleep(4500);
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            //How we check the DT value
            assertEquals(7, getDisplayedValue());

        });
        Thread.sleep(10000);
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            //How we check the DT value
            assertEquals(0, getDisplayedValue());

        });
        runUiThreadTasks();
        //Rotation
        getActivity().runOnUiThread(() -> {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        });
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            //How we check the DT value
            assertEquals(0, getDisplayedValue());
            //How we perform a click
        });
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        });
        //Confirmation
        runUiThreadTasks();
        getActivity().runOnUiThread(() -> {
            //How we check the DT value
            assertEquals(0, getDisplayedValue());
            //How we perform a click
        });







    }
    /**
     * when in Initial state button label == Increment
     */
    @Test
    public void testInitialButtonLabel() {
        getActivity().runOnUiThread(() -> {
            checkButtonText("Increment");
        });

    }


    // auxiliary methods for easy access to UI widgets
    private Activity getCurrentActivity(){
        return TimerAdapter.currentActivity;
    }
    protected abstract TimerAdapter getActivity();

    protected int tvToInt(final TextView t) {
        return Integer.parseInt(t.getText().toString().trim());
    }

    protected int getDisplayedValue() {
        final TextView ts = (TextView) getCurrentActivity().findViewById(R.id.seconds);

        return tvToInt(ts);
    }
    protected boolean checkButtonText(String toCheck) {
        final TextView buttonTextView = (TextView) getCurrentActivity().findViewById(R.id.Button_Label);
        final String buttonText = buttonTextView.getText().toString().trim();
        return toCheck.equals(buttonText);
    }

    protected Button getStartStopButton() {
        return (Button) getCurrentActivity().findViewById(R.id.Button_Label);
    }

    /**
     * Explicitly runs tasks scheduled to run on the UI thread in case this is required
     * by the testing framework, e.g., Robolectric.
     */
    protected void runUiThreadTasks() { }
}