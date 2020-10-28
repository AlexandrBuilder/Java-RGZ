package app.tasks;

public class TestTask implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("An error occurred while waiting for the timer: " + e.getMessage());
        }
        System.out.println("task");
    }
}