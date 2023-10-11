package zad1;

public class Main {
    public static void main(String[] args) {

        for (int i = 0; i < 3; i++) {
            Rewolwerowiec rel = new Rewolwerowiec(5);
            new Thread(rel).start();
        }

    }
}


