import java.util.List;

public class RR extends Scheduler {
    private final List<Process> pool;
    private Process currentProc;
    private final int timeSlice;
    private int timer;
    private int systemTime;
    private int idleTime;
    private final int processesTotal;
    private int poolIndex;

    public RR(List<Process> p, int ts) {
        super();
        currentProc = null;
        timeSlice = ts;
        timer = ts;
        pool = p;
        systemTime = 0;
        idleTime = 0;
        processesTotal = p.size();
        poolIndex = 0;
    }

    public void runScheduler() {
        System.out.println("Scheduling algorithm: Round Robin given Time Slice: " + timeSlice);
        System.out.println("============================================================");

        runPool(pool);

        System.out.println("<system time " + systemTime + "> All processes finished...");
        System.out.println("============================================================");

        double acpu = getAverageCPUUtil();
        double awt = getAverageWT(pool);
        double art = getAverageRT(pool);
        double att = getAverageTT(pool);

        System.out.print("Average CPU Usage:       ");
        System.out.printf("%.2f", acpu);
        System.out.println("%");

        System.out.print("Average Waiting Time:     ");
        System.out.printf("%.2f", awt);
        System.out.println();

        System.out.print("Average Response Time:    ");
        System.out.printf("%.2f", art);
        System.out.println();

        System.out.print("Average Turnaround Time:  ");
        System.out.printf("%.2f", att);
        System.out.println();

        System.out.println("============================================================");
    }

    private void runPool(List<Process> pool) {
        idleReport(pool);
        newArrivalsReport(pool);

        while(!(super.rqIsEmpty())) {
            currentProc = super.getHead();
            runProcess(pool);
            idleReport(pool);
        }
    }

    private void idleReport(List<Process> pool) {
        if (super.rqIsEmpty() && poolIndex < processesTotal) {
            while (systemTime < pool.get(poolIndex).getArrivalTime()) {
                System.out.println("<system time " + systemTime + "> CPU is idle");
                idleTime++;
                systemTime++;
            }
        }
    }

    private void runProcess(List<Process> pool) {
        if (currentProc.getResponseTime() == -1)
            currentProc.setResponseTime(systemTime - currentProc.getArrivalTime());

        while (currentProc.getCPUBurstRemaining() > 0) {
            System.out.println("<system time " + systemTime + "> process " + currentProc.getPID() + " is running");
            systemTime++;
            currentProc.decrementCPUBurstRemaining();
            timer--;
            newArrivalsReport(pool);
            preemptionReport(currentProc, timer);
        }
        currentProc.setTurnaroundTime(systemTime - currentProc.getArrivalTime());
        currentProc.setWaitTime(currentProc.getTurnaroundTime() - currentProc.getCPUBurstLength());
        timer = timeSlice;
        System.out.println("<system time " + systemTime + "> process " + super.removeHead().getPID() + " is finished...");
    }

    private void newArrivalsReport(List<Process> pool) {
        while ((poolIndex < processesTotal) && (systemTime == pool.get(poolIndex).getArrivalTime())) {
            super.addTail(pool.get(poolIndex));
            poolIndex++;
        }
    }

    private void preemptionReport(Process cp, int timer) {
        int burstRemaining = cp.getCPUBurstRemaining();
        if ((burstRemaining > 0) && timer == 0) {
            rotate();
            this.timer = timeSlice;
            currentProc = super.getHead();

            if (currentProc.getResponseTime() == -1) {
                currentProc.setResponseTime(systemTime - currentProc.getArrivalTime());
            }
        }
    }

    private void rotate() {
        Process interruptedProcess = super.removeHead();
        super.addTail(interruptedProcess);
    }

    public double getAverageCPUUtil() {
        double averageCPUUtil = (double)(systemTime - idleTime) / systemTime;
        return 100 * averageCPUUtil;
    }

    public double getAverageWT(List<Process> pool) {
        double sum = 0.0;
        int totalProcesses = pool.size();
        for (int i = 0; i < pool.size(); i++) {
            sum += pool.get(i).getWaitTime();
        }
        return sum / totalProcesses;
    }

    public double getAverageRT(List<Process> pool) {
        double sum = 0.0;
        int totalProcesses = pool.size();
        for (int i = 0; i < pool.size(); i++) {
            sum += pool.get(i).getResponseTime();
        }
        return sum / totalProcesses;
    }

    public double getAverageTT(List<Process> pool) {
        double sum = 0.0;
        int totalProcesses = pool.size();
        for (int i = 0; i < pool.size(); i++) {
            sum += pool.get(i).getTurnaroundTime();
        }
        return sum / totalProcesses;
    }
}
