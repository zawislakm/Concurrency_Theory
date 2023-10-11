package Lab2.example;

public class Main {
    public static void main(String[] args) {
        System.out.println("Start");
        NicNieRobiacaKlasa nnrk = new NicNieRobiacaKlasa();
        for (int i = 0; i < 10000; i++) {
            WatekNNRK wtk = new WatekNNRK(nnrk, 10000);
            new Thread(wtk).start();
        }
        System.out.println("Koniec");
    }
}