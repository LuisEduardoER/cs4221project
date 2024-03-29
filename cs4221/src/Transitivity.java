/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author GuoZheng
 */
import java.util.*;

public class Transitivity {

    private String[][] fdset;
    private String TDvar = "";

    public Transitivity() {
    }

    public Transitivity(String[][] input) {
        fdset = input;
    }

    public String[][] m5_plotTransitivity() {
        //Aims: Plot out the FD in a 2d array
        String checker[][] = new String[fdset.length][fdset.length];

        for (int i = 0; i < fdset.length; i++) {
            int x = -1, y = -1;
            String x1 = "", y1 = "";
            for (int j = 0; j < fdset.length; j++) {
                if (fdset[i][0].equals(fdset[j][0]) && x == -1) {
                    x = j;
                    x1 = fdset[j][0];
                //System.out.println("x=" + x + " " + x1);
                }
                if (fdset[i][1].equals(fdset[j][1]) && y == -1) {
                    y = j;
                    y1 = fdset[j][1];
                //System.out.println("y=" + y + " " + y1);
                }
                if (x != -1 && y != -1) {
                    break;
                }
            }
            checker[x][y] = x1 + "->" + y1;
        }

        return checker;
    }

    public String[][] m5_indentifyTransitivity(String[][] input, String[][] jset) {
        //Aim: Check each FD for transitivity and eliminate it
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input.length; j++) {
                boolean flag = false;
                if (input[i][j] != null) {
                    //check if there is transitivity for this FD
                    flag = m5_checkTransitivity(input, i, j);
                }
                if (flag == true) {
                    //eliminate this fd from the fdset[][]
                    fdset = m5_eliminateTransitivity(input, jset, i, j);
                }
            }
        }
        return fdset;
    }

    public boolean m5_checkTransitivity(String[][] input, int x, int y) {
        //Aim: Boolean return for transitivity check
        for (int i = 0; i < input.length; i++) {
            String[] st;
            String LHS = ".", RHS = ".";
            if (input[x][i] != null) {
                st = input[x][i].split("->");
                RHS = st[1];
                for (int j = 0; j < input.length; j++) {
                    if (input[j][y] != null) {
                        st = input[j][y].split("->");
                        LHS = st[0];
                    }
                    if (LHS.equals(RHS) || RHS.indexOf(LHS) != -1) {
                        //System.out.println("LHS = " + LHS + "  RHS = " + RHS);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public String[][] m5_eliminateTransitivity(String[][] input, String[][] jset, int x, int y) {
        //Trim the FDset to remove transitivity FD
        String[] temp = input[x][y].split("->");
        String newFdSet[][] = new String[fdset.length - 1][fdset.length - 1];
        String LHS = temp[0], RHS = temp[1];
        for (int i = 0; i < jset.length; i++) {
            //System.out.println(LHS + " " + jset[i][0] + " " + RHS + " " + jset[i][1]);
            if (LHS.equals(jset[i][0]) && RHS.equals(jset[i][1])) {
                return fdset;
            }
        }
        int cnt = 0;
        for (int i = 0; i < fdset.length; i++) {
            if (!fdset[i][0].equals(LHS) || !fdset[i][1].equals(RHS)) {
                newFdSet[cnt][0] = fdset[i][0];
                newFdSet[cnt][1] = fdset[i][1];
                cnt++;
            }
        }
        //System.out.println("Eliminate Transitivity FD : " + input[x][y]);
        TDvar = TDvar + "Eliminate Transitivity FD : " + input[x][y] +"\n";
        return newFdSet;
    }

    public void m5_printFDset() {
        //Aim: To print out the FDset
        for (int i = 0; i < fdset.length; i++) {
            System.out.print(fdset[i][0] + " -> ");
            System.out.println(fdset[i][1]);
        }
    }

    public void m5_printFDBigset() throws Exception {
        //Aim: To print out the FDBigset
        BigSet bigset;
        FDSet f = new FDSet(fdset, fdset.length);
        Partition p = new Partition();
        bigset = p.works(f);
        Merge m = new Merge();
        bigset = m.works(bigset, f);
        bigset.printBigSet();
    }
    public String m5_getTDvar(){
        return TDvar;
    }

    public String m5_toString() throws Exception {
        //Aim: To print out the FDBigset
        BigSet bigset;
        FDSet f = new FDSet(fdset, fdset.length);
        Partition p = new Partition();
        bigset = p.works(f);
        Merge m = new Merge();
        bigset = m.works(bigset, f);
        return bigset.toString();
    }

    public BigSet m6_displayRelation() throws Exception {
        // 
        LinkedList ll = new LinkedList();
        BigSet bigset;
        FDSet f = new FDSet(fdset, fdset.length);
        Partition p = new Partition();
        bigset = p.works(f);
        Merge m = new Merge();
        bigset = m.works(bigset, f);
        bigset = m6_regrouping(f,bigset, m.getJ());
        ll = bigset.BigSetToRelation();
        for (int i = 0; i < ll.size(); i++) {
            System.out.println(ll.get(i));
        }
        System.out.println();
        return bigset;
    }

    public String m6_toString() throws Exception {
        //
        String res = "";
        LinkedList ll = new LinkedList();
        BigSet bigset;
        FDSet f = new FDSet(fdset, fdset.length);
        Partition p = new Partition();
        bigset = p.works(f);
        Merge m = new Merge();
        bigset = m.works(bigset, f);
        bigset = m6_regrouping(f,bigset, m.getJ());
        ll = bigset.BigSetToRelation();
        for (int i = 0; i < ll.size(); i++) {
            res += (ll.get(i));
            res += "\n";
        }
        res += "\n";
        return res;
    }

    private BigSet m6_regrouping(FDSet f ,BigSet bs, ArrayList<FDSet> listsetj) throws Exception {
        FDAlgorithms fda = new FDAlgorithms();
        for (int i = 0; i < listsetj.size(); i++) {
            Boolean flag = false;
            BitSet closureJ = fda.AttributeClosure(f, listsetj.get(i).getfirstFD().getLeft());
            bs.removeFDSet(listsetj.get(i));
            Iterator it = bs.getBigSet().iterator();
            while (it.hasNext()) {
                FDSet fds = (FDSet) it.next();
                Iterator it2 = fds.getFDs().iterator();
                while (it2.hasNext()) {
                    FD fd = (FD) it2.next();
                    BitSet LHS = fd.getLeft();
                    BitSet closureLHS = fda.AttributeClosure(f, LHS);
                    //System.out.println(closureJ + " " + closureLHS);
                    if(closureJ.equals(closureLHS)){
                        flag = true;
                    }
                    break;
                }
                if(flag){
                    bs.removeFDSet(fds);
                    bs.addFDSet(fds.mergeFDSets(fds, listsetj.get(i)));
                    break;
                }
            }
            if(!flag){
                    bs.addFDSet(listsetj.get(i));
            }
        }
        return bs;
    }

    /*public void test() throws Exception {
    //Aim: 3 set of transitivity test cases
    System.out.println("TestRelation1 = {A -> B, A -> C, B -> C, C -> D, A -> D}");
    String testdata1[][] = {{"A", "B"}, {"A", "C"}, {"B", "C"}, {"C", "D"}, {"A", "D"}};

    fdset = testdata1;
    testdata1 = m5_plotTransitivity();
    m5_indentifyTransitivity(testdata1);
    m5_printFDBigset();
    m6_displayRelation();

    System.out.println("TestRelation2 = {OP -> CD, CD -> OP, " +
    "OP -> A, AO -> B, BP -> C , C -> A}");
    String testdata2[][] = {{"O,P", "C,D"}, {"C,D", "O,P"},
    {"O,P", "A"}, {"A,O", "B"}, {"B,P", "C"}, {"C", "A"}};
    fdset = testdata2;
    testdata2 = m5_plotTransitivity();
    m5_indentifyTransitivity(testdata2);
    m5_printFDBigset();
    m6_displayRelation();

    System.out.println("TestRelation3 = {B -> D, D -> B, A -> B, B -> C, A -> F}");
    String testdata3[][] = {{"B", "D"}, {"D", "B"},
    {"A", "B"}, {"B", "C"}, {"A", "F"}};
    fdset = testdata3;
    testdata3 = m5_plotTransitivity();
    m5_indentifyTransitivity(testdata3);
    m5_printFDBigset();
    m6_displayRelation();
    }*/
}
