package app;

import app.Interfaces.Callback;

import java.util.LinkedList;

public class TreadPoolExecutor {
    private final int poolSize;
    private final WorkerThread[] workers;
    private final LinkedList<Runnable> queue;

    public TreadPoolExecutor(int poolSize) {
        this.poolSize = poolSize;
        queue = new LinkedList<Runnable>();
        workers = new WorkerThread[poolSize];

        for (int i = 0; i < poolSize; i++) {
            workers[i] = new WorkerThread();
            workers[i].start();
        }
    }

    public void execute(Runnable task, Callback callback) {
        synchronized (this.queue) {
            CallbackTask callbackTask = new CallbackTask(task, callback);
            this.queue.add(callbackTask);
            this.queue.notify();
        }
    }

    private class WorkerThread extends Thread {
        private boolean isRunning = false;

        public void run() {
            Runnable task;

            while (true) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            System.out.println("An error occurred while queue is waiting: " + e.getMessage());
                        }
                    }
                    isRunning = true;
                    task = (Runnable) queue.poll();
                }

                try {
                    task.run();
                } catch (RuntimeException e) {
                    System.out.println("Thread pool is interrupted due to an issue: " + e.getMessage());
                }

                synchronized (queue) {
                    isRunning = false;
                    queue.notify();
                }
            }
        }
    }

    public boolean isWorkerWorking() {
        for (WorkerThread worker : workers) {
            if (worker.isRunning == true) {
                return true;
            }
        }
        return false;
    }


    public void waitWorkers() {
        while (true) {
            synchronized (queue) {
                while (!queue.isEmpty()) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        System.out.println("An error occurred while queue is waiting: " + e.getMessage());
                    }
                }
            }

            if (!this.isWorkerWorking()) {
                return;
            }
        }
    }

    public void shutdown() {
        System.out.println("Shutting down thread pool");
        for (int i = 0; i < poolSize; i++) {
            workers[i] = null;
        }
    }
}
