public class Process {
    private int PID;
    private int arrivalTime;
    private int cpuBurstLength;
    private int cpuBurstRemaining;
    private int waitTime;
    private int responseTime;
    private int turnaroundTime;
    private int completedTime;

    public Process(int p, int t, int l) {
        PID = p;
        arrivalTime = t;
        cpuBurstLength = l;
        cpuBurstRemaining = l;
        completedTime = -1;
        waitTime = -1;
        responseTime = -1;
        turnaroundTime = -1;
    }

    public int getPID() {
        return PID;
    }

    public void setID(int p) {
        PID = p;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int a) {
        arrivalTime = a;
    }

    public int getCPUBurstLength() {
        return cpuBurstLength;
    }

    public void setCPUBurstLength(int b) {
        cpuBurstLength = b;
    }

    public int getCPUBurstRemaining() {
        return cpuBurstRemaining;
    }

    public void decrementCPUBurstRemaining() {
        cpuBurstRemaining--;
    }

    public int getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(int t) {
        completedTime = t;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(int t) {
        turnaroundTime = t;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int t) {
        waitTime = t;
    }

    public int getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(int t) {
        responseTime = t;
    }
}
