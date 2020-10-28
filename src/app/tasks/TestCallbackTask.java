package app.tasks;

import app.interfaces.Callback;

public class TestCallbackTask implements Callback {
    @Override
    public void runCallback() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("An error occurred while waiting for the timer: " + e.getMessage());
        }
        System.out.println("callback");
    }
}
