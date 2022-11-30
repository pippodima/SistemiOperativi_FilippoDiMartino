public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello world!");

        int nSensori = 10;
        int mProcessor = 5;
        int k = 3;
        ValueManager manager = new ValueManager(nSensori,mProcessor);

        AcquisitionThread aq = new AcquisitionThread(nSensori,k,manager);   //inizializzazione di Acquisition Thread e avvio
        aq.start();                                                         //che inizializza tutte le movingAvgQueue  e tutti i SensorThread

        ProcessorThread[] processors = new ProcessorThread[mProcessor];     //inizializzazione dei ProcessorThread e avvio
        for (int i = 0; i < mProcessor; i++) {
            processors[i] = new ProcessorThread(i,nSensori,manager);
            processors[i].start();
        }

        Thread.sleep(10000);


        // interruzione e recupero dei dati interessati

        aq.interrupt();
        aq.join();
        System.out.println("AquisitionThread avgInserite -> " + aq.avgInserite);

        for (int i = 0; i < mProcessor; i++) {
            processors[i].interrupt();
            processors[i].join();
            System.out.println("ProcessorThread " + i + " richieste fatte -> " + (processors[i].count)+1); //+1 perchè count è inizializzato a -1
        }
    }
}