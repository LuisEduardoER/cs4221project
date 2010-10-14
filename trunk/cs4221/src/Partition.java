import java.util.*;

public class Partition {
        BigSet bigset = new BigSet();

        ///Convert a FDSet into a BigSet
        public BigSet works(FDSet fdset){

            int x = fdset.getFDs().size();
            boolean[]inserted = new boolean[x];
            for(int i = 0 ; i < x ; i++)inserted[i] = false;

            Iterator it1 = fdset.getFDs().iterator();
	    FD fd1,fd2;

            for(int i = 0 ; i < x ; i++){
                fd1 = (FD)it1.next();
                FDSet newfdset = new FDSet();
                if(!inserted[i]) {
                    newfdset.addFD(fd1);
                    bigset.addFDSet(newfdset);
                    inserted[i] = true;
                }
                Iterator it2 = fdset.getFDs().iterator();
                for(int j = 0 ; j < x ; j++){
                    fd2 = (FD)it2.next();
                    if (!inserted[j]){
                        if(fd2.Leftequals(fd1)){
                            newfdset.addFD(fd2);
                            inserted[j] = true;
                        }
                    }
                 }
            }

            return bigset;
        }
}
