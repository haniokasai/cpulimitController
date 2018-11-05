package com.haniokasai.cpulimitController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Parse {
    public static boolean iscpulimitEnable(){
        final ArrayList <String> commandoutput = new ExecCommand().execCommand(new String[]{"cpulimit", "-h"});
        if(commandoutput == null)return false;
        /*
        CPUlimit version 2.4
        Usage: cpulimit TARGET [OPTIONS...] [-- PROGRAM]
        TARGET must be exactly one of these:
        */
        for (String line : commandoutput) {
            if (line.contains("CPUlimit")&&line.contains("version")) {
                return true;
            }
        }
        return false;
    }

    public static String getcpulimitVersion(){
        final ArrayList <String> commandoutput = new ExecCommand().execCommand(new String[]{"cpulimit", "-h"});
        if(commandoutput == null)return null;
        for (String line : commandoutput) {
            if (line.contains("CPUlimit")&&line.contains("version")) {
                return line.replaceAll("CPUlimit","").replaceAll("version","").trim();
            }
        }
        return null;
    }

    /**
     * @param pid process id
     * @param percent percent (if you have 4 core cpu, maximum is 400)
     */
    public static void setlimitCPU(int pid,int percent){
        /*CpulimitThread cp = new CpulimitThread(pid,percent);
        cp.start();
        while (!cp.isdead){

        }*/
        test(pid,percent);
    }

    public static void test(int pid,int limit){
        String[] cmd = (new String[]{"cpulimit","--verbose","--lazy","--pid=" + pid, "--limit=" + limit, "--monitor-forks"});
        ProcessBuilder builder = new ProcessBuilder(cmd);
        builder.redirectErrorStream(true);

        try {
            Process process = builder.start();
            while(process.isAlive()){}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
