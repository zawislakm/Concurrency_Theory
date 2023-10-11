package Lab2.zad3.B;

public class Main {
    public static void main(String[] args) {

        StringCreator stringObject = new StringCreator();
        ReadThread readThread = new ReadThread(stringObject);
        WriteThread writeThread = new WriteThread(stringObject);

        new Thread(readThread).start();
        new Thread(writeThread).start();
    }
}
