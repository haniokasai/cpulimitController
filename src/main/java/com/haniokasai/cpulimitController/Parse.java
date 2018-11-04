package com.haniokasai.cpulimitController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Parse {
    public static boolean iscpulimitEnable(){
        final ArrayList <String> commandoutput = new ExecCommand().execInstantCommand(new String[]{"cpulimit", "-h"});
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
        final ArrayList <String> commandoutput = new ExecCommand().execInstantCommand(new String[]{"cpulimit", "-h"});
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

    }


}
