package edu.luc.etl.cs313.android.simplestopwatch.test.model.time;

import android.content.Context;

import org.junit.After;
import org.junit.Before;

import edu.luc.etl.cs313.android.simplestopwatch.model.time.DefaultTimeModel;

/**
 * Concrete testcase subclass for the default time model implementation.
 *
 * @author Albert
 *
 */
public class DefaultTimeModelTest extends AbstractTimeModelTest {

    public Context context;

    @Before
    public void setUp() throws Exception {
        setModel(new DefaultTimeModel(context));
    }

    @After
    public void tearDown() throws Exception {
        setModel(null);
    }
}
