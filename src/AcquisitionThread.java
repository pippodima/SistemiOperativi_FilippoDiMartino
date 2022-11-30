public class AcquisitionThread extends Thread {
    float[] avgs;
    int avgInserite=0;
    ValueManager manager;
    SensorThread[] sensorThreads;
    int nSensori;

    AcquisitionThread(int nSensori, int k, ValueManager manager) {
        this.nSensori=nSensori;
        this.manager = manager;
        this.avgs = new float[nSensori];
        this.sensorThreads = new SensorThread[nSensori];
        for (int i = 0; i < nSensori; i++) {
            sensorThreads[i] = new SensorThread(i, 100, k);         //inizializzazione dei sensorThread
            sensorThreads[i].start();
        }
        System.out.println("Finito il costruttore di Aq");
    }

    @Override
    public void run() {
        try {
            while (true) {
                for (int i = 0; i < nSensori; i++) avgs[i] = sensorThreads[i].queue.getAvg();     //richiesta gia ordinata delle medie dei vari sensori
                manager.set(avgs);
                avgInserite++;
            }
        } catch (InterruptedException e) {
            System.out.println("Procedo con l'interruzione");
        } finally {       //ho usato un blocco finally per l'interruzione in modo tale da essere sicuro dell'interruzione dei SensorThread
            try {
                for (int i = 0; i < nSensori; i++) {
                    sensorThreads[i].interrupt();                                    //interruzione e acqusizione dei dati necessari
                    sensorThreads[i].join();
                    System.out.println("Valori generati dal sensore: " + sensorThreads[i].id + " -> " + sensorThreads[i].value % 1000 + " Valori in coda -> " + sensorThreads[i].queue.values.size());
                }
            } catch (InterruptedException e) {
                System.out.println("Errore nell'interruzione dei sensori");
            }

        }
    }
}
