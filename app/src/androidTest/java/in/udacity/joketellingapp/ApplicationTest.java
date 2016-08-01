package in.udacity.joketellingapp;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.text.TextUtils;

import java.util.concurrent.CountDownLatch;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    CountDownLatch signal = null;
    String jokeString = null;

    public ApplicationTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        signal = new CountDownLatch(1);
    }

    @Override
    protected void tearDown() throws Exception {
        signal.countDown();
    }

    public void testAlbumGetTask() throws InterruptedException {

        EndpointsAsyncTask task = new EndpointsAsyncTask(new InterfaceDeliverJoke() {
            @Override
            public void onDeliver(String joke) {
                jokeString = joke;
            }
        });
        task.execute();
        signal.await();

        assertNull(jokeString);
        assertFalse(TextUtils.isEmpty(jokeString));

    }
}