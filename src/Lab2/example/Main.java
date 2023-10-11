package Lab2.example;

public class Main {
    public static void main(String[] args) {
        System.out.println("Start");
        NothingDoClass ndc = new NothingDoClass();
        for (int i = 0; i < 10000; i++) {
            ThreadNDC tndc = new ThreadNDC(ndc, 10000);
            new Thread(tndc).start();
        }
        System.out.println("Koniec");
    }
}