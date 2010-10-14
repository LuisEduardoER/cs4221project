import java.util.*;

public class Merge {

    ArrayList<FDSet> J = new ArrayList<FDSet>();

    //Merge a few FDSet with the same attributes closure
    public BigSet works(BigSet b,FDSet myfdset) throws Exception{
        BigSet newB = new BigSet();

        Iterator it1 = b.getBigSet().iterator();

        ArrayList<FDSet> fds = new ArrayList<FDSet>();
        ArrayList<FD> closure = new ArrayList<FD>();

        while(it1.hasNext()){
            FDSet fds1 =(FDSet)it1.next();
            fds.add(fds1);
            Iterator it2 = fds1.getFDs().iterator();
            FD fd1 =(FD)it2.next();
            BitSet fd1LeftBitSet = fd1.getLeft();
            BitSet temp = FDAlgorithms.AttributeClosure(myfdset, fd1LeftBitSet);
            closure.add(new FD(FD.toStringWithDelimiter(temp),fd1.leftToStringWithDelimiter()));
        }

        Collections.sort(closure);
        Collections.sort(fds);

        int a = closure.size();
        String [] s = new String[a];
        for(int i = 0;i < closure.size();i++){
            s[i] = closure.get(i).leftToString();
        }

        ArrayList<String> store = new ArrayList<String>();
        String ref = s[0];
        store.add(closure.get(0).rightToString());
        for(int i = 1;i < closure.size();i++){
            
            if(s[i].equals(ref))store.add(closure.get(i).rightToString());

            if(!s[i].equals(ref) || i == closure.size()-1){
                ref = s[i];
                if(store.size() > 1){
                    FDSet newfds = new FDSet();
                    for(int m = 0 ; m < store.size() ; m++){
                        for(int n = 0 ; n < store.size() ; n++){
                            if(m < n){
                                newfds = buildJ(newfds,store.get(m),store.get(n));
                            }
                        }
                    }

                    FDSet newUnionH = new FDSet();
                    for(int n = 0 ; n < store.size() ; n++){
                        for(int m = 0;m < fds.size();m++){
                            Iterator i3 = fds.get(m).getFDs().iterator();
                            FD fd = (FD)i3.next();
                            if(fd.leftToString().equals(store.get(n))){
                                newUnionH = buildH(newUnionH,newfds,fds.get(m));
                            }
                        }
                    }

                for(int m = store.size()-1 ; m >= 0 ; m--)store.remove(m);

                J.add(newfds);
                newB.addFDSet(newfds);
                newB.addFDSet(newUnionH);

                if(i != closure.size()-1)store.add(closure.get(i).rightToString());

            }
            else{
                for(int m = 0;m < fds.size();m++){
                    Iterator i3 = fds.get(m).getFDs().iterator();
                    FD fd = (FD)i3.next();
                    if(fd.leftToString().equals(store.get(0))){
                        newB.addFDSet(fds.get(m));
                        break;
                    }
                }
                    store.remove(0);

                store.add(closure.get(i).rightToString());

                if(i == closure.size()-1){
                   for(int m = 0;m < fds.size();m++){
                    Iterator i3 = fds.get(m).getFDs().iterator();
                    FD fd = (FD)i3.next();
                    if(fd.leftToString().equals(store.get(0))){
                        newB.addFDSet(fds.get(m));
                        break;
                    }
                }
                    store.remove(0);
                }
            }
                
            }
        }

        return newB;
    }

     //Build J = {x->y, y->x}
     public FDSet buildJ(FDSet newfds,String x,String y) throws Exception{
        FD addition1 = new FD(addDelimiter(x),addDelimiter(y));
        FD addition2 = new FD(addDelimiter(y),addDelimiter(x));

        newfds.addFD(addition1);
        newfds.addFD(addition2);

        return newfds;
     }

     //Build Union H = Hi - J;
     public FDSet buildH(FDSet newUnionH,FDSet newfds,FDSet fds1) throws Exception{

        newfds = FDAlgorithms.allSingleRHSAttribute(newfds);    //J
        fds1 = FDAlgorithms.allSingleRHSAttribute(fds1);        //H1

        Iterator it5 = fds1.getFDs().iterator();

        while(it5.hasNext()) newUnionH.addFD((FD)it5.next());

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
        return newUnionH;
     }

     //Add delimiter "," to a string
     public String addDelimiter(String str){
        String left = "";
        char c;
        for(int i = 0; i<str.length();i++){
            if(i != 0) left += "";
            c = str.charAt(i);
            left += c;
            left +=',';
        }
        return left;
    }

    ///Get the all the J's created in this step as an array List
     public ArrayList<FDSet> getJ(){
         return J;
     }

     ///Get the all the J's created in this step as an array
     public String[][] getArrayJ(){
         String[][]fds = new String[10][2];

         int index = 0;

         if(J.size() > 0){
             for(int i = 0 ; i < J.size() ; i++){
                 Iterator itr = J.get(i).getFDs().iterator();
                 while(itr.hasNext()){
                     FD fd = (FD) itr.next();
                     fds[index][0] = addDelimiter2(fd.leftToString());
                     fds[index][1] = addDelimiter2(fd.rightToString());
                     index++;
                 }
             }
         }

         return fds;
     }
     public String addDelimiter2(String s) {
        String newString = "";
        for (int i = 0; i < s.length(); i++) {
            newString = newString + s.charAt(i);
            if (i + 1 != s.length()) {
                newString = newString + ",";
            }
        }
        return newString;
    }
}
