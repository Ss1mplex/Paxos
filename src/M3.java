    import java.net.Socket;

    public class M3 extends M1 {
        public M3(Socket socket) {
            super(socket);
        }

        @Override
        public void run() {
            if (Math.random() < 0.5) { // 有50%的几率不回应
                System.out.println("M3 is not responding this time.");
                return;
            }
            super.run();
        }
    }
