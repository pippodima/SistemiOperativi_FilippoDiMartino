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
        while(setcount!=count)wait();//ho aggiunto una funzione che controlla se la funzione get() è stata fatta perchè senza questa condizione poteva capitare che i processor si scambiavano

        //c'è un bug che non permette al programma di far skippare ai processor l'array generato, prendendo quello del ciclo successivo
        // con l'aggiunta di Thread.sleep() questo bug accade solo al primo ciclo invece che randomicamente
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
