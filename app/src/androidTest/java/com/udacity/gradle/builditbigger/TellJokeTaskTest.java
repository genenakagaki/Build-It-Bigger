package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.support.test.filters.LargeTest;
import android.support.test.filters.SmallTest;
import android.support.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Created by gene on 12/28/16.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TellJokeTaskTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @org.junit.Before
    public void setUp() throws Exception {

    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    @org.junit.Test
    public void testTask() throws Throwable {
        // create  a signal to let us know when our task is done.
        final CountDownLatch signal = new CountDownLatch(1);

        final String[] taskResult = {new String()};

        final TellJokeTask tellJokeTask = new TellJokeTask(null) {

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                taskResult[0] = result;

            /* This is the key, normally you would use some type of listener
             * to notify your activity that the async call was finished.
             *
             * In your test method you would subscribe to that and signal
             * from there instead.
             */
                signal.countDown();
            }
        };

        // Execute the async task on the UI thread! THIS IS KEY!
        mActivityRule.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                tellJokeTask.execute(mActivityRule.getActivity());
            }
        });


        /* The testing thread will wait here until the UI thread releases it
         * above with the countDown() or 30 seconds passes and it times out.
         */
        signal.await(30, TimeUnit.SECONDS);

        // The task is done, and now you can assert some things!
        assertEquals("What do you serve but not eat? \n A tennis ball!", taskResult[0]);
    }

}