package server;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServerApp {

    public static void main(String[] args) {


        Socket socket;

        try (ServerSocket serverSocket = new ServerSocket(8181)) {
            System.out.println("\"Сервер запущен\"");
            socket = serverSocket.accept();
            System.out.println("Клиент подключился");

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));


            while (true) {
                String str = dis.readUTF();

                System.out.println(str);
                if (str.equals("/end")) {
                    break;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            while (true) {
                                String name = bf.readLine();
                                dos.writeUTF("Serv: " + name);
                                if (name.equalsIgnoreCase("/end")) {
                                    break;
                                }

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }







                } catch (IOException ignored) {
        }
    }

    private static void closeConnection(Socket s, DataInputStream dis, DataOutputStream dos) {

        try {
            dis.close();
        } catch (IOException ignored) {
        }

        try {
            dos.flush();
        } catch (IOException ignored) {
        }

        try {
            dos.close();
        } catch (IOException ignored) {
        }

        try {
            s.close();
        } catch (IOException ignored) {
        }
    }
}
