public class PrintABC_sync {
    final static Object monitor = new Object();
    private static volatile String state = "Printing A";

    public static void main(String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i<5;i++){
                    printA();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i<5;i++){
                    printB();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i<5;i++){
                    printC();
                }
            }
        }).start();
    }

    public static void printA() {
        synchronized (monitor) {
            try {
                while (state.equals("Printing C") || state.equals("Printing B") ) {
                    monitor.wait();
                }
                System.out.println("A");
                state = "Printing B";
                monitor.notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void printB() {
        synchronized (monitor) {
            try {
                while (state.equals("Printing A") || state.equals("Printing C") ) {

                    monitor.wait();
                }
                System.out.println("B");
                state = "Printing C";
                monitor.notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void printC() {
        synchronized (monitor) {
            try {
                while (state.equals("Printing B") || state.equals("Printing A") ) {

                    monitor.wait();
                }
                System.out.println("C");
                state = "Printing A";
                monitor.notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
