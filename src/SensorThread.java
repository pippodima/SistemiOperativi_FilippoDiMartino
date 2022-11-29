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
    }

    @Override
    public void run() {
        try {
            while (true) {
                queue.add(value);
                value++;
                sleep(attesa);
            }
        } catch (InterruptedException e) {
            System.out.println("Sensor Interrotto");
        }
    }
}
