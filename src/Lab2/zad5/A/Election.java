package Lab2.A;

public class Election {

    public int voteA = 0;
    public int voteB = 0;
    public int voteC = 0;

    public void update(int x) {
        switch (x) {
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
    }
}
