package com.haniokasai.cpulimitController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class OutputLogger extends Thread {
    private final InputStream is;
    private final Process pr;
    public ArrayList<String> outputlines;

    public OutputLogger(InputStream is, Process pr) {
        this.is = is;
        this.pr = pr;
        outputlines = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
                for (; ; ) {
                    String line = br.readLine();
                    if (line == null) break;
                    //System.out.println("[" + title + "]" + line);
                    try {
                        outputlines.add(line);
                    } catch (NullPointerException e) {
                        if (Main.debug) e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            if (Main.debug) e.printStackTrace();
        }
    }
}