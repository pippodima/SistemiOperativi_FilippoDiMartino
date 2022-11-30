import java.util.LinkedList;

public class MovingAvgQueue {
    int id;
    int k;
    LinkedList<Float> values = new LinkedList<>(); //ho usato una LinkedList<> perchè essendo soggetta a molte aggiunte di valori
                                                   //una lista collegata è più efficiente di un ArrayList (che crea tutte le volte una copia di tutto l'array)

    MovingAvgQueue(int id, int k){
        this.id=id;
        this.k=k;
        System.out.print("Creata Queue N." + id);
    }

    public synchronized void add(float value){
        values.add(value);
        notifyAll();
    }

    public synchronized float getAvg() throws InterruptedException {
        while (values.size()<k) wait();
        float avg=0;
        //calcolo della media
        for (int i = 0; i < k; i++) avg+=values.get(i);
        avg=avg/k;
        //rimozione del valore più vecchio
        values.remove(0);
        notifyAll();
        return avg;
    }
}
