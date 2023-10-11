package zad2;

public class AddThread implements Runnable {

    Add addClass;
    int n;

    AddThread(Add x, int n) {
        this.addClass = x;
        this.n = n;
    }


    public void run() {
        int value = 0;
        for (int i = 0; i < this.n; i++) {
            value = this.addClass.increment();
            value = Add.incrementStatic();
        }
        System.out.println(Thread.currentThread().getId() + " " + value);

    }
}
