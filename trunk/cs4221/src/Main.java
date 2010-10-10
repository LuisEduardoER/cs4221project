public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{

        int maxFD = 10, width = 2;

        FDSet fdset;
        BigSet bigset;

        ///Step 0 : Read the input and put it into a FDSet::fdset
        String input = "";
        for (int i = 0; i < args.length; i++){
            input = input.concat(args[i]);
        }
        System.out.println(input);

        ReadInput readInput = new ReadInput();
        String[][]fds = new String[maxFD][width];
        fds = readInput.readInput(input, maxFD, width);

        fdset = new FDSet(fds,maxFD);
        System.out.println();
        System.out.println("This is the original FDSet with the FDs");
        fdset.printFDSet();

        ///Step 3 Partition : Convert the FDSet into a few FDSets(BigSet)
        Partition p = new Partition();
        bigset = p.works(fdset);
        System.out.println();
        System.out.println("This is the BigSet after partiton(result (p/s:work in progress))");
        bigset.printBigSet();

        Merge m = new Merge();
        bigset = m.works(bigset);
        System.out.println();
        System.out.println("This is the BigSet after merge(result (p/s:work in progress))");
        bigset.printBigSet();

        ///Step 5 Transistivity
        Transitivity t = new Transitivity();
        //t.test();
    }
}
