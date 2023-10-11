package Lab2.zad2;

public class Main {
    public static void main(String[] args) {
        Add addClass = new Add();

        for (int i = 0; i < 2; i++) {
            AddThread addThr = new AddThread(addClass, 1000);
            new Thread(addThr).start();
        }
    }
}
