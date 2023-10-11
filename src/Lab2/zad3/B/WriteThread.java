package Lab2.zad3.B;

public class WriteThread implements Runnable {

    StringCreator stringObject;

    public WriteThread(StringCreator x) {
        this.stringObject = x;
    }

    @Override
    public void run() {
        while (true) {
            this.stringObject.extendString();
        }
    }
}