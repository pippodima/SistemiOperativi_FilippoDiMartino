public class AcquisitionThread extends Thread {
    float[] avgs;
    int avgInserite = 0;
    ValueManager manager;
    SensorThread[] sensorThreads;
    int nSensori;

    AcquisitionThread(int nSensori, int k, ValueManager manager) {
        this.nSensori=nSensori;
        this.manager = manager;
        this.avgs = new float[nSensori];
        this.sensorThreads = new SensorThread[nSensori];
        for (int i = 0; i < nSensori; i++) {
            sensorThreads[i] = new SensorThread(i, 100, k);
            sensorThreads[i].start();
        }
        System.out.println("Finito il costruttore di Aq");
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println("Aq richiesta n." + avgInserite); //Ho tolto il for perchè non entrava nel metodo,  usando avg inserite come counter e come indice della posizione corrente
                avgs[avgInserite] = sensorThreads[avgInserite].queue.getAvg();
                manager.set(avgs);
                avgInserite=(avgInserite+1)%nSensori;
            }
        } catch (InterruptedException e) {
            System.out.println("Procedo con l'interruzione");
        } finally {
            try {
                for (int i = 0; i < nSensori; i++) {
                    sensorThreads[i].interrupt();
                    sensorThreads[i].join();
                    System.out.println("Valori generati da: " + sensorThreads[i].id + " -> " + sensorThreads[i].value % 1000 + " Valori in coda -> " + sensorThreads[i].queue.values.size());
                }
            } catch (InterruptedException e) {
                System.out.println("Errore nell'interruzione dei sensori");
            }

        }
    }
}
