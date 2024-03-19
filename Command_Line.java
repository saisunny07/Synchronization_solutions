public class Command_Line {
    public static void main(String[] args) {

        try {
        if(args.length != 2)
            System.out.println("No or different number of command line arguments provided");
        else if(args[0].equals("-A") && args[1].equals("1"))
                Dining_Philosophers.Task1();
        else if(args[0].equals("-A") && args[1].equals("2"))
            Post_Office.Task2();
        else if(args[0].equals("-A") && args[1].equals("3"))
            Readers_Writers.Task3();
        else{
            System.out.println("Entered an invalid input");
        }
        }catch(InterruptedException exc){};
    }
}
