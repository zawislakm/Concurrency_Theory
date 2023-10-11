package Lab2.zad3.A;


public class Main {
    public static void main(String[] args) {
        System.out.println("Min Value: " + Double.MIN_VALUE);
        System.out.println("Max Value: " + Double.MAX_VALUE);

        AddFloat addFloat = new AddFloat();

        AddFloatReadThread addFloatRead = new AddFloatReadThread(addFloat);
        new Thread(addFloatRead).start();

        AddFloatWriteThread addFloatWrite = new AddFloatWriteThread(addFloat);
        new Thread(addFloatWrite).start();

    }
}
