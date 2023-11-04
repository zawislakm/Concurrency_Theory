package Lab4.Var6;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.io.FileWriter;
import java.io.IOException;

class LoginSemaphore {
    private Semaphore semaphore;

    public LoginSemaphore(int x) {
        this.semaphore = new Semaphore(x);
    }

    boolean tryLogin() {
        return semaphore.tryAcquire();
    }

    void logout() {
        semaphore.release();
    }

}

class UniqID {
    private static int numerId = 0;

    synchronized public static int newID() {
        return numerId++;
    }
}

class Fork {
}

class Buffer {

    public static ArrayList<Fork> buf_ = new ArrayList<>();

    public Buffer(int x) {
        for (int i = 0; i < x; i++) {
            buf_.add(new Fork());

        }
    }

}


class Statistic {

    long start = System.nanoTime();
    int out_eat = 0;
    ArrayList<Long> times = new ArrayList<>();

    public void pickUpFork() {
        times.add(System.nanoTime() - start);
    }
    public void eatingOut() {
        this.out_eat += 1;
    }
    public void putDownFork() {
        start = System.nanoTime();
    }

    public List<Long> getStats() {
        return this.times;
    }
}


class Philosopher extends Thread {
    int id;
    Statistic stats;
    final Fork left_fork;
    final Fork right_fork;
    LoginSemaphore sem;

    public Philosopher(int x, LoginSemaphore sem) {
        this.sem = sem;
        this.id = x;
        this.left_fork = Buffer.buf_.get(x);
        this.right_fork = Buffer.buf_.get((x + 1) % Buffer.buf_.size());
        this.stats = new Statistic();
    }

    @Override
    public void run() {

        this.stats.putDownFork();
        while (true) {

            if (sem.tryLogin()) {
                synchronized (this.left_fork) {
                    System.out.println(this.id + " picked up left fork");
                    synchronized (this.right_fork) {
                        System.out.println(this.id + " picked up right fork");
                        this.stats.pickUpFork();
                    }
                }
                sem.logout();

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                this.stats.putDownFork();
            } else {
                synchronized (this.right_fork) {
                    System.out.println(this.id + " picked up right fork, out sem");
                    synchronized (this.left_fork) {
                        this.stats.pickUpFork();
                        System.out.println(this.id + " picked up left fork, out sem");
                        this.stats.eatingOut();
                    }
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                this.stats.putDownFork();
            }


        }
    }


    public List<Long> getStats() {
        return this.stats.getStats();
    }

    public int getOutEat() {
        return this.stats.out_eat;
    }
}

public class Simulation {

    public static void main(String[] args) throws InterruptedException, IOException {
        int philo_amount = 5;
        new Buffer(philo_amount);
        LoginSemaphore loginSemaphore = new LoginSemaphore(philo_amount - 1);
        ExecutorService executor = Executors.newFixedThreadPool(philo_amount);
        Philosopher[] philosophers = new Philosopher[philo_amount];
        for (int i = 0; i < philo_amount; i++) {
            Philosopher philosopher = new Philosopher(UniqID.newID(), loginSemaphore);
            philosophers[i] = philosopher;
            executor.submit(philosopher);
        }

        Thread.sleep(5000);


        executor.shutdownNow();
        try (FileWriter csvWrite = new FileWriter("data.csv")) {
            for (Philosopher p : philosophers) {
                List<Long> statsLong = p.getStats();
                for (Long stat : statsLong) {
                    csvWrite.append(stat + ";");
                }
                csvWrite.append("\n");
                csvWrite.flush();
            }
        }

        try (FileWriter csvWrite = new FileWriter("outeat.csv")) {
            for (Philosopher p : philosophers) {
                int outEat = p.getOutEat();
                csvWrite.append(outEat + ";");

                csvWrite.append("\n");
                csvWrite.flush();
            }
        }

    }
}
