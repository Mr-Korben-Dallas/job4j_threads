package ru.job4j.concurrent;

public class Wget {
    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                    try {
                        System.out.println("Loading is started ... ");
                        for (int index = 0; index <= 100; index++) {
                            System.out.print("\rLoaded : " + index  + "%");
                            Thread.sleep(1000);
                        }
                        System.out.println("\nDone.");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        thread.start();
    }
}
