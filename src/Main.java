public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello world!");

        int nSensori = 10;
        int mProcessor = 3;
        int k = 12;
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
        System.out.println("AquisitionThread -> " + aq.avgInserite);

        for (int i = 0; i < mProcessor; i++) {
            processors[i].interrupt();
            processors[i].join();
            System.out.println("ProcessorThread" + i + " count -> " + processors[i].count);
        }
    }
}