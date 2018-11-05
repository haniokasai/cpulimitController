package com.haniokasai.cpulimitController;


import static com.haniokasai.cpulimitController.ExecCommand.execCpulimit;
import static com.haniokasai.cpulimitController.Parse.getcpulimitVersion;
import static com.haniokasai.cpulimitController.Parse.iscpulimitEnable;

public class Main {
    public static boolean debug = false;
    public static void main(String args[]) {
        System.out.println("cpulimit:"+String.valueOf(iscpulimitEnable())+" version:"+String.valueOf(getcpulimitVersion()));
        switch (args.length){
            case 3:
                debug=true;
            case 2:
                execCpulimit(Integer.valueOf(args[0]),Integer.valueOf(args[1]));
            break;
            default:
                System.out.println("pid limit <debug>");
                break;
        }
    }
}
