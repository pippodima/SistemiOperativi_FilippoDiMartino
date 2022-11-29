public class AcquisitionThread extends Thread {
    float[] avgs;
    int avgInserite;
    ValueManager manager;
    SensorThread[] sensorThreads;
    int nSensori;

    AcquisitionThread(int nSensori, int k, ValueManager manager) {
        this.manager = manager;
        this.avgs = new float[nSensori];
        this.sensorThreads = new SensorThread[nSensori];
        for (int i = 0; i < nSensori; i++) {
            sensorThreads[i] = new SensorThread(i, 500, k);
            sensorThreads[i].start();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                for (int i = 0; i < nSensori; i++) {
                    avgs[i] = sensorThreads[i].queue.getAvg();
                    manager.set(avgs);
                    avgInserite++;
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Procedo con l'interruzione");
        } finally {
            try {
                for (int i = 0; i < nSensori; i++) {
                    sensorThreads[i].interrupt();
                    sensorThreads[i].join();
                    System.out.println("Valori generati da: " + sensorThreads[i].id + " -> " + sensorThreads[i].value%1000 + " Valori in coda -> " + sensorThreads[i].queue.values.size());
                }
            } catch (InterruptedException e) {
                System.out.println("Errore nell'interruzione dei sensori");
            }

        }
    }
}
