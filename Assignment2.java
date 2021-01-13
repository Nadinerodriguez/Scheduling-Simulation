import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Assignment2 {

    public static void main(String[] args) {
        ArrayList<Process> pool = new ArrayList<>();
        Process p;

        try {
            FileInputStream fstream = new FileInputStream("assignment2.txt");
            Scanner sc = new Scanner(fstream);

            String inputLine;
            String[] params;
            int pid, arrivalTime, cpuBurstLength;

            while(sc.hasNext()) {
                inputLine = sc.nextLine();
                params = inputLine.trim().split("\\s+");

                pid = Integer.parseInt(params[0]);
                arrivalTime = Integer.parseInt(params[1]);
                cpuBurstLength = Integer.parseInt(params[2]);

                p = new Process(pid, arrivalTime, cpuBurstLength);
                pool.add(p);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (args[0].toLowerCase().equals("fcfs")) {
            FCFS cpusch = new FCFS(pool);
            cpusch.runScheduler();
        }

        else if (args[0].toLowerCase().equals("srtf")) {
            SRTF cpusch = new SRTF(pool);
            cpusch.runScheduler();
        }

        else if (args[0].toLowerCase().equals("rr")) {
            RR cpusch = new RR(pool, Integer.parseInt(args[1]));
            cpusch.runScheduler();
        }
    }
}
