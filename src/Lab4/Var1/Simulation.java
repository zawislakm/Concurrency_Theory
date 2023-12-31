package Lab4.Var1;

import java.util.ArrayList;


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

class Philosopher implements Runnable {
    int id;
    final Fork left_fork;
    final Fork right_fork;

    public Philosopher(int x) {
        this.id = x;
        this.left_fork = Buffer.buf_.get(x);
        this.right_fork = Buffer.buf_.get((x + 1) % Buffer.buf_.size());
    }

    @Override
    public void run() {
        while (true) {
            synchronized (this.left_fork) {
                System.out.println(this.id + " picked up left fork");
                synchronized (this.right_fork) {
                    System.out.println(this.id + " picked up right fork");
                }
            }
        }

    }
}


public class Simulation {
    public static void main(String[] args) throws InterruptedException {
        int philo_amount = 5;
        new Buffer(philo_amount);


        for (int i = 0; i < philo_amount; i++) {
            Philosopher philosopher = new Philosopher(UniqID.newID());
            new Thread(philosopher).start();
//            Thread.sleep(2);
        }


    }
}
