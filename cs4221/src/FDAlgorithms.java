import java.util.*;
/**
 * Write a description of class FD here.
 * 
 * @author (Cui Zheng, please add your name here) 
 * @version (19-9-2010)
 */
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

}
