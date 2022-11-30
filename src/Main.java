public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello world!");

        int nSensori = 5;
        int mProcessor = 5;
        int k = 5;
        ValueManager manager = new ValueManager(nSensori,mProcessor);

        AcquisitionThread aq = new AcquisitionThread(nSensori,k,manager);
        aq.start();

        ProcessorThread[] processors = new ProcessorThread[mProcessor];
        for (int i = 0; i < mProcessor; i++) {
            processors[i] = new ProcessorThread(i,nSensori,manager);
            processors[i].start();
        }

        Thread.sleep(10000);

        aq.interrupt();
        aq.join();
        System.out.println("AquisitionThread avgInserite -> " + aq.avgInserite);

        for (int i = 0; i < mProcessor; i++) {
            processors[i].interrupt();
            processors[i].join();
            System.out.println("ProcessorThread " + i + " richieste fatte -> " + (processors[i].count+1));
        }
    }
}