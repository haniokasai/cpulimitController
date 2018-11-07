package com.haniokasai.cpulimitController;

import java.io.*;
import java.util.ArrayList;

public class CpulimitThread extends Thread{

    private InputStream is;
    private final String[] cmd;

    public  Process process;
    public ArrayList<String> outputlines;
    public boolean isdetected = false;
    public boolean noprocessfound = false;
    public boolean isdead = false;
    public String msg = null;


    //cpulimit --pid=12728  --monitor-forks  --limit=10
    public CpulimitThread(String[] cmd) {
        this.cmd = cmd;
        outputlines = new ArrayList<>();

    }

    @Override
    public void run() {
        try {
            ProcessBuilder builder = new ProcessBuilder(cmd);
            builder.redirectErrorStream(true);

            process = builder.start();
            OutputStream stdin = process.getOutputStream();
            is = process.getInputStream();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
                int i = 0;
                if(Main.debug)i++;
                if(Main.debug)System.out.println(i);
                while (process.isAlive()&&!isdead) {
                    String line = null;
                    try {
                         line = br.readLine();
                    }catch (IOException e){
                        noprocessfound = true;
                        isdead = true;
                    }
                    if(Main.debug)i++;
                    if(Main.debug)System.out.println(i);
                    if(line!=null) {
                        try {

                            if (Main.debug) System.out.println(line);

                            outputlines.add(line);
                            int len = outputlines.size();
                            while (len > 150) {
                                outputlines.remove(0);
                                --len;
                            }

                            //matches
                            if (line.matches(".*" + "detected" + ".*")) {
                                isdetected = true;
                                msg=line;
                                System.out.println("[detected]");
                            }

                            //matches
                            if (line.matches(".*" + "No process found" + ".*")) {
                                noprocessfound = true;
                                msg=line;
                                System.out.println("[No process found]");
                                process.destroy();
                            }

                            //matches
                            if (line.matches(".*" + "dead!" + ".*")) {
                                isdead = true;
                                msg=line;
                                System.out.println("[dead!]");
                                process.destroy();
                            }


                        } catch (NullPointerException e) {
                            if (Main.debug) e.printStackTrace();
                        }
                    }
                }
            }
        } catch (IOException e) {
            if (Main.debug) e.printStackTrace();
        }
    }

    public static String getScraping(String source, String pattern, String end_pattern) {
        int start = source.indexOf(pattern) + pattern.length();
        int end = source.indexOf(end_pattern, start + 1);
        return source.substring(start, end);
    }

}
