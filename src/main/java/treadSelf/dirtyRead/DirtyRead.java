package treadSelf.dirtyRead;

/**
 * Created by 2P on 18-12-11.
 */
public class   DirtyRead {
    private String userName = "userName";
    private String passWord = "passWord";

    //加上static就可以保证同步
//    private String userName="userName";
//    private String passWord="passWord";
    public synchronized void setVal(String userName, String passWord) {
        this.userName = userName;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.passWord = passWord;
        System.out.println("setVale final user:" + userName + "pas:" + passWord);
    }

    public void getVal() {
        System.out.println("user:" + userName + "   pass:" + passWord);
    }

    public static void main(String[] args) throws InterruptedException {
        final DirtyRead dr = new DirtyRead();
        Thread thread = new Thread(() -> {
            dr.setVal("wangyayn", "111");
        });
        thread.start();
        Thread.sleep(1000);
        dr.getVal();

    }

}
