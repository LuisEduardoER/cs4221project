
import java.util.*;

public class BigSet {

    public Set<FDSet> _bs;

    //basic constructor
    public BigSet() {
        _bs = new TreeSet<FDSet>();
    }

    public BigSet(FDSet fdset) {
        _bs = new TreeSet<FDSet>();
        _bs.add(new FDSet(fdset));
    }

    public Set<FDSet> getBigSet() {
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
        while (it.hasNext()) {
            System.out.print(new Character((char) i).toString() + " = {");
            i++;
            FDSet fds = (FDSet) it.next();

            Iterator it2 = fds.getFDs().iterator();
            while (it2.hasNext()) {
                FD fd = (FD) it2.next();
                System.out.print(fd.toString());
                if (it2.hasNext()) {
                    System.out.print(", ");
                }
            }

            System.out.println("}");
        }
    }
    
    public int countFD() {
    	int a=0;
    	   Iterator it = this.getBigSet().iterator();
           while (it.hasNext()) {
               FDSet fds = (FDSet) it.next();
              
               Iterator it2 = fds.getFDs().iterator();
               while (it2.hasNext()) {
            	   FD fd = (FD) it2.next();
                   a++;
                   }
           }
           
         return a;
    }
    
    public LinkedList BigSetToRelation() {
        int i = 0;
        LinkedList ll = new LinkedList();
        Iterator it = this.getBigSet().iterator();
        while (it.hasNext()) {
            String s = "";
            int j = 0;
            i++;
            FDSet fds = (FDSet) it.next();
            Iterator it2 = fds.getFDs().iterator();
            while (it2.hasNext()) {
                FD fd = (FD) it2.next();
                if( j == 0){
                    s = addDelimiter(fd.leftToString()) + "," +addDelimiter(fd.rightToString());
                    j++;
                }else{
                    if(s.indexOf(addDelimiter(fd.leftToString())) != -1
                            && s.indexOf(addDelimiter(fd.rightToString())) != -1){
                        //mark both as key
                    }else if(s.indexOf(addDelimiter(fd.leftToString())) != -1
                            && s.indexOf(addDelimiter(fd.rightToString())) == -1){
                        s = s + "," + fd.rightToString();
                    }
                }
            }
            s = "R" + i + " = (" + s + ")";
            ll.add(s);
        }
        return ll;
    }
    public String[][] BigSetToArray(int noOfFD) {
        String[][] bs =new String[noOfFD][2];
        int i = 0;
        Iterator it = this.getBigSet().iterator();
        while (it.hasNext()) {
            FDSet fds = (FDSet) it.next();
            Iterator it2 = fds.getFDs().iterator();
            while (it2.hasNext()) {
                FD fd = (FD) it2.next();
                bs[i][0] = addDelimiter(fd.leftToString());
                bs[i][1] = addDelimiter(fd.rightToString());
                i++;
            }
        }
        return bs;
    }
    public String addDelimiter(String s){
        String newString = "";
        for(int i=0; i<s.length(); i++){
            newString = newString + s.charAt(i);
            if( i+1 != s.length()){
                newString = newString + ",";
            }
        }
        return newString;
    }
}
