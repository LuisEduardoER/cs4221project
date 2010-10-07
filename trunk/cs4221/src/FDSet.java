import java.util.*;
import java.lang.*;

public class FDSet implements Comparable
{
    // instance variables - replace the example below with your own
    private Set<FD> _fds;

    //basic constructor
    public FDSet() {
    	 _fds = new TreeSet<FD>();
    }
    //construct the functional dependency set based on string array input
    public FDSet(String fds[][], int max)throws Exception{
        
        //System.out.println(fds[0][0] + fds[0][1]);
        
        _fds = new TreeSet<FD>();
        for(int i=0; i<max; i++){
            if(!fds[i][0].isEmpty() && !fds[i][1].isEmpty())
            _fds.add(new FD(fds[i][0],fds[i][1]));
        }
    }

    //construct a fdset based on another fdsed
    public FDSet(FDSet oldfds){
        _fds = new TreeSet<FD>(oldfds.getFDs());
    }


    //obj must be an FD
    public int compareTo(Object fdset){
        return this.toString().compareTo(fdset.toString());
    }

    public Set<FD> getFDs (){
        return _fds;
    }
    
    public void addFD(FD fd) {
    	_fds.add(fd);
    }
    
    public void removeFD(FD fd) {
    	_fds.remove(fd);
    }
    
    public void printFDSet() {
    	 Iterator it = this.getFDs().iterator();
	        while(it.hasNext()){
	            FD fd=(FD)it.next();
	            fd.printFD();
	        }
    }
    
    
    public static void test()throws Exception{
        int max_fd = 7;
        String fdinput[][] = {{"A","B"},
                {"A","C"},
                {"B","C"},
                {"D","B"},
                {"B","D"},
                {"A,B,E","F"}, //duplicate, thus will not actually add into set
                {"A,B,C","A,B"}};
        
        //Step 1 and 2 test
        //this should be the order to obtain the minimal cover 
        FDSet fdset = new FDSet(fdinput, max_fd);
        Iterator it3 = fdset.getFDs().iterator();
        while(it3.hasNext()){
            FD fd=(FD)it3.next();
            fd.printFD();
        }
        System.out.println("Above is the original set of fds"); 
        System.out.println();
        
        fdset=FDAlgorithms.allSingleRHSAttribute(fdset);
        Iterator it2 = fdset.getFDs().iterator();
        while(it2.hasNext()){
            FD fd=(FD)it2.next();
            fd.printFD();
        }
        System.out.println("Above is set with all singleton rhs attributes"); 
        System.out.println();
        
        fdset=FDAlgorithms.removeExtraneousAttr(fdset);
        Iterator it = fdset.getFDs().iterator();
        while(it.hasNext()){
            FD fd=(FD)it.next();
            fd.printFD();
        }
       System.out.println("Above is set with no extraneous attributes on lhs"); 
       System.out.println();
       
       fdset=FDAlgorithms.removeRedundantFDs(fdset);
       Iterator it4 = fdset.getFDs().iterator();
       while(it4.hasNext()){
           FD fd=(FD)it4.next();
           fd.printFD();
       }
      System.out.println("Above is set with no redundant attributes");  
      System.out.println();
      
      
        FD fd1 = new FD("B","C");
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
    public static void main(String[] args) throws Exception{
        test();
        }
        
    
}
