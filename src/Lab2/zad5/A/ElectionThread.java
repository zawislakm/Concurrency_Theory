package Lab2.zad5.A;

import java.util.Random;

public class ElectionThread implements Runnable {

    Election election;
    int districSize;

    ElectionThread(Election x, int n) {
        this.election = x;
        this.districSize = n;
    }

    @Override
    public void run() {

        int voteA = 0;
        int voteB = 0;
        int voteC = 0;

        for (int i = 0; i < this.districSize; i++) {

            Random random = new Random();
            int vote = random.nextInt(3);
//            System.out.println(vote);
            switch (vote) {
                case 0:
                    voteA++;
                    break;
                case 1:
                    voteB++;
                    break;
                case 2:
                    voteC++;
                    break;
                default:
                    break;
            }

            this.election.update(vote);
        }

        System.out.println(Thread.currentThread().getId() + "Counting ended: A: " + voteA + " B: " + voteB + " C: " + voteC);
        System.out.println(Thread.currentThread().getId() + "Summary ended: A: " + election.voteA + " B: " + election.voteB + " C: " + election.voteC);
    }
}
