package com.haniokasai.cpulimitController;


import static com.haniokasai.cpulimitController.Parse.getcpulimitVersion;
import static com.haniokasai.cpulimitController.Parse.iscpulimitEnable;
import static com.haniokasai.cpulimitController.Parse.setlimitCPU;
import static com.haniokasai.cpulimitController.cpulimittest.test;

public class Main {
    public static boolean debug = false;
    public static void main(String args[]) {
        System.out.println("cpulimit:"+String.valueOf(iscpulimitEnable())+" version:"+String.valueOf(getcpulimitVersion()));
        switch (args.length){
            case 0:
            case 1:
                System.out.println("pid limit <debug>");
                test();
                break;
            case 2:
                debug=false;
                setlimitCPU(Integer.valueOf(args[0]),Integer.valueOf(args[1]));
            break;
            default:
                debug=true;
                setlimitCPU(Integer.valueOf(args[0]),Integer.valueOf(args[1]));
                break;
        }
    }
}
