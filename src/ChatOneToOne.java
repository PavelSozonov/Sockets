import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by pavel on 15.06.17.
 */
public class ChatOneToOne {

    private int port;
    private ServerSocket serverSocket;
    private Socket socket;
    private InputStream inputStream;
    private DataInputStream dataInputStream;

    public ChatOneToOne(int port) {
        this.port = port;
        try {
            serverSocket = new ServerSocket(port);
            socket = serverSocket.accept();
            inputStream = socket.getInputStream();
            dataInputStream = new DataInputStream(inputStream);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        dataInputStream.close();
        inputStream.close();
        socket.close();
        serverSocket.close();
    }

    public void start() {
        try (Scanner scanner = new Scanner(System.in)) {
            loop:
            while (true) {
                System.out.print("Command (/h - help): ");
                String command = scanner.nextLine();
                switch (command) {
                    case "/help":
                        showHelp();
                        break;
                    case "/connect":
                        newChat();
                        break;
                    case "/exit":
                        break loop;
                    default:
                        break;
                }
            }
        }
    }

    private void newChat() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Address: ");
            String address = scanner.nextLine();
            System.out.print("Port: ");
            int port = scanner.nextInt();
        }
    }

    private void showHelp() {
        System.out.println("/exit - exit\n/help - help\n/chat - new chat");
    }
}
