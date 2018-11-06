package com.haniokasai.cpulimitController;

import java.io.*;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class cpulimittest {


    //http://software.fujitsu.com/jp/manual/manualfiles/M070077/J2UZ9520/01Z2A/note03/note0179.html
    public static void test(){

        try {
            // "sort data.txt"を、子プロセスとして実行
            // sortは、テキストをソートするコマンド
            // data.txtは、javaプロセスのカレントディレクトリ上のテキストファイル
            Process p = Runtime.getRuntime().exec("cpulimit -p 8162 -l 6 -v");

            // 子プロセスの標準出力および標準エラー出力を入力するスレッドを起動
            new StreamThread(p.getInputStream(), "stdout.txt").start();
            new StreamThread(p.getErrorStream(), "stderr.txt").start();
            // 子プロセスの終了を待つ
            try {
                p.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}


/**
 *子プロセスの出力ストリームから入力し、ファイルに出力するスレッド
 */
class StreamThread extends Thread {
    private static final int BUF_SIZE = 5;
    private InputStream in;
    private BufferedOutputStream out;

    public StreamThread(InputStream in, String outputFilename) throws IOException {
        this.in = in;
        this.out = new BufferedOutputStream(new FileOutputStream(outputFilename));
    }

    public void run() {
        byte[] buf = new byte[BUF_SIZE];
        int size = -1;
        try {
            while ((size = in.read(buf, 0, BUF_SIZE)) != -1) {
                out.write(buf, 0, size);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
            }
            try {
                out.close();
            } catch (IOException ex) {
            }
        }
    }
}