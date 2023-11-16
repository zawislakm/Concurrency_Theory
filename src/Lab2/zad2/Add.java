package Lab2.zad2;

public class Add {
    static private int c = 0;

    synchronized public int increment() {
        synchronized (this) {
            c++;
            return c;
        }
    }

    synchronized static public int incrementStatic() {
        c++;
        return c;

    }
}
