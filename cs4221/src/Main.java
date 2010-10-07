public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{

        Control control = new Control();
        int maxFD = 10, width = 2;

        FDSet fdset;
        BigSet bigset;

        ///Step 0 : Read the input and put it into a FDSet namely fdset
        String input = "";
        for (int i = 0; i < args.length; i++){
            input = input.concat(args[i]);
        }
        System.out.println(input);
        String[][]fds = new String[maxFD][width];
        fds = control.readInput(input, maxFD, width);
        fdset = new FDSet(fds,maxFD);
        System.out.println();
        fdset.printFDSet();

        ///Step 3 Partition : Convert 1 FDSet into a few FDSets(BigSet)
        Partition p = new Partition();
        bigset = p.works(fdset);
        System.out.println();
        bigset.printBigSet();

        ///Step 5 Transistivity
        Transitivity t = new Transitivity();
        //t.test();
    }
}
