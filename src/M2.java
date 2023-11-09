import java.net.Socket;

public class M2 extends M1 {
    public M2(Socket socket) {
        super(socket);
    }

    @Override
    public void run() {
        try {
            Thread.sleep((long) (Math.random() * 1000)); // 模拟在咖啡馆的情况
            super.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
