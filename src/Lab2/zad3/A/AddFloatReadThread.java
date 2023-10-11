package Lab2.zad3.A;

public class AddFloatReadThread implements Runnable {

    AddFloat addFloat;


    AddFloatReadThread(AddFloat x) {
        this.addFloat = x;
    }

    public void run() {
        while (true){
            System.out.println(addFloat.read());
        }
    }
}
