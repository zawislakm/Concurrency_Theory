package Lab2.example;

public class NothingDoClass {
    public int val = 0;

    synchronized public int functionNothingDo() {
        synchronized (this) {
            val++;
            val--;
            // wait or notify
            // wait gives key to someone waiting
            // notify wakeup
            return val;
        }
    }
}
