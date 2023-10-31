package Lab4.Var6;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

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

class Philosopher extends Thread {
    int id;
    final Fork left_fork;
    final Fork right_fork;
    LoginSemaphore sem;

    public Philosopher(int x, LoginSemaphore sem) {
        this.sem = sem;
        this.id = x;
        this.left_fork = Buffer.buf_.get(x);
        this.right_fork = Buffer.buf_.get((x + 1) % Buffer.buf_.size());
    }

    @Override
    public void run() {
        while (true) {

            if (sem.tryLogin()) {
                synchronized (this.left_fork) {
                    System.out.println(this.id + " picked up left fork");
                    synchronized (this.right_fork) {
                        System.out.println(this.id + " picked up right fork");
                    }
                }
                sem.logout();
            }

        }
    }
}

public class Simulation {

    public static void main(String[] args) {
        int philo_amount = 5;
        UniqID uniqID = new UniqID();
        Buffer buffer = new Buffer(philo_amount);
        LoginSemaphore loginSemaphore = new LoginSemaphore(philo_amount - 1);

        for (int i = 0; i < philo_amount; i++) {
            Philosopher philosopher = new Philosopher(uniqID.newID(), loginSemaphore);
            new Thread(philosopher).start();
        }

    }
}
