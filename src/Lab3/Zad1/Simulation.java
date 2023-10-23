package Lab3.Zad1;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

class UniqID {
    private static int numerId = 0;

    synchronized public static int newID() {
        return numerId++;
    }
}

class Producer extends Thread {
    private Buffer _buf;
    UniqID uniqID = new UniqID();
    public Producer(Buffer x) {
        this._buf = x;
    }

    public void run() {
        for (int i = 0; i < 100; ++i) {
            try {
                _buf.put(UniqID.newID());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

    }

}


class Consumer extends Thread {
    private Buffer _buf;

    public Consumer(Buffer x) {
        this._buf = x;
    }

    public void run() {
        for (int i = 0; i < 100; ++i) {
            try {
                System.out.println(Thread.currentThread().getId() + " Consumer get number " + _buf.get());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

    }

}


class Buffer {
    ArrayList<Integer> buffer = new ArrayList<Integer>();
    int limit_buffer_size;


    public Buffer(int n) {
        this.limit_buffer_size = n;
    }

    public void put(int i) throws InterruptedException {
        synchronized (this) {
            while (this.buffer.size() >= limit_buffer_size) {
                wait();
            }
            System.out.println(Thread.currentThread().getId() + " Producer put number " + i);
            this.buffer.add(i);
            notifyAll();
        }

    }


    public int get() throws InterruptedException {
        int i;
        synchronized (this) {
            while (this.buffer.isEmpty()) {
                wait();
            }
            i = this.buffer.get(0);
            this.buffer.remove(0);
            notifyAll();
        }
        return i;
    }

}


public class Simulation {
    public static void main(String[] args) throws InterruptedException {
        // task variables
        int buffer_size =4;
        int consumer_amount = 5;
        int producer_amount = 10;

        Buffer buffer = new Buffer(buffer_size);

        for (int i = 0; i < consumer_amount; i++) {
            Consumer consumer = new Consumer(buffer);
            new Thread(consumer).start();
        }

        for (int i = 0; i < producer_amount; i++) {
            Producer producer = new Producer(buffer);
            new Thread(producer).start();
        }

    }
}