package com.haniokasai.cpulimitController;

import java.io.*;
import java.util.ArrayList;

public class CpulimitThread extends Thread{
    private InputStream is;
    private final int limit;
    private final int pid;
    public  Process process;
    public ArrayList<String> outputlines;
    public boolean isdetected = false;
    public boolean noprocessfound = false;
    public boolean isdead = false;


    //cpulimit --pid=12728  --monitor-forks  --limit=10
    public CpulimitThread(int pid, int limit) {
        this.pid = pid;
        this.limit = limit;
        outputlines = new ArrayList<>();

    }

    @Override
    public void run() {
        try {
            String[] cmd = (new String[]{"cpulimit","--verbose","--lazy","--pid=" + pid, "--limit=" + limit, "--monitor-forks"});
            ProcessBuilder builder = new ProcessBuilder(cmd);
            builder.redirectErrorStream(true);

            process = builder.start();
            OutputStream stdin = process.getOutputStream();
            is = process.getInputStream();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
                int i = 0;
                i++;
                System.out.println(i);
                while (process.isAlive()) {
                    String line = br.readLine();
                    i++;
                    System.out.println(i);
                    try {

                        if(Main.debug)System.out.println(line);

                        outputlines.add(line);
                        int len = outputlines.size();
                        while (len > 150) {
                            outputlines.remove(0);
                            --len;
                        }

                        //matches
                        if (line.matches(".*" + "detected" + ".*")) {
                            isdetected =true;
                        }

                        //matches
                        if (line.matches(".*" + "No process found" + ".*")) {
                            noprocessfound =true;
                            process.destroy();
                        }

                        //matches
                        if (line.matches(".*" + "dead!" + ".*")) {
                            isdead =true;
                            process.destroy();
                        }

                    } catch (NullPointerException e) {
                        if (Main.debug) e.printStackTrace();
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
