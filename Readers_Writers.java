import java.util.Scanner;
import java.util.concurrent.Semaphore;

    public class Readers_Writers {
        static int r;
        static int w;
        static Semaphore mutex = new Semaphore(1);
        static int loopCount=0;
        static int readCount=0;
        static int writeCount=0;
        static int max_readers;
        static Semaphore area = new Semaphore(1);
        static Semaphore write_trigger = new Semaphore(0);

        public static void Task3(){
            System.out.println("Hey! You has chosen to execute Readers_Writers problem Implementation");
            Scanner inp = new Scanner(System.in);
            System.out.print("Enter the number of readers,R: ");
            r = inp.nextInt();
            System.out.print("Enter the number of writers,W: ");
            w = inp.nextInt();
            System.out.print("Enter number of maximum readers, max_readers: ");
            max_readers = inp.nextInt();

            //Forking the threads equal to number of readers
            for(int i=0; i<r; i++) {
                Read read = new Read();
                read.start();
            }

            //Forking the threads equal to number of writers
            for(int i=0; i<w; i++) {
                Write write = new Write();
                write.start();
            }
        }
    }

    class Read extends Thread{
        public void run(){
            Readers_Writers.mutex.acquireUninterruptibly();
            Readers_Writers.readCount++;
            Readers_Writers.loopCount++;
            if(Readers_Writers.loopCount == 1) {
                Readers_Writers.area.acquireUninterruptibly();
            }
            System.out.println("Read operation by reader " + Readers_Writers.readCount);
            if(Readers_Writers.loopCount == Readers_Writers.max_readers){
                Readers_Writers.loopCount = 0;
                if(Readers_Writers.writeCount < Readers_Writers.w) {
                    Readers_Writers.area.release();
                    Readers_Writers.write_trigger.release();
                }
            }
            else if(Readers_Writers.readCount == Readers_Writers.r){
                System.out.println("No more readers");
                if(Readers_Writers.writeCount < Readers_Writers.w) {
                    Readers_Writers.area.release();
                    Readers_Writers.write_trigger.release();
                }
            }
            else {
                Readers_Writers.mutex.release();
            }
        }
    }

    class Write extends Thread{
        public void run(){
            int write_loopCount = 0;
            Readers_Writers.write_trigger.acquireUninterruptibly();
            if(write_loopCount < 1){
                Readers_Writers.area.acquireUninterruptibly();
            }
            if(Readers_Writers.readCount == Readers_Writers.r) {
                Readers_Writers.area.release();
                while ((Readers_Writers.w - Readers_Writers.writeCount) > 0) {
                    Readers_Writers.writeCount++;
                    Readers_Writers.area.acquireUninterruptibly();
                    System.out.println("...........................");
                    System.out.println("Write operation by writer " + Readers_Writers.writeCount);
                    System.out.println("...........................");
                    System.out.println("Area control released");
                    Readers_Writers.area.release();
                }
                Readers_Writers.write_trigger.release();
            }
            else {
                Readers_Writers.writeCount++;
                Readers_Writers.mutex.release();
                System.out.println("...........................");
                System.out.println("Write operation by writer " + Readers_Writers.writeCount);
                System.out.println("...........................");
                Readers_Writers.area.release();
            }
        }
    }
