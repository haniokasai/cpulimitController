package com.haniokasai.cpulimitController;

import java.io.*;
import java.util.ArrayList;
public class ExecCommand {

    /**
     * @param cmdarray cmdをString[]で渡しなさい。
     * @return ArrayListに出力をまとめる、エラーが出ればnull
     */

    public  ArrayList<String> execInstantCommand(String[] cmdarray){
        try {
            ProcessBuilder builder = new ProcessBuilder(cmdarray);
            builder.redirectErrorStream(true);

            Process process = builder.start();
            OutputStream stdin = process.getOutputStream();
            InputStream is = process.getInputStream();

            OutputLogger ol = new OutputLogger(is, process);
            ol.start();
            while(process.isAlive()){}
            if(Main.debug) System.out.println(ol.outputlines);
            return ol.outputlines;
        } catch (IOException e) {
            if(Main.debug)e.printStackTrace();
            return null;
        }

    }

    public static void execCpulimit(int pid,int limit){
        CpulimitThread cp = new CpulimitThread(pid,limit);
        cp.start();
        while (cp.process.isAlive()){

        }
    }



}