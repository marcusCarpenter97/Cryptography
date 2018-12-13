/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crypto;

/**
 *
 * @author marcus
 */
public class BCH {
    
    public final int m;
    public final ModularArithmetic ma;
    
    public BCH() {
        m = 11;
        ma = new ModularArithmetic();
    }
    
    //BCH(10,6) decoder
    public String bchDecoder(String s) {
        
        int[] n = convertToInt(s);
        int s1, s2, s3, s4;
        int p, q, r;
        int magnitude, position;
        int root, i, j, a, b, temp;
        
        if (n[0] == -1 || n.length != 10){
            return "Input must be 10 integers!";
        }
        
        s1= ma.xModY((n[0]+n[1]+n[2]+n[3]+n[4]+n[5]+n[6]+n[7]+n[8]+n[9]), m);
        s2= ma.xModY((n[0]+2*n[1]+3*n[2]+4*n[3]+5*n[4]+6*n[5]+7*n[6]+8*n[7]+9*n[8]+10*n[9]), m);
        s3= ma.xModY((n[0]+4*n[1]+9*n[2]+5*n[3]+3*n[4]+3*n[5]+5*n[6]+9*n[7]+4*n[8]+n[9]), m);
        s4= ma.xModY((n[0]+8*n[1]+5*n[2]+9*n[3]+4*n[4]+7*n[5]+2*n[6]+6*n[7]+3*n[8]+10*n[9]), m);
        
        //no error
        if(s1 + s2 + s3 + s4 == 0) {
            return toString(n) + "\nString is OK!";
        }
        
        p = ma.xModY((s2 * s2 - s1 * s3), m);
        q = ma.xModY((s1 * s4 - s2 * s3), m);
        r = ma.xModY((s3 * s3 - s2 * s4), m);
        
        //one error 
        if(p + q + r == 0) {
            magnitude = s1;
            position = ma.divide(s2, s1, m);
            
            if(position == 0) {
                return errMessage(s1, s2, s3, s4, p, q, r);
            }
            
            n[position-1] = ma.subtract(n[position-1], magnitude, m);
            
            if(n[position-1] > 9) {
                return errMessage(s1, s2, s3, s4, p, q, r);
            }
            
            return "One error found!" +
                   "\nSyndromes: " + s1 + ", " + s2 + ", " + s3 + ", " + s4 +
                   "\nPosition: " + position + 
                   "\nMagnitude: " + magnitude + 
                   "\nCorrected to: " + toString(n);
        }
        
        //two errors
        /*
        position
        i = (- Q + √(Q2-4*P*R)) / 2*P
	j = (- Q -  √(Q2-4*P*R)) / 2*P
        magnitude
	b = (i*s1- s2) / (i - j)
	a = s1 – b
        */
        
        //calc positions
        //root = sqrMod11(subtract(multiply(q,q), multiply(multiply(p,q), 4)));
        root = sqrMod11(ma.xModY((q * q - 4 * p * r), m));
        
        if(root == -1){
            return errMessage(s1, s2, s3, s4, p, q, r);
        }
        
        i = ma.add(-q, root, m);
        j = ma.subtract(-q, root, m);
        temp = ma.multiply(p, 2, m);
        i = ma.divide(i, temp, m);
        j = ma.divide(j, temp, m);
        
        if(i == 0 || j == 0){
            return errMessage(s1, s2, s3, s4, p, q, r);
        }
        
        //calc magnitude
        b = ma.divide(ma.subtract(i*s1, s2, m), ma.subtract(i, j, m), m);
        a = ma.subtract(s1, b, m);
        
        n[i-1] = ma.subtract(n[i-1], a, m);
        n[j-1] = ma.subtract(n[j-1], b, m);
        
        if(n[i-1] > 9 || n[j-1] > 9){
            return errMessage(s1, s2, s3, s4, p, q, r);
        }
        
        return "Two errors found!" + 
               "\nSyndromes: " + s1 + ", " + s2 + ", " + s3 + ", " + s4 +
               "\nPqr: " + p + ", " + q + ", " + r +
               "\nPositions:  i=" + i + " j=" + j +
               "\nMagnitudes: a=" + a + " b=" + b +
               "\nCorrected to: " + toString(n);
    }
    
    //BCH(10,6) generator
    public String bchGenerator(String s){
        int[] n = convertToInt(s);
        int d7, d8, d9, d10;
        
        if (n[0] == -1 || n.length != 6){
            return "Input must be 6 integers!";
        }
        
        d7 = ma.xModY((4*n[0]+10*n[1]+9*n[2]+2*n[3]+n[4]+7*n[5]), m);
        d8 = ma.xModY((7*n[0]+8*n[1]+7*n[2]+n[3]+9*n[4]+6*n[5]), m);
        d9 = ma.xModY((9*n[0]+n[1]+7*n[2]+8*n[3]+7*n[4]+7*n[5]), m);
        d10 = ma.xModY((n[0]+2*n[1]+9*n[2]+10*n[3]+4*n[4]+n[5]), m);
        
        if (d7 > 9 || d8 > 9 || d9 > 9 || d10 > 9){
            return "Unusable number!";
        }
                
        return "" + n[0] + n[1] + n[2] + n[3] + n[4] + n[5] + d7 + d8 + d9 + d10;
    }
    
    //lookup table for square root under mod 11
    public int sqrMod11(int n) {
        switch(n){
            case 1:
                return 1;
            case 3:
                return 5;
            case 4:
                return 2;
            case 5:
                return 4;
            case 9:
                return 3;
            default:
                return -1;
        }
    }
    
    public String errMessage(int s1, int s2, int s3, int s4, int p, int q, int r){
        return  "More than two errors found! Errors not fixed!" +
                "\nSyndromes: " + s1 + ", " + s2 + ", " + s3 + ", " + s4 +
                "\nPqr: " + p + ", " + q + ", " + r;
    }
    
    //String to int
    public int[] convertToInt(String s){
        int i, test;
        int size = s.length();
        int[] arr = new int[size];
        int[] err = {-1};
        
        try{
            for (i = 0; i < size; i++) {
                arr[i] = Integer.parseInt(String.valueOf(s.charAt(i)));
            }
            
            test = arr[0];
            
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return err;
        }
        return arr;
    }
    
    //int[] to string
    public String toString(int[] n){
        int i;
        String ret = "";
        
        for(i = 0; i < n.length; i++){
            ret += n[i];
        }
        
        return ret;
    }

//    public int add(int x, int y) {
//        return mod((x + y));
//    }
//
//    public int subtract(int x, int y) {
//        return ((mod(x) - mod(y)) + m) % m;
//    }
//
//    public int multiply(int x, int y) {
//        return mod((x * y));
//    }
//
//    public int divide(int x, int y) {
//        int inverseY = inverse(y, m); 
//        return multiply(x, inverseY);
//    }
//
//    public int mod(int x) {
//        return ((x % m) + m) % m;
//    }
//    
//    public int inverse(int a, int n) {
//        int t = 0;
//        int newt = 1;
//        int r = n;
//        int newr = a;
//        int q, temp;
//        while (newr != 0) {
//            q = r / newr; /* integer division */
//            temp = newt;  /* remember newt    */
//            newt = t - q * newt;
//            t = temp;
//            temp = newr;  /* remember newr    */
//            newr = r - q * newr;
//            r = temp;
//        }
//        if (r > 1) {
//            return -1; /* not invertible */
//        }
//        if (t < 0) {
//            t = t + n; /* change to positive */
//        }
//        return t;
//    }

}
