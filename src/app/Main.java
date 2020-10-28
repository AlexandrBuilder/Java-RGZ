package app;

import app.tasks.TestCallbackTask;
import app.tasks.TestTask;

public class Main {
    public static void testWithPool() {
        TreadPoolExecutor treadPoolExecutor = new TreadPoolExecutor(4);

        TestTask testTask = new TestTask();
        TestCallbackTask testCallbackTask = new TestCallbackTask();

        for (int i = 0; i < 8; i++) {
            treadPoolExecutor.execute(testTask, testCallbackTask);
        }

        if (treadPoolExecutor.isEmpty()) {
            treadPoolExecutor.shutdown();
        }
    }

    public static void testWithoutPool() {
        TestTask testTask = new TestTask();
        TestCallbackTask testCallbackTask = new TestCallbackTask();

        CallbackTask callbackTask = new CallbackTask(testTask, testCallbackTask);

        for (int i = 0; i < 8; i++) {
            callbackTask.run();
        }
    }

    public static void main(String[] args) {
        long parallelTime = System.currentTimeMillis();
        Main.testWithPool();
        System.out.println("Tread pool time: " + (double)(System.currentTimeMillis() - parallelTime) + "ms");

        long poolTime = System.currentTimeMillis();
        Main.testWithoutPool();
        System.out.println("Tread time: " + (double)(System.currentTimeMillis() - poolTime) + "ms");
    }
}