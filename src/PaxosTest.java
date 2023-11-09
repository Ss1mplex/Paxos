import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class PaxosTest {

    private static final int CLIENT_COUNT = 9;
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) throws InterruptedException {
        testWithDelays();
        testWithNoResponse();
        comprehensiveTest();
    }

    private static void testWithDelays() throws InterruptedException {
        System.out.println("Running test with delays...");

        CountDownLatch latch = new CountDownLatch(CLIENT_COUNT);
        for (int i = 1; i <= CLIENT_COUNT; i++) {
            final String clientName = "M" + i;
            PaxosClient client = new PaxosClient(SERVER_IP, SERVER_PORT);

            Thread clientThread = new Thread(() -> {
                try {
                    if (clientName.equals("M2") || (clientName.compareTo("M4") >= 0 && clientName.compareTo("M9") <= 0)) {
                        // Introduce a random delay for M2 and M4 to M9
                        Thread.sleep((long) (Math.random() * 1000));
                    }

                    if (clientName.compareTo("M3") <= 0) {
                        // M1 to M3 are proposers
                        client.sendProposal(clientName);
                    } else {
                        // M4 to M9 are voters
                        String proposer = "M" + ((int) (Math.random() * 3) + 1);
                        client.sendVote(clientName, proposer);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            });

            clientThread.start();
        }

        // Wait for all threads to finish with a timeout of 10 seconds
        boolean completed = latch.await(10, TimeUnit.SECONDS);
        System.out.println("Test with delays completed: " + completed);
    }

    private static void testWithNoResponse() throws InterruptedException {
        System.out.println("Running test with no response...");

        CountDownLatch latch = new CountDownLatch(CLIENT_COUNT);
        for (int i = 1; i <= CLIENT_COUNT; i++) {
            final String clientName = "M" + i;
            PaxosClient client = new PaxosClient(SERVER_IP, SERVER_PORT);

            Thread clientThread = new Thread(() -> {
                try {
                    if (clientName.equals("M3")) {
                        // M3 has a 50% chance to not respond
                        if (Math.random() < 0.5) {
                            System.out.println(clientName + " is not responding.");
                            return;
                        }
                    }

                    if (clientName.compareTo("M3") <= 0) {
                        // M1 to M3 are proposers
                        client.sendProposal(clientName);
                    } else {
                        // M4 to M9 are voters
                        String proposer = "M" + ((int) (Math.random() * 3) + 1);
                        client.sendVote(clientName, proposer);
                    }
                } finally {
                    latch.countDown();
                }
            });

            clientThread.start();
        }

        // Wait for all threads to finish with a timeout of 10 seconds
        boolean completed = latch.await(10, TimeUnit.SECONDS);
        System.out.println("Test with no response completed: " + completed);
    }

    private static void comprehensiveTest() throws InterruptedException {
        System.out.println("Running comprehensive test with delays and no response...");

        CountDownLatch latch = new CountDownLatch(CLIENT_COUNT);
        for (int i = 1; i <= CLIENT_COUNT; i++) {
            final String clientName = "M" + i;
            PaxosClient client = new PaxosClient(SERVER_IP, SERVER_PORT);

            Thread clientThread = new Thread(() -> {
                try {
                    // Introduce random delays for all clients
                    Thread.sleep((long) (Math.random() * 1000));

                    if (clientName.equals("M3") && Math.random() < 0.5) {
                        // M3 has a 50% chance to not respond
                        System.out.println(clientName + " is not responding.");
                        return;
                    }

                    if (clientName.compareTo("M3") <= 0) {
                        // M1 to M3 are proposers
                        client.sendProposal(clientName);
                    } else {
                        // M4 to M9 are voters
                        String proposer = "M" + ((int) (Math.random() * 3) + 1);
                        client.sendVote(clientName, proposer);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            });

            clientThread.start();
        }

        // Wait for all threads to finish with a timeout of 10 seconds
        boolean completed = latch.await(10, TimeUnit.SECONDS);
        System.out.println("Comprehensive test completed: " + completed);
    }
}
