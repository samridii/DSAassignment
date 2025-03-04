//Question 6a.

// File Name: ThreadController.java

class NumberPrinter {
    // Prints "0", even and odd numbers
    public void printZero() {
        System.out.print("0");
    }
    public void printEven(int num) {
        System.out.print(num);
    }
    public void printOdd(int num) {
        System.out.print(num);
    }
}

public class ThreadController {
    private final NumberPrinter numberPrinter; // Instance of NumberPrinter
    private int currentNumber = 1; // Tracks the current number to print
    private boolean isZeroTurn = true; // Indicates if it's ZeroThread's turn

    public ThreadController(NumberPrinter numberPrinter) {
        this.numberPrinter = numberPrinter;
    }

    // Synchronized method to print "0"
    public synchronized void printZero() throws InterruptedException {
        while (!isZeroTurn) {
            wait(); // Wait if it's not ZeroThread's turn
        }
        numberPrinter.printZero(); 
        isZeroTurn = false; // Switch to EvenThread or OddThread
        notifyAll(); 
    }

    // Synchronized method to print even numbers
    public synchronized void printEven() throws InterruptedException {
        while (isZeroTurn || currentNumber % 2 != 0) {
            wait(); // Wait if it's not EvenThread's turn or the number is odd
        }
        numberPrinter.printEven(currentNumber); // Print even number
        currentNumber++; // Increment the number
        isZeroTurn = true; // Switch back to ZeroThread
        notifyAll(); // Notify all waiting threads
    }

    // Synchronized method to print odd numbers
    public synchronized void printOdd() throws InterruptedException {
        while (isZeroTurn || currentNumber % 2 == 0) {
            wait(); // Wait if it's not OddThread's turn or the number is even
        }
        numberPrinter.printOdd(currentNumber); // Print odd number
        currentNumber++; 
        isZeroTurn = true; 
        notifyAll(); 
    }

    // Inner classes for threads
    static class ZeroThread extends Thread {
        private final ThreadController controller; // ThreadController instance
        private final int n; // Maximum number to print

        public ZeroThread(ThreadController controller, int n) {
            this.controller = controller;
            this.n = n;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < n; i++) {
                    controller.printZero(); // Print "0" n times
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class EvenThread extends Thread {
        private final ThreadController controller; // ThreadController instance
        private final int n; 

        public EvenThread(ThreadController controller, int n) {
            this.controller = controller;
            this.n = n;
        }

        @Override
        public void run() {
            try {
                for (int i = 2; i <= n; i += 2) {
                    controller.printEven(); // Print even numbers up to n
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class OddThread extends Thread {
        private final ThreadController controller; // ThreadController instance
        private final int n; // Maximum number to print

        public OddThread(ThreadController controller, int n) {
            this.controller = controller;
            this.n = n;
        }

        @Override
        public void run() {
            try {
                for (int i = 1; i <= n; i += 2) {
                    controller.printOdd(); // Print odd numbers up to n
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        NumberPrinter numberPrinter = new NumberPrinter();
        ThreadController controller = new ThreadController(numberPrinter);
        int n = 10; // Example input (changed to 10)

        // Create threads
        ZeroThread zeroThread = new ZeroThread(controller, n);
        EvenThread evenThread = new EvenThread(controller, n);
        OddThread oddThread = new OddThread(controller, n);

        // Start threads
        zeroThread.start();
        oddThread.start();
        evenThread.start();

        // Wait for threads to finish
        try {
            zeroThread.join();
            oddThread.join();
            evenThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}