import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

public class PaxosClient {
    private String serverIP;
    private int serverPort;

    public PaxosClient(String ip, int port) {
        this.serverIP = ip;
        this.serverPort = port;
    }
    public void sendProposal(String proposer) {
        try (Socket socket = new Socket(serverIP, serverPort);
             DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             DataInputStream dis = new DataInputStream(socket.getInputStream())) {

            dos.writeUTF("Proposal:" + proposer);
            dos.flush();

            // Read the response from the server
            String response = dis.readUTF();
            System.out.println(proposer + " - Received response from server: " + response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendVote(String voter, String proposer) {
        try (Socket socket = new Socket(serverIP, serverPort);
             DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             DataInputStream dis = new DataInputStream(socket.getInputStream())) {

            // Sending a vote message to the server in the format "Vote:voter:proposer"
            dos.writeUTF("Vote:" + voter + ":" + proposer);
            dos.flush();

            // Read the response from the server
            String response = dis.readUTF();
            System.out.println(voter + " - Received response from server: " + response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
