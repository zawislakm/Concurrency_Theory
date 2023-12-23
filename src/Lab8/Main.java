package Lab8;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        String python_script = "src/Lab8/theory.py";
        String filePath;

        try {
            filePath = args[0];
        } catch (Exception e) {
            filePath = "src/Lab8/input1.txt";
        }

        GaussMatrix GM = new GaussMatrix();

        try {
            GM.fromFile(filePath);
            System.out.println(GM.toString());

            Process process = new ProcessBuilder("python3", python_script, GM.getSize()).inheritIO().start();
            int exitCode = process.waitFor();

            GM.concurrentGauss();

            GM.solveMatrix();
            System.out.println(GM.toString());
            GM.toFile("src/Lab8/output.txt");
        } catch (IOException e) {
            System.out.println("Something went wrong!!");
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
