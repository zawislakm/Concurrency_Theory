package Lab7;

import Lab7.parallelism.ConcurentBlockRunner;

class Application {

    public static void main(String args[]) {

        Executor e = new Executor(new ConcurentBlockRunner());
        e.start();
    }
}
