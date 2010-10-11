import java.util.*;

public class Merge {

    public BigSet works(BigSet b,FDSet myfdset) throws Exception{

        boolean changed = true;
        BigSet newB = new BigSet();

        while(changed){
            changed = false;
            Iterator it1 = b.getBigSet().iterator();
        
            while(it1.hasNext() && !changed){
                FDSet fds1 =(FDSet)it1.next();

                Iterator it2 = fds1.getFDs().iterator();
                FD fd1 =(FD)it2.next();
                BitSet fd1LeftBitSet = fd1.getLeft();
                BitSet closure1 = FDAlgorithms.AttributeClosure(myfdset, fd1LeftBitSet);

                Iterator it3 = b.getBigSet().iterator();
                while(it3.hasNext() && !changed){
                    FDSet fds2 =(FDSet)it3.next();

                    Iterator it4 = fds2.getFDs().iterator();
                    FD fd2 =(FD)it4.next();
                    BitSet fd2LeftBitSet = fd2.getLeft();
                    BitSet closure2 = FDAlgorithms.AttributeClosure(myfdset, fd2LeftBitSet);

                    if(closure1.equals(closure2) && !fds1.equals(fds2)){

                        //System.out.println(closure1.toString() + "  " + closure2.toString());
                        //fd1.printFD();
                        //fd2.printFD();

                        FDSet newfds = new FDSet();

                        newfds.addFD(new FD(fd1.leftToStringWithDelimiter(),fd2.leftToStringWithDelimiter()));
                        newfds.addFD(new FD(fd2.leftToStringWithDelimiter(),fd1.leftToStringWithDelimiter()));

                        newB.addFDSet(newfds);
                        //System.out.println("newB after addimg newfds");
                        //newB.printBigSet();

                        b.removeFDSet(fds1);
                        b.removeFDSet(fds2);
                        //System.out.println("b after remove fds1-2");
                        //b.printBigSet();
                        
                        newfds = FDAlgorithms.allSingleRHSAttribute(newfds);    //J
                        fds1 = FDAlgorithms.allSingleRHSAttribute(fds1);        //H1
                        fds2 = FDAlgorithms.allSingleRHSAttribute(fds2);        //H2

                        Iterator it5 = fds1.getFDs().iterator();
                        Iterator it6 = fds2.getFDs().iterator();

                        FDSet newUnionH = new FDSet();
                        while(it5.hasNext()) newUnionH.addFD((FD)it5.next());
                        while(it6.hasNext()) newUnionH.addFD((FD)it6.next());

                        Iterator it7 = newfds.getFDs().iterator();
                        boolean removed = false;

                        while(it7.hasNext()){

                            FD temp1 = (FD)it7.next();
                            Iterator it8 = newUnionH.getFDs().iterator();
                            removed = false;

                            while(it8.hasNext() && !removed){

                                FD temp2 = (FD)it8.next();

                                if(temp1.equals(temp2)) {
                                    newUnionH.removeFD(temp2);
                                    removed = true;
                                }
                            }
                        }
                        //newUnionH.printFDSet();
                        newB.addFDSet(newUnionH);
                        //System.out.println("newB after addimg newUnionH");
                        //newB.printBigSet();
                        changed = true;

                    }
                    else if(!it3.hasNext()){
                        //System.out.println("newB in else");
                        newB.addFDSet(fds1);
                        //newB.printBigSet();
                    }
                }
            }
        }
        return newB;
    }

    public boolean checkPE(FD fd,FD ifd){
        if(fd.getLeft().equals(ifd.getRight()) &&
           fd.getRight().equals(ifd.getLeft())    )
            return true;
        else return false;
    }
}
