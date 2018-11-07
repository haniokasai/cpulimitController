package com.haniokasai.cpulimitController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Parse {

    /**
     * @return Block thread until result display.
     */
    public static boolean iscpulimitEnable(){
        int iii = 0;
        while (iii!=2) {
            iii++;

            CpulimitThread cp = new CpulimitThread(new String[]{"cpulimit", "-h"});
            cp.start();
            try {
                cp.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            final ArrayList<String> commandoutput = cp.outputlines;
            if (commandoutput == null && Main.debug) System.out.println("i");
            if (commandoutput != null) {
        /*
        CPUlimit version 2.4
        Usage: cpulimit TARGET [OPTIONS...] [-- PROGRAM]
        TARGET must be exactly one of these:
        */
                for (String line : commandoutput) {
                    if (line.contains("CPUlimit") && line.contains("version")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @return Block thread until result display.
     */
    public static String getcpulimitVersion(){
        int iii = 0;
        while (iii!=2) {
            iii++;
            CpulimitThread cp = new CpulimitThread(new String[]{"cpulimit", "-h"});
            cp.start();
            try {
                cp.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            final ArrayList<String> commandoutput = cp.outputlines;
            if (commandoutput != null) {
                for (String line : commandoutput) {
                    if (line.contains("CPUlimit") && line.contains("version")) {
                        return line.replaceAll("CPUlimit", "").replaceAll("version", "").trim();
                    }
                }
            }
        }
        return null;
    }

    /**
     * @param pid process id
     * @param percent percent (if you have 4 core cpu, maximum is 400)
     */
    public static void setlimitCPU(int pid,int percent){
        CpulimitThread cp = new CpulimitThread(new String[]{"cpulimit","--lazy","--pid=" + pid, "--limit=" + percent, "--monitor-forks"});
        cp.start();
    }

}
