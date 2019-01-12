package nioSelf;

/**
 * Created by 2P on 19-1-11.
 */
public class NioTestClientB {
    public static void main(String[] args) throws Exception{
        NioClientA nioClientA = new NioClientA();
        //连接server
        nioClientA.initClient("localhost",7999);
        nioClientA.sendAndRecv("I'm BBBBB try to send msg");
    }
}
