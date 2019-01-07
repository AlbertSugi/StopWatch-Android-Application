package edu.luc.etl.cs313.android.simplestopwatch.test.android;

import edu.luc.etl.cs313.android.simplestopwatch.BuildConfig;
import edu.luc.etl.cs313.android.simplestopwatch.android.TimerAdapter;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Concrete Robolectric test subclass. For the Gradle unitTest task to work,
 * the Robolectric dependency needs to be isolated here instead of being present in src/main.
 *
 * @author Albert
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22)
public class TimerActivityRobolectric extends AbstractTimerActivityTest {

    private static String TAG = "stopwatch-android-activity-robolectric";

    private TimerAdapter activity;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(TimerAdapter.class).create().start().visible().get();
    }

    @Override
    protected void runUiThreadTasks() {
        // Robolectric requires us to run the scheduled tasks explicitly!
        org.robolectric.shadows.ShadowLooper.runUiThreadTasks();
    }

    @Override
    protected TimerAdapter getActivity() {
        return activity;
    }
}
