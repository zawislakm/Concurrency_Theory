package Lab8;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GaussMatrix {
    private int N;
    private ArrayList<ArrayList<Double>> matrix;
    private ArrayList<Double> m;
    private ArrayList<ArrayList<Double>> n;


    public void actionA(int i, int k) {
        synchronized (this) {
            m.set(k, matrix.get(k).get(i) / matrix.get(i).get(i));
        }
    }

    public void actionB(int i, int j, int k) {
        synchronized (this) {
            n.get(k).set(j, matrix.get(i).get(j) * m.get(k));
        }
    }

    public void actionC(int j, int k) {
        synchronized (this) {
            matrix.get(k).set(j, matrix.get(k).get(j) - n.get(k).get(j));
        }
    }


    public void concurrentGauss() throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < N - 1; i++) {

            for (int j = i + 1; j < N; j++) {
                int finalI = i;
                int finalJ = j;
                Thread threadA = new Thread(() -> actionA(finalI, finalJ));
                threads.add(threadA);
            }
            startAndJoinThreads(threads);


            for (int k = i + 1; k < N; k++) {
                for (int j = i; j <= N; j++) {
                    int finalI = i;
                    int finalJ = j;
                    int finalK = k;
                    Thread threadB = new Thread(() -> actionB(finalI, finalJ, finalK));
                    threads.add(threadB);
                }
            }
            startAndJoinThreads(threads);


            for (int k = i + 1; k < N; k++) {
                for (int j = i; j <= N; j++) {
                    int finalJ = j;
                    int finalK = k;
                    Thread threadC = new Thread(() -> actionC(finalJ, finalK));
                    threads.add(threadC);
                }
            }
            startAndJoinThreads(threads);
        }
    }


    private void startAndJoinThreads(List<Thread> threads) throws InterruptedException {
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }
        threads.clear();
    }

    public void solveMatrix() {
        for (int i = N - 1; i >= 0; i--) {
            for (int j = N - 1; j > i; j--) {
                matrix.get(i).set(N, matrix.get(i).get(N) - matrix.get(i).get(j) * matrix.get(j).get(N));
                matrix.get(i).set(j, 0.0);
            }
            matrix.get(i).set(N, matrix.get(i).get(N) / matrix.get(i).get(i));
            matrix.get(i).set(i, 1.0);

        }
    }

    public void start(int N, List<List<Double>> A, List<Double> Y) {
        this.N = N;
        matrix = new ArrayList<>();
        m = new ArrayList<>(Collections.nCopies(N, 0.0));
        n = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            matrix.add(new ArrayList<>());
            n.add(new ArrayList<>(Collections.nCopies(N + 1, 0.0)));
            for (int j = 0; j < N; j++) {
                matrix.get(i).add(A.get(i).get(j));
            }
            matrix.get(i).add(Y.get(i));
        }


    }


    public List<Double> readLineNumbers(String line, int N) {
        List<Double> result = new ArrayList<>();

        String[] nums = line.split(" ");
        for (int i = 0; i < N; i++) {
            result.add(Double.parseDouble(nums[i]));
        }

        return result;
    }

    public String getSize() {
        return String.valueOf(this.N);
    }

    public void fromFile(String filename) throws IOException {
        BufferedReader file = new BufferedReader(new FileReader(filename));

        String line = file.readLine();
        int fileN = Integer.parseInt(line);

        List<List<Double>> A = new ArrayList<>();
        for (int i = 0; i < fileN; i++) {
            line = file.readLine();
            List<Double> row = readLineNumbers(line, fileN);
            A.add(row);
        }

        line = file.readLine();
        List<Double> Y = readLineNumbers(line, fileN);

        file.close();

        this.start(fileN, A, Y);
    }

    public void toFile(String filename) throws IOException {
        FileWriter file = new FileWriter(filename);

        file.write(this.toString());

        file.close();
    }

    @Override
    public String toString() {
        StringBuilder stringMatrix = new StringBuilder();
        stringMatrix.append(N).append("\n");
        stringMatrix.append(String.format("%.1f", matrix.get(0).get(0)));
        for (int i = 1; i < N; i++) {
            stringMatrix.append(" ");
            stringMatrix.append(String.format("%.1f", matrix.get(0).get(i)));
        }
        stringMatrix.append("\n");

        for (int i = 1; i < N; i++) {
            for (int j = 0; j < N; j++) {
                stringMatrix.append(String.format("%.1f", matrix.get(i).get(j)));
                if (j < N - 1) {
                    stringMatrix.append(" ");
                } else {
                    stringMatrix.append("\n");
                }
            }
        }

        for (int i = 0; i < N; i++) {
            stringMatrix.append(String.format("%.1f", matrix.get(i).get(N)));
            if (i < N - 1) {
                stringMatrix.append(" ");
            }
        }

        return stringMatrix.toString();
    }
}

