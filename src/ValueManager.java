public class ValueManager {
    float[] avgs;
    int count = -1;
    int setcount=-1;
    int nProcessor;
    int nSensori;


    ValueManager(int nSensori, int nProcessor) {
        this.nSensori = nSensori;
        this.avgs = new float[nSensori];
        this.nProcessor = nProcessor;
    }

    public synchronized void set(float[] avgs) throws InterruptedException {
        while(setcount!=count)wait();
        Thread.sleep(10);
        this.avgs = avgs;
        count = (count + 1) % nProcessor;
        notifyAll();
    }

    public synchronized float[] get(int id) throws InterruptedException {
        while (id != count || avgs[avgs.length-1]==0) wait();  //modificata la condizione perchè era possibile stampare un vettore mezzo vuoto: avgs[avgs.length-1]==0 invece che avgs[0]==0
        float[] t = avgs;
        avgs = new float[nSensori];
        setcount = (setcount + 1) % nProcessor;
        notifyAll();
        return t;
    }
}
