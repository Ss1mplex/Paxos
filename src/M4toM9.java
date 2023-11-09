import java.net.Socket;

public class M4toM9 extends M1 {
    public M4toM9(Socket socket) {
        super(socket);
    }

    @Override
    public void run() {
        try {
            Thread.sleep((long) (Math.random() * 500)); // 反应时间变化
            super.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
