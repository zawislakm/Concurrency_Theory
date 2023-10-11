package Lab2.example;

public class WatekNNRK implements Runnable {
    NicNieRobiacaKlasa nnrk;
    int n;

    WatekNNRK(NicNieRobiacaKlasa nnrk, int n) {
        this.nnrk = nnrk;
        this.n = n;
    }


    public void run() {
        int zmiena_lokalna = 0;
        for (int i = 0; i < n; i++) {
            zmiena_lokalna = nnrk.funkcjaNicNieRobiaca();
            if (zmiena_lokalna != 0) {
                break;
            }
        }
        if (zmiena_lokalna != 0) {
            System.out.println(Thread.currentThread().getId() + " " + zmiena_lokalna);
        }
    }
}
