package Lab2.zad3;


public class Main {
    public static void main(String[] args) {
        System.out.println("Min Value: " + Double.MIN_VALUE);
        System.out.println("Max Value: " + Double.MAX_VALUE);

        AddFloat addFloat = new AddFloat();

        AddFloatRead addFloatRead = new AddFloatRead(addFloat);
        new Thread(addFloatRead).start();

        AddFloatWrite addFloatWrite = new AddFloatWrite(addFloat);
        new Thread(addFloatWrite).start();

    }
}
