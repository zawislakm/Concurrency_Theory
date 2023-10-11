package zad4;

public class Main {
    public static void main(String[] args) {

        ProcessClass stringObject = new ProcessClass();
        ProcPThread procP = new ProcPThread(stringObject);
        ProcKThread procK = new ProcKThread(stringObject);

        new Thread(procP).start();
        new Thread(procK).start();
    }
}
