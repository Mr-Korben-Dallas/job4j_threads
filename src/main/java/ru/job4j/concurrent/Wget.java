package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        String filename = "downloaded-file";
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(filename)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long before;
            long after;
            long referenceTime;
            while ((before = System.currentTimeMillis()) > 0
                    && (bytesRead = in.read(dataBuffer, 0, 1024)) != -1
            ) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                after = System.currentTimeMillis();
                referenceTime = after - before;
                if (referenceTime < speed) {
                    try {
                        Thread.sleep(speed - referenceTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 2) {
            throw new InterruptedException("Enter URL and speed parameters");
        }
        if (!args[0].contains("https://")) {
            throw new InterruptedException("URL must have: https://");
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        if (speed < 0) {
            throw new InterruptedException("Speed param invalid");
        }
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
