package Lab2.zad3.B;

public class ReadThread implements Runnable {

    StringCreator stringObject;

    public ReadThread(StringCreator x) {
        this.stringObject = x;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(this.stringObject.content);
        }
    }
}
