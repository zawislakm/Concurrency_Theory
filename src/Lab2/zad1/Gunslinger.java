package Lab2.zad1;

public class Gunslinger implements Runnable {

    int count;
    static boolean shotFired = false;

    Gunslinger(int count) {
        this.count = count;
    }

    public void run() {

        for (int i = count; i > 0; i--) {
            if (shotFired) {
                break;
            }
            System.out.println(i);
        }
        if (!shotFired) {
            shotFired = true;
            System.out.println("Pif paf");

        }
    }
}
