import java.util.concurrent.Semaphore;
import java.util.Scanner;
import java.util.Random;

public class Dining_Philosophers {
        static int p;
        static int m;
        public static void Task1() throws InterruptedException{
            System.out.println("Hey! You has chosen to execute Dinning Philosophers having meals together!");
            //prompt user for number of philosophers and meals
            System.out.print("Enter the number of philosophers and meals to be eaten: ");
            Scanner inp = new Scanner(System.in);
            p = inp.nextInt();
            m = inp.nextInt();

            //Forking a single thread for each philosopher
            for(int i=0; i < Dining_Philosophers.p; i++) {
                MyThread A = new MyThread(i);
                A.start();
            }
        }
    }

    class Shared{
        static int threadCount=0;
        static int threadRelease=0;
        static int meals = Dining_Philosophers.m;
        static Semaphore meals_access = new Semaphore(1);
        public static Semaphore[] chopsticks(){
            int philosophers = Dining_Philosophers.p;
            Semaphore[] chopstick = new Semaphore[philosophers];
            for(int i=0; i<philosophers; i++) {
                chopstick[i] = new Semaphore(1);
            }
            return chopstick;
        }
    }
    class MyThread extends Thread {
        static Semaphore mutex = new Semaphore(1);
        static Semaphore semHold = new Semaphore(0);
        static Semaphore tutex = new Semaphore(1);
        static Semaphore tutHold = new Semaphore(0);
        public int threadNumber;



        public MyThread(int threadNumber) {
            this.threadNumber = threadNumber;
        }
        long start_time = System.nanoTime();
        public void run() {
            mutex.acquireUninterruptibly();
            Shared.threadCount++;
            if (Shared.threadCount == Dining_Philosophers.p-1) {
                for (int i = 0; i < Dining_Philosophers.p; i++) {
                    semHold.release();
                    //System.out.println("Released thread - " + i);
                }
                System.out.println("ALl philosophers sat down at table");
                mutex.release();
            } else {
                mutex.release();
                semHold.acquireUninterruptibly();
            }
            Semaphore[] store = new Semaphore[Dining_Philosophers.p];

            //Eating
            for (int i = 0; i < Dining_Philosophers.p; i++)
                store[i] = Shared.chopsticks()[i];

            for (Semaphore resource : store)
                resource.release();

            while (Shared.meals > 0) {
                Shared.meals_access.acquireUninterruptibly();
                Shared.meals--;
                System.out.println("Available meals - " + Shared.meals);

                int P = new Random().nextInt(0, (Dining_Philosophers.p));
                if (store[P].availablePermits() != 0) {
                    System.out.println( threadNumber + " - Pick up left chopstick");
                    if (store[(P + 1) % Dining_Philosophers.p].availablePermits() != 0) {
                        System.out.println(threadNumber + " - Pick up right chopstick");
                        System.out.println(threadNumber + " - Begin Eating");
                        int j = new Random().nextInt(3, 7);
                        for (int k = 0; k < j; k++) {
                            Thread.yield();
                            System.out.println(threadNumber + " put down left chopstick");
                            store[P].release();
                            System.out.println(threadNumber + " put down Right chopstick");
                            store[(P + 1) % Dining_Philosophers.p].release();
                            System.out.println(threadNumber + " - Begin thinking");
                            int l = new Random().nextInt(3, 7);
                            //for (int m = 0; m < l; m++)
                             //   Thread.yield();
                            Shared.meals_access.release();
                        }
                    }
                }
            }
            //Exit code
            tutex.acquireUninterruptibly();
            Shared.threadRelease++;
            if (Shared.threadRelease == Dining_Philosophers.p-1) {
                for (int i = 0; i < Dining_Philosophers.p; i++) {
                    tutHold.release();
                    //System.out.println("Released thread - " + i);
                }
                System.out.println("ALl philosophers left the table");
                tutex.release();
            } else {
            tutex.release();
            tutHold.acquireUninterruptibly();
            }
            long end_time = System.nanoTime();
            System.out.printf("Runtime in milliseconds = ");
            System.out.println((end_time - start_time) / 1000000.0);
        }
    }
