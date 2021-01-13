import java.util.ArrayList;
import java.util.List;

abstract class Scheduler {
    private final List<Process> readyQueue;

    public Scheduler() {
        this.readyQueue = new ArrayList<>();
    }

    public void addHead(Process p) {
        this.readyQueue.add(0, p);
    }

    public void addTail(Process p) {
        this.readyQueue.add(p);
    }

    public void addProcess(int i, Process p) {
        this.readyQueue.add(i, p);
    }

    public Process removeHead() {
        return this.readyQueue.remove(0);
    }

    public Process removeTail() {
        return this.readyQueue.remove(readyQueue.size()-1);
    }

    public Process removeProcess(int i) {
        return this.readyQueue.remove(i);
    }

    public Process getHead() {
        return this.readyQueue.get(0);
    }

    public Process getTail() {
        return this.readyQueue.get(readyQueue.size()-1);
    }

    public Process getProcess(int i) {
        return this.readyQueue.get(i);
    }

    public boolean rqIsEmpty() {
        return this.readyQueue.isEmpty();
    }

    public int rqSize() {
        return this.readyQueue.size();
    }

    public List<Process> getRQ() {
        return this.readyQueue;
    }

    abstract double getAverageCPUUtil();

    abstract double getAverageTT(List<Process> pool);

    abstract double getAverageWT(List<Process> pool);

    abstract double getAverageRT(List<Process> pool);
}
