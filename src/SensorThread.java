public class SensorThread extends Thread {
    int id;
    int attesa;
    float value;
    MovingAvgQueue queue;

    SensorThread(int id, int x, int k) {
        this.id = id;
        this.attesa = x;
        this.value = id * 1000;
        this.queue = new MovingAvgQueue(id, k);
        System.out.println(" Creato Sensore N." + id);
    }

    @Override
    public void run() {
        try {
            while (true) {
                queue.add(value);   //aggiunta delle misurazioni alla coda
                value++;
                sleep(attesa);
            }
        } catch (InterruptedException e) {
            System.out.print("Sensor " + id + " Interrotto ->");
        }
    }
}
