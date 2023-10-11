package Lab2.zad4;

public class ProcKThread implements Runnable {
    ProcessClass proc;

    public ProcKThread(ProcessClass x) {
        this.proc = x;
    }

    @Override
    public void run() {
        while (true) {
            this.proc.procK();
        }
    }
}
