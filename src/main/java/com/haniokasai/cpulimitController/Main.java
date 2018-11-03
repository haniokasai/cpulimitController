package com.haniokasai.cpulimitController;


import static com.haniokasai.cpulimitController.Parse.getcpulimitVersion;
import static com.haniokasai.cpulimitController.Parse.iscpulimitEnable;

public class Main {
    public static boolean debug = false;
    public static void main(String args[]) {
        System.out.println("cpulimit:"+String.valueOf(iscpulimitEnable())+" version:"+String.valueOf(getcpulimitVersion()));
        switch (args.length){
            case 0:
            case 1:
                System.out.println("something");
                break;
            case 3:
                debug=true;
            case 2:
                switch (args[0]){
                    case "something":
                        System.out.println("something");
                        break;
                }
                break;
            default:
                System.out.println("something");
                break;
        }
    }
}
