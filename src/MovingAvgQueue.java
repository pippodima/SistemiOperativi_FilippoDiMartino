import java.util.LinkedList;

public class MovingAvgQueue {
    int id;
    int k;
    LinkedList<Float> values = new LinkedList<>();

    MovingAvgQueue(int id, int k){
        this.id=id;
        this.k=k;
        System.out.println("Creata queue con id: " + id);
    }

    public synchronized void add(float value){
        values.add(value);
        notifyAll();
    }

    public synchronized float getAvg() throws InterruptedException {
        while (values.size()<k) wait();
        float avg=0;
        for (int i = 0; i < k; i++) avg+=values.get(i);
        avg=avg/k;
        values.remove(0);
        notifyAll();
        return avg;
    }
}
