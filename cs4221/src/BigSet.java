
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

    public String toString() {
        String res="";
        int i = 70;

        Iterator it = this.getBigSet().iterator();
        while (it.hasNext()) {
            res += (new Character((char) i).toString() + " = {");
            i++;
            FDSet fds = (FDSet) it.next();

            Iterator it2 = fds.getFDs().iterator();
            while (it2.hasNext()) {
                FD fd = (FD) it2.next();
                res += (fd.toString());
                if (it2.hasNext()) {
                    res += (", ");
                }
            }

            res += ("}\n");
        }
        return res;
    }

    public int countFD() {
        int a = 0;
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
    
    public int countFDset() {
        int a = 0;
        Iterator it = this.getBigSet().iterator();
        while (it.hasNext()) {
        	FDSet fds = (FDSet) it.next();
                a++;
            }
        return a;
    }

    public LinkedList BigSetToRelation() {
        int i = 0;
        LinkedList ll = new LinkedList();
        Iterator it = this.getBigSet().iterator();
        while (it.hasNext()) {
            String s = "";
            String key = "";
            int j = 0;
            i++;
            FDSet fds = (FDSet) it.next();
            Iterator it2 = fds.getFDs().iterator();
            while (it2.hasNext()) {
                FD fd = (FD) it2.next();
                if (j == 0) {
                    key = fd.leftToString();
                    s = combineAttributes(addDelimiter(fd.leftToString()),fd.rightToString());
                    j++;
                } else {
                    s = combineAttributes(s,fd.leftToString());
                    s = combineAttributes(s,fd.rightToString());
                    if(key.indexOf(fd.leftToString()) == -1){
                        key = key + ", " + fd.leftToString();
                    }
                }
            }
            if (!s.equals("")) {
                s = "R" + i + " = (" + s + ")      key = " + key;
                ll.add(s);
            }else{
                i--;
            }
        }
        return ll;
    }

    public String[][] BigSetToArray(int noOfFD) {
        String[][] bs = new String[noOfFD][2];
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

    public String combineAttributes(String s, String substr){
        for(int i=0; i<substr.length(); i++){
            if(s.indexOf(substr.charAt(i)) == -1){
                s = s + "," + substr.charAt(i);
            }
        }
        return s;
    }

    public String addDelimiter(String s) {
        String newString = "";
        for (int i = 0; i < s.length(); i++) {
            newString = newString + s.charAt(i);
            if (i + 1 != s.length()) {
                newString = newString + ",";
            }
        }
        return newString;
    }
    
    public static void supertest() throws Exception{
    	 String fdinput[][] = {{"A,B","E"},
                 {"A,D","B"},
                 {"A,C","F"},
                 {"A,C","A,D"},
                 {"A,C","A,B"},
                 {"A,B","A,C"},
                 {"A,B","A,D"},
                 {"A,D","A,C"},
                 {"A,D","A,B"}};

                 
    	 String fdinput2[][] = {{"B","C"}};
    	 String fdinput3[][] = {{"C","D"}};
    	 FDSet fdset=new FDSet(fdinput,3);
    	 FDSet fdset2=new FDSet(fdinput2,1);
    	 FDSet fdset3=new FDSet(fdinput3,1);
    	 BigSet bigset= new BigSet();
    	 
    	 bigset.addFDSet(fdset);
    	 bigset.addFDSet(fdset2);
    	 bigset.addFDSet(fdset3);
    	 
    	 BigSet bigset2= new BigSet();
    	 LinkedList<FDSet> listfdset=FDAlgorithms.superFluous(bigset);
    	 LinkedList<FDSet> hope=FDAlgorithms.superFluouscheck(listfdset);
    	 Iterator it = hope.iterator();
    	 while (it.hasNext()) {
    		 FDSet fdsetq = (FDSet)it.next();
    		 bigset2.addFDSet(fdsetq);
    	 }
    	LinkedList ll = new LinkedList();
    	 ll=bigset2.BigSetToRelation();
    	System.out.println(FDAlgorithms.superfluous_tostring(ll));
    	 
    }
    
    public static void main(String[] args) throws Exception{
   	supertest();
    }
    
    
}
