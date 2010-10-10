import java.util.*;

public class Merge {

    public BigSet works(BigSet b){

        boolean changed = true;

        while(changed){
            changed = false;
        Iterator it1 = b.getBigSet().iterator();
        
        while(it1.hasNext() && !changed){
            FDSet fds1 =(FDSet)it1.next();

            Iterator it2 = fds1.getFDs().iterator();
	    while(it2.hasNext() && !changed){
                FD fd1 =(FD)it2.next();
                
                //System.out.println("fd1 " + fd1.toString());

                Iterator it3 = b.getBigSet().iterator();
                while(it3.hasNext() && !changed){
                    FDSet fds2 =(FDSet)it3.next();

                    if(fds1.equals(fds2))break;
                    Iterator it4 = fds2.getFDs().iterator();
                    while(it4.hasNext() && !changed){
                        FD fd2 =(FD)it4.next();

                        //System.out.println("fd2 " + fd2.toString());

                        if(this.checkPE(fd1,fd2)){
                            FDSet newfds = new FDSet();
                            newfds.addFD(fd1);
                            newfds.addFD(fd2);
                            b.addFDSet(newfds);

                            fds1.removeFD(fd1);
                            if(fds1.getFDs().isEmpty())b.removeFDSet(fds1);
                            fds2.removeFD(fd2);
                            if(fds2.getFDs().isEmpty())b.removeFDSet(fds2);
                            changed = true;

                            //b.printBigSet();

                        }
                    }
                }
            }
        }
        }
        return b;
    }

    public boolean checkPE(FD fd,FD ifd){
        if(fd.getLeft().equals(ifd.getRight()) &&
           fd.getRight().equals(ifd.getLeft())    )
            return true;
        else return false;
    }
}
