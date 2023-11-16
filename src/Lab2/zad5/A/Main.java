package Lab2.zad5.A;


public class Main {
    public static void main(String[] args) {
        Election election = new Election();
        for (int i = 0; i < 5; i++) {
            ElectionThread electionThread = new ElectionThread(election, 20);
            new Thread(electionThread).start();
        }


//        System.out.println("Summary ended: A: " + election.voteA + " B: " + election.voteB + " C: " + election.voteC);
    }
}
