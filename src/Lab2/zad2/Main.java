package zad2;

import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        Add addClass = new Add();

        for (int i = 0; i < 2; i++) {
            AddThread addThr = new AddThread(addClass, 1000);
            new Thread(addThr).start();
        }
    }
}
