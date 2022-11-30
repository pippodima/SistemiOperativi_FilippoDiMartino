import java.util.Arrays;

public class ProcessorThread extends Thread {
    int id;
    int count = 0;
    ValueManager manager;
    float[] myavg;
    int nSensori;

    ProcessorThread(int id, int nSensori, ValueManager manager) {
        this.id = id;
        this.myavg = new float[nSensori];
        this.manager=manager;
        this.nSensori=nSensori;
    }

    @Override
    public void run() {
        try {
            while (true){
                myavg = manager.get(id);  //ogni processor fa richiesta al manager di ricevere la sua media di valori, entrando in coda fino a che non arriva il suo turno
                System.out.print("ProcessorThread " + id + ": " + Arrays.toString(myavg));
                System.out.println();
                sleep((int) ((Math.random()*2000)+1000));
                count++;
            }
        } catch (InterruptedException e) {
            System.out.print("Processor "+ id +" interrotto -> ");
        }
    }
}
