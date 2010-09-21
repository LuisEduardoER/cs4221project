import java.util.*;
/**
 * Write a description of class FD here.
 * 
 * @author (Cui Zheng, please add your name here) 
 * @version (19-9-2010)
 */
public class FDSet
{
    // instance variables - replace the example below with your own
    private Set<FD> _fds;

    //construct the functioanl dependency set based on string array input
    public FDSet(String fds[][], int max){
        _fds = new TreeSet<FD>();
        for(int i=0; i<max; i++){
            _fds.add(new FD(fds[i][0],fds[i][1]));
        }
    }

    //construct a fdset based on another fdsed
    public FDSet(FDSet oldfds){
        _fds = new TreeSet<FD>(oldfds.getFDs());
    }

    public Set<FD> getFDs (){
        return _fds;
    }

    public static void test(){
        int max_fd = 7;
        String fdinput[][] = {{"A,B,C,D","E,F,G"},
                {"A,B,C,D","E"},
                {"A,C,D","E,F,G"},
                {"A","C"},
                {"C","D"},
                {"A,B,C,D","E,F,G"}, //duplicate, thus will not actually add into set
                {"A,B","E,F,G,H"}};
        FDSet fdset = new FDSet(fdinput, max_fd);

        Iterator it = fdset.getFDs().iterator();

        while(it.hasNext()){
            FD fd=(FD)it.next();
            fd.printFD();
        }
        FD fd1 = new FD("A","G");
        FD fd2 = new FD("B","G");
        fd1.printFD();
        fd2.printFD();
        if(FDAlgorithms.isMember(fd1, fdset)) {
            System.out.print("fd1: ");
            fd1.printFD();
            System.out.println("is a member of fdset");
        }
        else {
            System.out.print("fd1: ");
            fd1.printFD();
            System.out.println("is NOT a member of fdset");
        }
        if(FDAlgorithms.isMember(fd2, fdset)) {
            System.out.print("fd2: ");
            fd2.printFD();
            System.out.println("is a member of fdset");
        }
        else{
            System.out.print("fd2: ");
            fd2.printFD();
            System.out.println("is NOT a member of fdset");
        }
    }
}
