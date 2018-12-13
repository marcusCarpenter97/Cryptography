/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crypto;

import java.math.BigInteger;

/**
 *
 * @author marcus
 */
public class FermatFactorization {

    private BigInteger big;
    private BigInteger fermatsX;
    private BigInteger fermatsY;

    public FermatFactorization() {
    }

    public String callFermat(String bigString) {
                
        try {
            big = new BigInteger(bigString);
        } catch (NumberFormatException bigError) {
            return "Big problem! Only use digits." + bigError;
        }

        bigFermat();
        
        return "Input value: " + big.toString() + "\n"
                + "X = " + fermatsX.toString() + "\n"
                + "Y = " + fermatsY.toString() + "\n"
                + "Check: X * Y = " + checkFermat();
    }

    private void bigFermat() {
        
        BigInteger bigTemp;

        fermatsX = getIntSqrt(big).add(BigInteger.ONE);
        fermatsY = fermatsX.pow(2).subtract(big);

        while (!isBigSquare(fermatsY)) {
            fermatsX = fermatsX.add(BigInteger.ONE);
            fermatsY = fermatsX.pow(2).subtract(big);
        }

        fermatsY = getIntSqrt(fermatsY);
        bigTemp = fermatsY;
        fermatsY = fermatsX.subtract(fermatsY);
        fermatsX = fermatsX.add(bigTemp);

    }

    private boolean isBigSquare(BigInteger N) {
        BigInteger bigRoot = getIntSqrt(N);
        return N.compareTo(bigRoot.multiply(bigRoot)) == 0;
    }
    
    private String checkFermat() {
        
        BigInteger check;
        
        check = fermatsX.multiply(fermatsY);
        
        if (check.compareTo(big) == 0) {
            return check.toString() + " OK!";
        }
        
        return check.toString() + " Error!";
    }

    /* this is my code to help you to work out big integer sqrt - rong */
 /* It returns s where s^2 =< x < (s+1)^2, that is s = floor(sqrt(x)) */
    public static BigInteger getIntSqrt(BigInteger x) {
        BigInteger s; // final result 
        BigInteger currentRes = BigInteger.valueOf(0); // init value is 0
        BigInteger currentSum = BigInteger.valueOf(0); // init value is 0
        BigInteger sum = BigInteger.valueOf(0);
        String xS = x.toString(); // change input x to a string xS

        int lengthOfxS = xS.length();
        int currentTwoBits;
        int i = 0; // index
        if (lengthOfxS % 2 != 0) {// if odd length, add a dummy bit
            xS = "0".concat(xS); // add 0 to the front of string xS
            lengthOfxS++;
        }

        while (i < lengthOfxS) { // go through xS two by two, left to right
            currentTwoBits = Integer.valueOf(xS.substring(i, i + 2));
            i += 2;

            // sum = currentSum*100 + currentTwoBits
            sum = currentSum.multiply(BigInteger.valueOf(100));
            sum = sum.add(BigInteger.valueOf(currentTwoBits));
            // subtraction loop
            do {
                currentSum = sum; // remember the value before subtract
                // in next 3 lines, we work out currentRes = sum - 2*currentRes - 1
                sum = sum.subtract(currentRes);
                currentRes = currentRes.add(BigInteger.valueOf(1)); // currentRes++
                sum = sum.subtract(currentRes);

            } while (sum.compareTo(BigInteger.valueOf(0)) >= 0); // stop when sum < 0
            currentRes = currentRes.subtract(BigInteger.valueOf(1)); // go one step back
            currentRes = currentRes.multiply(BigInteger.valueOf(10));
        }
        s = currentRes.divide(BigInteger.valueOf(10)); // go one step back
        return s;
    }
}
