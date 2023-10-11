package Lab2.example;

public class ThreadNDC implements Runnable {
    NothingDoClass ndc;
    int n;

    ThreadNDC(NothingDoClass x, int n) {
        this.ndc = x;
        this.n = n;
    }


    public void run() {
        int local_vaule = 0;
        for (int i = 0; i < n; i++) {
            local_vaule = ndc.functionNothingDo();
            if (local_vaule != 0) {
                break;
            }
        }
        if (local_vaule != 0) {
            System.out.println(Thread.currentThread().getId() + " " + local_vaule);
        }
    }
}
