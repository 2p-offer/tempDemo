package treadSelf.queue;

/**
 * Created by 2P on 18-12-19.
 */
public class MyTask implements Comparable<MyTask> {
    private int id;

    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(MyTask o) {
        return this.id>o.id?1:(this.id<o.id?-1:0);
    }

    @Override
    public String toString() {
        return "MyTask{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
