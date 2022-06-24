package ru.job4j.concurrent;

import java.util.ArrayList;
import java.util.List;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        int step = 0;
        ArrayList<Character> characters = new ArrayList<>(
                List.of('-', '\\', '|', '/')
        );
        while (!Thread.currentThread().isInterrupted()) {
            try {
                System.out.print("\rLoading: " + characters.get(step));
                Thread.sleep(500);
                step = (step < 3) ? ++step : 0;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(1000);
        progress.interrupt();
    }
}
