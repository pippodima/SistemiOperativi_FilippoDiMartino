public class ValueManager {
    float[] avgs;
    int count = -1;
    int nProcessor;
    int nSensori;

    ValueManager(int nSensori, int nProcessor) {
        this.nSensori = nSensori;
        this.avgs = new float[nSensori];
        this.nProcessor = nProcessor;
    }

    public synchronized void set(float[] avgs) {
        this.avgs = avgs;
        count = (count + 1) % nProcessor;
        notifyAll();
    }

    public synchronized float[] get(int id) throws InterruptedException {
        while (id != count || avgs[0] == 0) wait();
        float[] t = avgs;
        avgs = new float[nSensori];
        notifyAll();
        return t;
    }
}
