import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class PaxosServer {
    private static final AtomicInteger totalVotes = new AtomicInteger(0);
    private static final int SERVER_PORT = 12345;
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);
    private static final ConcurrentHashMap<String, AtomicInteger> proposals = new ConcurrentHashMap<>();

    private static final AtomicInteger voteCountM1 = new AtomicInteger(0);
    private static final AtomicInteger voteCountM2 = new AtomicInteger(0);
    private static final AtomicInteger voteCountM3 = new AtomicInteger(0);

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
        System.out.println("Paxos Voting Server running on port " + SERVER_PORT);

        try {
            while (true) {
                final Socket clientSocket = serverSocket.accept();
                executor.submit(() -> handleClient(clientSocket));
            }
        } finally {
            serverSocket.close();
        }
    }

    private static void handleClient(Socket clientSocket) {

        try (DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
             DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream())) {
            if (totalVotes.incrementAndGet() == 6) { // Total 6 voters from M4 to M9
                String elected = getElected();
                System.out.println("Elected: " + elected); // Output to the server console.
                dos.writeUTF("Elected: " + elected); // Send elected result to the client.
            }
            String input = dis.readUTF();

            System.out.println("Received from client: " + input);

            if (input.startsWith("Proposal:")) {
                String proposal = input.split(":")[1].trim();
                // Only M1, M2, M3 are valid proposers
                if (!proposal.equals("M1") && !proposal.equals("M2") && !proposal.equals("M3")) {
                    dos.writeUTF("Error: Invalid proposer");
                } else {
                    dos.writeUTF("Proposal received: " + proposal);
                }
            } else if (input.startsWith("Vote:")) {
                String[] parts = input.split(":");
                String voter = parts[1].trim();
                String proposer = parts[2].trim();

                // Only accept votes for M1, M2, or M3
                if (!proposer.equals("M1") && !proposer.equals("M2") && !proposer.equals("M3")) {
                    dos.writeUTF("Error: Invalid vote for non-proposer");
                } else {
                    AtomicInteger count = getVoteCountForProposer(proposer);
                    if (count != null) {
                        count.incrementAndGet();
                        dos.writeUTF("Vote counted for: " + proposer);
                    }
                }

                // Announce the result if voting is completed for all proposers
                if (voteCountM1.get() + voteCountM2.get() + voteCountM3.get() == 6) { // Total 6 voters from M4 to M9
                    String elected = getElected();
                    dos.writeUTF("Elected: " + elected);
                }
            } else {
                dos.writeUTF("Error: Unrecognized request");
            }
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String getElected() {

        if (voteCountM1.get() >= voteCountM2.get() && voteCountM1.get() >= voteCountM3.get()) {
            return "M1";
        } else if (voteCountM2.get() > voteCountM1.get() && voteCountM2.get() >= voteCountM3.get()) {
            return "M2";
        } else {
            return "M3";
        }
    }

    private static AtomicInteger getVoteCountForProposer(String proposer) {
        switch (proposer) {
            case "M1":
                return voteCountM1;
            case "M2":
                return voteCountM2;
            case "M3":
                return voteCountM3;
            default:
                return null;
        }
    }
}
