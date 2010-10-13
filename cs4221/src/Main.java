import java.util.*;

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
        FDSet temp = fdset;
        ///Step 1: Eliminate extraneous attributes
       // fdset=FDAlgorithms.allSingleRHSAttribute(fdset);
        fdset=FDAlgorithms.removeExtraneousAttr(fdset);
        System.out.println();
        System.out.println("This is after step 1");
        fdset.printFDSet();
        
        ///Step 2:
        fdset=FDAlgorithms.removeRedundantFDs(fdset);
        System.out.println();
        System.out.println("This is the minimal cover after step 1 and 2");
        fdset.printFDSet();

        ///Step 3 Partition : Convert the FDSet into a few FDSets(BigSet)
        Partition p = new Partition();
        bigset = p.works(fdset);
        System.out.println();
        System.out.println("This is the BigSet after partiton(result (p/s:work in progress))");
        bigset.printBigSet();
        System.out.println();

        Merge m = new Merge();
        bigset = m.works(bigset,fdset);
        System.out.println();
        System.out.println("This is the BigSet after merge(result (p/s:work in progress))");
        bigset.printBigSet();

        System.out.println("\nThis is the FDSet J");
        ArrayList<FDSet> Js = m.getJ();
        if(Js.size() > 0){
            for(int i = 0; i < Js.size();i++){
                Js.get(i).printFDSet();
                System.out.println();
            }
        }

        ///Step 5 Transistivity
        String[][] data = bigset.BigSetToArray(bigset.countFD());
        Transitivity t = new Transitivity(data);
        System.out.println();
        data = t.m5_plotTransitivity();
        t.m5_indentifyTransitivity(data);
        System.out.println("This is the BigSet after TD elimination(result (p/s:work in progress))");
        t.m5_printFDBigset();
        System.out.println("There u have the Relations");
        t.m6_displayRelation();
        //System.out.println("This are three other testcases for transitivity");
        //t.test();
    }
}
