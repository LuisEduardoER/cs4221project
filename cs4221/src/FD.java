//import java.io.*;
import java.util.*;
import java.lang.*;

public class FD implements Comparable {
    // instance variables - replace the example below with your own

    private BitSet _BitLeft,  _BitRight; //the BitSet representation of FD
    //private char _CharLeft, _CharRight; //the alphabet representation of FD

    /**
     * Constructor for objects of class FD
     */
    public FD() {
    }
    ;

    //input format: 
    //  left: A, B, C, D
    //  right: A, B, C, D
    public FD(String left, String right) throws Exception {

        //System.out.println(left + " " + right);

        StringTokenizer lefttoken = new StringTokenizer(left, ", ");
        StringTokenizer righttoken = new StringTokenizer(right, ", ");

        if (lefttoken.countTokens() == 0 || righttoken.countTokens() == 0) {
            throw new Exception("left or right is empty!");
        } else {
            _BitLeft = new BitSet();
            _BitRight = new BitSet();
            while (lefttoken.countTokens() > 0) {
                String next = lefttoken.nextToken();
                char c = next.charAt(0);
                BitSet bc = ToBitSet(c);
                _BitLeft.or(bc);
            }
            while (righttoken.countTokens() > 0) {
                String next = righttoken.nextToken();
                char c = next.charAt(0);
                BitSet bc = ToBitSet(c);
                _BitRight.or(bc);
            }
        }
    }

    //input format: 
    //  left: A, B, C, D
    //  right: A, B, C, D
    //max is the maximum of attributes allowed
    public FD(String left, String right, int max) throws Exception {
        StringTokenizer lefttoken = new StringTokenizer(left, ", ");
        StringTokenizer righttoken = new StringTokenizer(right, ", ");
        if (lefttoken.countTokens() == 0 || righttoken.countTokens() == 0) {
            throw new Exception("left or right is empty!");
        } else {
            _BitLeft = new BitSet();
            _BitRight = new BitSet();
            while (lefttoken.countTokens() > 0) {
                String next = lefttoken.nextToken();
                char c = next.charAt(0);
                BitSet bc = ToBitSet(c, max);
                _BitLeft.or(bc);
            }
            while (righttoken.countTokens() > 0) {
                String next = righttoken.nextToken();
                char c = next.charAt(0);
                BitSet bc = ToBitSet(c, max);
                _BitRight.or(bc);
            }
        }
    }


    //obj must be an FD
    public int compareTo(Object fd) {
        return this.toString().compareTo(fd.toString());
    }

    //Convert a char attibute into a BitSet
    //maximum of arrtibutes is 26
    public static BitSet ToBitSet(char a) {
        BitSet bits = new BitSet(26);
        int id = a - 'A';
        bits.flip(id);
        return bits;
    }

    //Convert a char attibute into a BitSet
    //when maximum number of attibutes is specified
    public BitSet ToBitSet(char a, int size) throws Exception {
        BitSet bits = new BitSet(size);
        int id = a - 'A';
        if (id >= size) {
            throw new Exception("Invalid Arribute");
        }
        bits.flip(id);
        return bits;
    }

    public BitSet getLeft() {
        return _BitLeft;
    }

    public BitSet getRight() {
        return _BitRight;
    }

    public boolean Leftequals(FD fd) {
        if (this._BitLeft.equals(fd.getLeft())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean Rightequals(FD fd) {
        if (this._BitRight.equals(fd.getRight())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean equals(FD fd) {
        if (this.Leftequals(fd) && this.Rightequals(fd)) {
            return true;
        } else {
            return false;
        }
    }

    public String leftToString() {
        String left = "";
        int diff;
        char c;
        for (int i = 0; i < _BitLeft.length();) {
            if (i != 0) {
                left += "";
            }
            int setid = _BitLeft.nextSetBit(i);
            diff = setid + 65;
            c = (char) diff;
            left += c;
            i = setid + 1;
        }
        return left;
    }

    public String leftToStringWithDelimiter() {
        String left = "";
        int diff;
        char c;
        for (int i = 0; i < _BitLeft.length();) {
            if (i != 0) {
                left += "";
            }
            int setid = _BitLeft.nextSetBit(i);
            diff = setid + 65;
            c = (char) diff;
            left += c;
            left += ',';
            i = setid + 1;
        }
        return left;
    }

    public static String toStringWithDelimiter(BitSet bitset) {
        String str = "";
        int diff;
        char c;
        for (int i = 0; i < bitset.length();) {
            if (i != 0) {
                str += "";
            }
            int setid = bitset.nextSetBit(i);
            diff = setid + 65;
            c = (char) diff;
            str += c;
            str += ',';
            i = setid + 1;
        }
        return str;
    }

    public String rightToString() {
        String right = "";
        int diff;
        char c;
        for (int i = 0; i < _BitRight.length();) {
            if (i != 0) {
                right += "";
            }
            int setid = _BitRight.nextSetBit(i);
            diff = setid + 65;
            c = (char) diff;
            right += c;
            i = setid + 1;
        }
        return right;
    }

    public String rightToStringWithDelimiter() {
        String right = "";
        int diff;
        char c;
        for (int i = 0; i < _BitRight.length();) {
            if (i != 0) {
                right += "";
            }
            int setid = _BitRight.nextSetBit(i);
            diff = setid + 65;
            c = (char) diff;
            right += c;
            right += ',';
            i = setid + 1;
        }
        return right;
    }

    public String toString() {
        return (this.leftToString() + " -> " + this.rightToString());
    }

    public void printFD() {
        System.out.println(this.toString());
    }

    public static void test() throws Exception {

        FD fd1 = new FD("A,B,G", "F,E", 7);
        System.out.println("leftToString() output: " + fd1.leftToString());
        System.out.println("RightttoString() output: " + fd1.rightToString());
        System.out.println("toString() output: " + fd1.toString());
        fd1.printFD();

        FD fd2 = new FD("A,B,C,D", "E");
        FD fd3 = new FD("A,C,D", "E,F,G");
        FD fd4 = new FD("A,B,C,D", "E,F,G");
        FD fd5 = new FD("A,B,C", "E,F");
        FD fd6 = new FD("A,B,C", "F,G");
        fd2.printFD();
        fd3.printFD();
        fd4.printFD();
        fd5.printFD();
        fd6.printFD();
        if (fd2.Leftequals(fd1)) {
            System.out.println("Left of fd2 equals left of fd1");
        }
        if (fd3.Rightequals(fd1)) {
            System.out.println("Right of fd3 equals Right of fd1");
        }
        if (fd4.equals(fd1)) {
            System.out.println("Fd4 equals fd1");
        }
        if (fd5.compareTo(fd1) > 0) {
            System.out.println("fd3 > fd1");
        } else if (fd3.compareTo(fd1) == 0) {
            System.out.println("fd3 > fd1");
        } else {
            System.out.println("fd3 < fd2");
        }
    }

    public static void main(String[] args) throws Exception {
        test();
    }
}
