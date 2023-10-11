package Lab2.zad3;

public class NonAtomicFloatDemo {
    private static volatile float sharedFloat = 0.0f;

    public static void main(String[] args) {
        Thread writerThread = new Thread(() -> {
            while (true) {
                sharedFloat += 0.1f; // Zapisz wartość float
            }
        });

        Thread readerThread = new Thread(() -> {
            while (true) {
                float value = sharedFloat; // Odczytaj wartość float
                if (value % 1 != 0) {
                    System.out.println("Niezgodność znaleziona: " + value);
                }
            }
        });

        writerThread.start();
        readerThread.start();
    }
}
