package Lab2.zad3.A;

public class AddFloatWrite implements Runnable {

    AddFloat addFloat;


    AddFloatWrite(AddFloat x) {
        this.addFloat = x;
    }

    public void run() {
        for (int i = 0; i < 100; i++) {
            addFloat.write();
        }
    }
}
