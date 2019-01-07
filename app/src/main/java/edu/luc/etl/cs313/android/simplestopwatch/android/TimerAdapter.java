package edu.luc.etl.cs313.android.simplestopwatch.android;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import edu.luc.etl.cs313.android.simplestopwatch.R;
import edu.luc.etl.cs313.android.simplestopwatch.common.Constants;
import edu.luc.etl.cs313.android.simplestopwatch.common.TimerUIUpdateListener;
import edu.luc.etl.cs313.android.simplestopwatch.model.ConcreteTimerModelFacade;
import edu.luc.etl.cs313.android.simplestopwatch.model.TimerModelFacade;

/**
 * A thin adapter component for the stopwatch.
 *
 * @author Albert
 */
public class TimerAdapter extends Activity implements TimerUIUpdateListener {

    private static String TAG = "stopwatch-android-activity";

    /**
     * The state-based dynamic model.
     */
    public static Activity currentActivity; // Used for test generation

    private TimerModelFacade model;

    protected void setModel(final TimerModelFacade model) {
        this.model = model;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //sets up a saved state
        currentActivity = this;
        // inject dependency on view so this adapter receives UI events
        setContentView(R.layout.activity_main);
        // inject dependency on model into this so model receives UI events
        final Context context = getApplicationContext();

        this.setModel(new ConcreteTimerModelFacade(context)); // gives the context to the facade for sound generation
        // inject dependency on this into model to register for UI updates

        model.setUIUpdateListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    /**
     * Runs immediately after on create, and starts the event loop.
     */
    @Override
    protected void onStart() {
        super.onStart();
        model.onStart();
    }

    /**
     * Updates the seconds and minutes in the UI.
     *
     * @param time
     */
    public void updateTime(final int time) {
        // UI adapter responsibility to schedule incoming events on UI thread
        runOnUiThread(() -> {
            final TextView tvS = (TextView) findViewById(R.id.seconds);

            final int seconds = time % Constants.SEC_PER_MIN;
            //final int minutes = time / Constants.SEC_PER_MIN;
            tvS.setText(Integer.toString(seconds / 10) + Integer.toString(seconds % 10));

        });
    }

    /**
     * Updates the state name in the UI.
     *
     * @param stateId
     */
    public void updateState(final int stateId) {
        // UI adapter responsibility to schedule incoming events on UI thread
        runOnUiThread(() -> {
            final TextView stateName = (TextView) findViewById(R.id.stateName);
            stateName.setText(getString(stateId));
        });
    }

    // forward event listener methods to the model
    public void onClick(final View view) {
        model.onClick();
    }

    // Updates the button label in the UI
    public void updateButton(String s) {
        runOnUiThread(() -> {
            final TextView tvS = (TextView) findViewById(R.id.Button_Label);
            tvS.setText(s);
        });
    }

    // Get's the current button label
    public String getButton() {
        final TextView tvS = (TextView) findViewById(R.id.Button_Label);
        return tvS.getText().toString();
    }
}