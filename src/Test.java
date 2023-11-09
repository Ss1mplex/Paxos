import java.util.concurrent.CountDownLatch;
import java.util.Random;

public class Test {

    public static void main(String[] args) {
        int numberOfClients = 9;
        CountDownLatch startSignal = new CountDownLatch(1);
        Random random = new Random();

        for (int i = 0; i < numberOfClients; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    // Wait for the start signal
                    startSignal.await();

                    // Assuming you have a client name corresponding to each number
                    String clientName = "M" + (finalI + 1);
                    PaxosClient client = new PaxosClient("127.0.0.1", 12345);

                    // If it's a voter (M4-M9), vote for a random proposer (M1-M3)
                    if (finalI >= 3) {
                        String proposer = "M" + (random.nextInt(3) + 1);
                        client.sendVote(clientName, proposer);
                    } else {
                        // If it's a proposer (M1-M3), send a proposal
                        client.sendProposal(clientName);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        // Once all threads are ready to start, count down the latch
        startSignal.countDown();
    }
}
