import java.util.*;

public class BigSet {
    private Set<FDSet> _bs;

    //basic constructor
    public BigSet() {
    	 _bs = new TreeSet<FDSet>();
    }

    public BigSet(FDSet fdset){
         _bs = new TreeSet<FDSet>();
         _bs.add(new FDSet(fdset));
    }

    public Set<FDSet> getBigSet (){
        return _bs;
    }

    public void addFDSet(FDSet fdset) {
    	_bs.add(fdset);
    }

    public void removeFDSet(FDSet fdset) {
    	_bs.remove(fdset);
    }

    public void printBigSet() {
        int i = 70;

        Iterator it = this.getBigSet().iterator();
        while(it.hasNext()){
            System.out.print(new Character((char)i).toString() + " = {");
            i++;
            FDSet fds =(FDSet)it.next();

            Iterator it2 = fds.getFDs().iterator();
	    while(it2.hasNext()){
                FD fd=(FD)it2.next();
	        System.out.print(fd.toString());
                if(it2.hasNext()) System.out.print(", ");
            }

            System.out.println("}");
	}
    }
}
