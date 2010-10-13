import java.util.*;

public class FDAlgorithms
{

    //Given a set of attributes as left hand side, find their closure
    // eg. if FDs A->B, B->C
    // then given attributes = A,B as {110}
    // the res = A,B,C as {111}
    //All attributes info are stored as BitSet
    public static BitSet AttributeClosure(FDSet fds, BitSet attributes) {
        //initialize Closure with the given attributes
        //Reflexivity rule
        BitSet Closure = (BitSet)attributes.clone();		
        boolean changes = true;
        while (changes) {
            changes = false;
            for (FD fd : fds.getFDs()) {
                BitSet lhs = (BitSet)fd.getLeft().clone();
                BitSet rhs = (BitSet)fd.getRight().clone();							
                if (lhs.length() > 0) { // if lhs is empty, do nothing	
                    lhs.and(Closure);
                    if (lhs.equals(fd.getLeft())) { //lhs is inside the closure
                        rhs.and(Closure);
                        if (!rhs.equals(fd.getRight())) { //rhs is not inside the closure
                            Closure.or(fd.getRight());
                            changes = true;
                        }
                    }
                }
            }
        }

        return Closure;
    }

    //Perform the member ship test.
    //return true if fd is a member of fds, return false otherwise
    // eg. fds:{A->B, B->C, A,C->F} fd1: A->F fd2: B->F 
    // isMember(fd1,fds) returns true
    // isMember(fd2,fds) returns false
    public static boolean isMember(FD fd, FDSet fds) {
        BitSet left = (BitSet)fd.getLeft().clone();

        BitSet attriClosure = FDAlgorithms.AttributeClosure(fds, left);
        BitSet right = (BitSet)fd.getRight().clone();
        right.and(attriClosure);
        return right.equals(fd.getRight());
    }

    
    
    
    //check if there are more than 1 attribute on rhs
    public static boolean isMultipleRHSAttribute(FD fd) {
    	if (fd.getRight().cardinality()>1)
        return true;
    	else return false;
        }
        
    //method to seperate multiple rhs attributes to different fds
        public static void seperateRHSAttribute(FDSet fdset,FD fd) throws Exception {
        	int size = fd.getLeft().size();
        	String left = fd.leftToStringWithDelimiter();
        	//loop through the attributes on rhs
        	for (int i = fd.getRight().nextSetBit(0); i >= 0; i = fd.getRight().nextSetBit(i+1)) {
        		String right="";
        		char c;
            	int a=65+i;
            	c=(char)a;
            	right+=c;
                FD fd1 = new FD(left,right,size);
                //add the single rhs attribute fd to the fd set
                fdset.addFD(fd1);
           }
        }
        
        //given a set of fds, this method will return a set of fds with only single rhs attributes
        public static FDSet allSingleRHSAttribute(FDSet fdset) throws Exception {
        	FDSet newfdset = new FDSet();
        	Iterator it = fdset.getFDs().iterator();
        	while(it.hasNext())  {
                FD fd=(FD)it.next();
                if (isMultipleRHSAttribute(fd)) 
                	seperateRHSAttribute(newfdset, fd);
                	else
                		newfdset.addFD(fd); 
                }
   return newfdset;
}
        
      //check if there are more than 1 attribute on lhs
        public static boolean isMultipleLHSAttribute(FD fd) {
        	if (fd.getLeft().cardinality()>1)
            return true;
        	else return false;
            }
        
        
        //method to remove the extraneous attributes on lhs, an attribute is extraneous in A, lhs of a fd, if 
        //the attribute is in (A minus attribute) closure on the set of fds. If it is, remove the attribute from lhs
        //and returns the set of fds with that particular fd having no extraneous attribute
        public static FDSet seperateLHSAttribute(FDSet fdset,FD fd) throws Exception {
        	//only for fds with multiple lhs attributes
        	if (isMultipleLHSAttribute(fd)) {
        	int size = fd.getLeft().size();
        	String right = fd.rightToStringWithDelimiter();
        	String left = "";
        	//loop through attributes on lhs
        	for (int i = fd.getLeft().nextSetBit(0); i >= 0; i = fd.getLeft().nextSetBit(i+1)) {
        		BitSet leftfd = (BitSet)fd.getLeft().clone();
        		char c;
            	int a=65+i;
            	c=(char)a;
            	BitSet attribute = FD.ToBitSet(c); 
            	BitSet attribute2 = FD.ToBitSet(c); 
            	leftfd.xor(attribute);
            	//find the closure of (A minus attribute) on the fdset
            	BitSet attriclosure= AttributeClosure(fdset,leftfd);
            	//check if attribute lies in the closure found above
            	attribute.and(attriclosure);
            	//if it does can remove the extraneous attribute from lhs
            	if (attribute.equals(attribute2)) {
               		left+=FD.toStringWithDelimiter(leftfd);
            		 FD fd1 = new FD(left,right,size);
            		 fdset.removeFD(fd);
            		 fdset.addFD(fd1);
            		 //recursive call since fdset is now modified
            		 seperateLHSAttribute(fdset,fd1);
            		 break;
            	}
           }
        	}
        	return fdset;
        }
        
        //given a set of fds, this method will return a set of fds with no extraneous attributes on lhs
        public static FDSet removeExtraneousAttr(FDSet fdset) throws Exception {
        	FDSet newfdset=new FDSet(fdset);
        	Iterator it = fdset.getFDs().iterator();
        	while(it.hasNext())  {
                FD fd=(FD)it.next();
                 newfdset=seperateLHSAttribute(newfdset, fd);
                }
   return newfdset;
}
        //method to check whether a fd is redundant in a set of fds
        //a fd, z, is redundant for a set of fds, F, if closure of lhs attributes of z 
        //on (F minus z) contains the attributes in rhs of z. Returns true or false
        public static boolean checkForRedundancy(FDSet fdset,FD fd) throws Exception {
        	FDSet newfdset=new FDSet(fdset);
        	newfdset.removeFD(fd);
        	BitSet leftfd = (BitSet)fd.getLeft().clone();
        	BitSet rightfd = (BitSet)fd.getRight().clone();
        	BitSet attriclosure= AttributeClosure(newfdset,leftfd);
        	rightfd.and(attriclosure);
        	if (rightfd.equals(fd.getRight()))
        	return true;
        	else return false;
        }
        
        //given a set of fds, return a set where there is no redundant attributes in the set
        public static FDSet removeRedundantFDs(FDSet fdset) throws Exception {
        	FDSet newfdset=new FDSet(fdset);
        	//FDSet newfdset2=new FDSet(fdset);
        	Iterator it = fdset.getFDs().iterator();
        	while(it.hasNext())  {
                FD fd=(FD)it.next();
                 if (checkForRedundancy(newfdset,fd))
                	 newfdset.removeFD(fd); 
                }
        	return newfdset;
        }
        
        
 //superfluous check
        
        public static LinkedList<FDSet> superFluous(BigSet bigset) {
        	LinkedList<FDSet> listfdset= new LinkedList<FDSet>();
        	Iterator it = bigset.getBigSet().iterator();
            for(int i=0;it.hasNext();i++) {
            	FDSet fdset = (FDSet) it.next();
            	listfdset.add(fdset);
            }
        	return listfdset;
        }
        
        public static LinkedList<FDSet> superFluouscheck(LinkedList<FDSet> bigset)throws Exception {
        	boolean restorable=false;
        	boolean nonessential=false;
        	LinkedList<FDSet> listfdset2=(LinkedList<FDSet>)bigset.clone();
        	LinkedList<FDSet> listfdset3=(LinkedList<FDSet>)bigset.clone();
        	Iterator it = listfdset2.iterator();
        	 while(it.hasNext()) {
        		 FDSet fdset = (FDSet) it.next();
        		 BitSet primeattr=getprimeattr(fdset);
        		  
        		 for (int i = primeattr.nextSetBit(0); i >= 0; i = primeattr.nextSetBit(i+1)) {
        			 BitSet marked = new BitSet(26);
        			 marked.set(i);
        			 FDSet gset=createGset(bigset,fdset,marked);
        			 LinkedList<BitSet> keysmarked = getkeysmarked(getkeys(fdset),marked);
        			 LinkedList<BitSet> keyswithoutmarked = getkeyswithoutmarked(getkeys(fdset),marked);
        			 if (!keyswithoutmarked.isEmpty()) {
        				 Iterator it3 = keyswithoutmarked.iterator();
        				 while(it3.hasNext()) {
        					 BitSet tmpkey=(BitSet)it3.next();
        					 BitSet attriclosure = AttributeClosure(gset,tmpkey);
        					 attriclosure.and(tmpkey);
        					 //restorable
        					 if (attriclosure.equals(tmpkey)) {
        						 restorable=true;
        						break;
        					 }}
        				 if (restorable) {
        					 Iterator it4=keysmarked.iterator();
        					 while(it4.hasNext()) {
        						 BitSet tmpkey=(BitSet)it4.next();
        						 BitSet attriclosure = AttributeClosure(gset,tmpkey);
        						 Iterator it5=keyswithoutmarked.iterator();
        						 while(it5.hasNext()) {
        							 BitSet tmpattriclosure = (BitSet)attriclosure.clone();
        							 BitSet tmpkey2=(BitSet)it5.next();
        							 tmpattriclosure.and(tmpkey2);
        							 if (tmpattriclosure.equals(tmpkey2)) {
        								 nonessential=true;
        								 break;
        							 }
        						 }
        						 if (nonessential)
    								 break;
        					 }
        				 } 
        				 }
        			 if (restorable && nonessential) {
        				 listfdset3.remove(fdset);
        			     FDSet fdsettmp= new FDSet(fdset);
        			     Iterator it6=fdsettmp.getFDs().iterator();
        			     while (it6.hasNext()) {
        			    	 FD fd = (FD)it6.next();
        			    	 FD tmpfd= new FD(fd.leftToStringWithDelimiter(),fd.rightToStringWithDelimiter());
        			    	 tmpfd.getRight().and(marked);
        			    	 tmpfd.getLeft().and(marked);
        			    	 if (tmpfd.getLeft().equals(marked) || tmpfd.getRight().equals(marked))
        			    		 fdset.removeFD(fd);
        			     }
        			     listfdset3.add(fdset);
        				 break;
        			 }
        		 }   if (restorable && nonessential) {
        			 superFluouscheck(listfdset3);
        			 break;
        		 }
        	 }
        	 return listfdset3;
        }
        
        
        public static FDSet createGset(LinkedList<FDSet> listfdset,FDSet fdset,BitSet marked) throws Exception {
        	FDSet newfdset = new FDSet();
        	LinkedList<FDSet> tmplist=(LinkedList<FDSet>)listfdset.clone();
        	tmplist.remove(fdset);
        	Iterator it = tmplist.iterator();
            while (it.hasNext()) {
                FDSet fds = (FDSet) it.next();
                Iterator it2 = fds.getFDs().iterator();
                while (it2.hasNext()) {
                    FD fd = (FD) it2.next();
                    newfdset.addFD(fd);
                    } }
        	LinkedList<BitSet> keys = getkeyswithoutmarked(getkeys(fdset),marked);
        	BitSet nonprimeattr = getnonprimeattr(fdset);
        	Iterator it3 = keys.iterator();
        	while (it3.hasNext()) {
        		BitSet bit = (BitSet) it3.next();
        		FD tmpfd = new FD(FD.toStringWithDelimiter(bit),FD.toStringWithDelimiter(nonprimeattr));
        		newfdset.addFD(tmpfd);
        	}
        	return newfdset;
        }
        
        
        
        public static BitSet getprimeattr(FDSet fdset) {
        	BitSet primeattr = FD.ToBitSet('A');
        	primeattr.clear();
        	Iterator it = fdset.getFDs().iterator();
        	while(it.hasNext())  {
        		FD fd=(FD)it.next();
        		BitSet tmp=fd.getLeft();
        		primeattr.or(tmp);
        	}
        	return primeattr;
        }
        
        public static LinkedList<BitSet> getkeys(FDSet fdset) {
        	LinkedList<BitSet> bs = new LinkedList<BitSet>();
        	Iterator it = fdset.getFDs().iterator();
        	while(it.hasNext())  {
        		FD fd=(FD)it.next();
        		BitSet tmp=(BitSet)fd.getLeft().clone();
        		bs.add(tmp);
        	}
        	return bs;
        }
        
        public static LinkedList<BitSet> getkeyswithoutmarked(LinkedList<BitSet> bs, BitSet marked) {
        	LinkedList<BitSet> tmpbs = (LinkedList<BitSet>)bs.clone();
        	BitSet tmp,tmp2 = new BitSet(26);
        	Iterator it = bs.iterator();
        	while(it.hasNext())  {
        		tmp=(BitSet)it.next();
        		tmp2=(BitSet)tmp.clone();
        		tmp2.and(marked);
        		if (tmp2.equals(marked)) {
        			tmpbs.remove(tmp);
        			}
        	}        
        	return tmpbs;
        }
        
        public static LinkedList<BitSet> getkeysmarked(LinkedList<BitSet> bs, BitSet marked) {
        	LinkedList<BitSet> tmpbs = (LinkedList<BitSet>)bs.clone();
        	BitSet tmp,tmp2 = new BitSet(26);
        	Iterator it = bs.iterator();
        	while(it.hasNext())  {
        		tmp=(BitSet)it.next();
        		tmp2=(BitSet)tmp.clone();
        		tmp2.and(marked);
        		if (!tmp2.equals(marked)) {
        			tmpbs.remove(tmp);
        			}
        	}        
        	return tmpbs;
        }
        
        
      
        public static BitSet getnonprimeattr(FDSet fdset) {
        	BitSet leftattr = FD.ToBitSet('A');
        	leftattr.clear();
        	BitSet rightattr = FD.ToBitSet('A');
        	rightattr.clear();
        	Iterator it = fdset.getFDs().iterator();
        	while(it.hasNext())  {
        		FD fd=(FD)it.next();
        		BitSet tmpl=fd.getLeft();
        		BitSet tmpr=fd.getRight();
        		leftattr.or(tmpl);
        		rightattr.or(tmpr);
        	}
        	leftattr.flip(0, leftattr.size());
        	leftattr.and(rightattr);
        	return leftattr;
        }
        
        public static BigSet removeSuperfluous(BigSet bigset) throws Exception {
        	BigSet bigset2= new BigSet();
        	LinkedList<FDSet> listfdset=FDAlgorithms.superFluous(bigset);
       	    LinkedList<FDSet> listfdset2=FDAlgorithms.superFluouscheck(listfdset);
       	    Iterator it = listfdset2.iterator();
    	 while (it.hasNext()) {
    		 FDSet fdsetq = (FDSet)it.next();
    		 bigset2.addFDSet(fdsetq);
    	 }
    	 return bigset2;
        }
        
        public static String superfluous_tostring(LinkedList ll) {
        	String res = "";
        	  for (int i = 0; i < ll.size(); i++) {
                  res += (ll.get(i));
                  res += "\n";
                  System.out.println("!!!");
              }
              res += "\n";
              System.out.println(res);
              return res;
        }
        
}





