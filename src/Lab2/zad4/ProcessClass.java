package zad4;

public class ProcessClass {
    public boolean tp = false;
    public boolean tk = false;


    public void procP() {
        while (true) {
            System.out.println("P Local section");
            this.tp = true;
            while (tk == false) {
                System.out.println("procP is waiting");
                try {
                    Thread.sleep(10000); // Symulacja opóźnienia
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("P Critical section");
            this.tp = false;
        }
    }

    public void procK() {
        while (true) {
            System.out.println("K Local section");
            this.tk = true;
            while (tp == false) {
                System.out.println("procK is waiting");
                try {
                    Thread.sleep(10000); // Symulacja opóźnienia
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("K Critical section");
            this.tk = false;
        }
    }

}
