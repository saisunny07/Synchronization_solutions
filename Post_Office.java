import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.Random;
    public class Post_Office {
        static int N;
        static int S;
        static int M;
        static Semaphore[] emptySpaces;
        static Semaphore[] permits;
        static ArrayList<String>[] mailbox;
        static int[] count;
        public static void Task2(){
            System.out.println("Hey! You has chosen to execute Post Office simulation!");
            System.out.print("Number of people participating in the simulation: ");
            Scanner inp = new Scanner(System.in);
            N = inp.nextInt();
            mailbox = new ArrayList[N];
            System.out.print("Maximum number of messages a person's mailbox can hold: ");
            S = inp.nextInt();
            System.out.print("Total number of messages to be sent before the simulation ends: ");
            M = inp.nextInt();


            emptySpaces = new Semaphore[N];
            count = new int[Post_Office.N];
            permits = new Semaphore[Post_Office.N];

            for(int i=0; i<N; i++){
                emptySpaces[i] = new Semaphore(1);
                mailbox[i] = new ArrayList<>();
                count[i] = 0;
                permits[i] = new Semaphore(1);
                Simulation s = new Simulation(i);
                s.start();
            }
        }
    }

    class Simulation extends Thread {
        int threadNumber;
        static int i;
        public Simulation(int threadNumber) {
            this.threadNumber = threadNumber;
        }

        public void run() {
            for(i=0 ; i < Post_Office.M; i++){
                //Enter the post office
                System.out.println("Enter the post office: Person " + threadNumber);
                //Read a message in that person’s mailbox
                if (Post_Office.count[threadNumber] == 0)
                    Execution.Execute(threadNumber);

                //compose a message addressed to a random person other than themselves
                int k = RandomNumberGenerator.randomNumber(threadNumber);
                if (Post_Office.mailbox[k].size() < Post_Office.S) {
                    Post_Office.emptySpaces[k].release();
                    //Call P() on a semaphore corresponding to the recipient’s mailbox.
                    Post_Office.emptySpaces[k].acquireUninterruptibly();
                    //Place the message in their mailbox
                    Post_Office.mailbox[k].add("Hi from person "+threadNumber);
                    System.out.println("Message inserted");
                }
            }
            System.out.println("Person "+ threadNumber + " left the post office");
            int j = new Random().nextInt(3, 7);
            for (int k = 0; k < j; k++)
                Thread.yield();
            Execution.Execute(threadNumber);
        }
    }

    class Execution {
        static Semaphore protect = new Semaphore(1);
        public static void Execute(int threadNumber) {
            if (Post_Office.count[threadNumber] == 0) {
                System.out.println("Currently no messages present in person "+threadNumber+" mailbox!");
                Post_Office.count[threadNumber]++;
                Thread.yield();
            } else {
                Post_Office.permits[threadNumber].acquireUninterruptibly();
                System.out.println("Number of messages received in mailbox " + threadNumber + " : "
                        + Post_Office.mailbox[threadNumber].size());

                int len = Post_Office.mailbox[threadNumber].size();
                while (len > 0) {
                    //Call V() on a semaphore corresponding to that person’s mailbox
                    Execution.protect.acquireUninterruptibly();
                    System.out.println("Message "+len+" in mailbox "+ threadNumber+ " is "+
                            Post_Office.mailbox[threadNumber].get(0));
                    Post_Office.mailbox[threadNumber].remove(0);
                    len--;
                    Thread.yield();
                    Execution.protect.release();
                }
            }
        }
    }

    class RandomNumberGenerator {
        public static int randomNumber(int i) {
            int n = new Random().nextInt(0, Post_Office.N);
            if (n != i)
                return n;
            else {
                if (n == 0)
                    return new Random().nextInt(1, Post_Office.N);
                else if (n == Post_Office.N)
                    return new Random().nextInt(0, (Post_Office.N - 1));
                else
                    return n - 1; // can use (n+1) also
            }
        }
    }

