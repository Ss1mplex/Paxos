import java.io.*;
import java.net.Socket;

public class M1 extends Thread {
    private Socket socket;

    public M1(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            // waiting
            String proposal = dis.readUTF();
            System.out.println("M1 received proposal: " + proposal);

            // immediate responses
            dos.writeUTF("M1 Accepted: " + proposal);

            dis.close();
            dos.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
