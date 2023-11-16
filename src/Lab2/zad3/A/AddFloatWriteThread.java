package Lab2.zad3.A;

public class AddFloatWriteThread implements Runnable {

    AddFloat addFloat;


    AddFloatWriteThread(AddFloat x) {
        this.addFloat = x;
    }

    public void run() {
        while (true) {
            addFloat.write();
        }
    }
}
