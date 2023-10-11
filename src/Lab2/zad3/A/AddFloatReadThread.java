package Lab2.zad3.A;

public class AddFloatRead implements Runnable {

    AddFloat addFloat;


    AddFloatRead(AddFloat x) {
        this.addFloat = x;
    }

    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(addFloat.read());
        }
    }
}
