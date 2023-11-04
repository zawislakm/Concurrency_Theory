package Lab4.Var2;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.IOException;

class UniqID {
    private static int numerId = 0;

    synchronized public static int newID() {
        return numerId++;
    }
}

class Fork {
    public ReentrantLock lock = new ReentrantLock();

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

    ArrayList<Long> times = new ArrayList<>();

    public void pickUpFork() {
        times.add(System.nanoTime() - start);
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

    public Philosopher(int x) {
        this.id = x;
        this.left_fork = Buffer.buf_.get(x);
        this.right_fork = Buffer.buf_.get((x + 1) % Buffer.buf_.size());
        this.stats = new Statistic();
    }


    @Override
    public void run() {
        this.stats.putDownFork();
        while (true) {
            boolean b1 = left_fork.lock.tryLock();
            if (!b1) {
                continue;
            }
            boolean b2 = right_fork.lock.tryLock();
            if (!b2) {
                left_fork.lock.unlock();
                continue;
            }

            System.out.println(this.id + " philosopher lock both forks");
            this.stats.pickUpFork();
            left_fork.lock.unlock();
            right_fork.lock.unlock();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.stats.putDownFork();
        }
    }

    public List<Long> getStats() {
        return this.stats.getStats();
    }
}


public class Simulation {

    public static void main(String[] args) throws InterruptedException, IOException {
        int philo_amount = 5;
        new Buffer(philo_amount);

        ExecutorService executor = Executors.newFixedThreadPool(philo_amount);
        Philosopher[] philosophers = new Philosopher[philo_amount];
        for (int i = 0; i < philo_amount; i++) {
            Philosopher philosopher = new Philosopher(UniqID.newID());
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
                csvWrite.append('\n');
                csvWrite.flush();
            }


        }

    }
}
