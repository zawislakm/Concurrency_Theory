package Lab2.zad4;

public class ProcPThread implements Runnable {

    ProcessClass proc;

    public ProcPThread(ProcessClass x) {
        this.proc = x;
    }

    @Override
    public void run() {
        while (true) {
            this.proc.procP();
        }
    }
}
