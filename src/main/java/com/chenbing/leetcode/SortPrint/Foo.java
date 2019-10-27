package com.chenbing.leetcode.SortPrint;

import java.util.concurrent.Semaphore;

class Foo {

    private Semaphore semaphore_2_3 = new Semaphore(0);
    private Semaphore semaphore_1_2 = new Semaphore(0);

    public Foo() {
    }

    public void first(Runnable printFirst) throws InterruptedException {

        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        semaphore_1_2.release();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        semaphore_1_2.acquire();
        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();
        semaphore_2_3.release();
    }

    public void third(Runnable printThird) throws InterruptedException {
        semaphore_2_3.acquire();
        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
    }

    public static void main(String[] args) throws InterruptedException {
        Foo ad = new Foo();

        Thread t1 = new Thread(() -> {
            try {
                ad.first(() -> {
                    System.out.println("One");
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                ad.second(() -> {
                    System.out.println("Two");
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t3 = new Thread(() -> {
            try {
                ad.third(() -> {
                    System.out.println("Three");
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t2.start();
        Thread.sleep(1000);
        t3.start();
        Thread.sleep(1000);
        t1.start();
    }

}
