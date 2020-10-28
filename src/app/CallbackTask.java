package app;

import app.Interfaces.Callback;

public class CallbackTask implements Runnable {
    private final Runnable task;
    private final Callback callback;


    CallbackTask(Runnable task, Callback callback) {
        this.task = task;
        this.callback = callback;
    }

    @Override
    public void run() {
        task.run();
        callback.runCallback();
    }
}
