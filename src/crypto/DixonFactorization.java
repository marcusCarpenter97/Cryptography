/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crypto;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author marcus
 */
class CandidatePair {

    private BigInteger bigCandidate;
    private int[] powerList;

    public CandidatePair() {
    }

    public BigInteger getBigCandidate() {
        return bigCandidate;
    }

    public void setBigCandidate(BigInteger bigCandidate) {
        this.bigCandidate = bigCandidate;
    }

    public int[] getPowerList() {
        return powerList;
    }

    public void setPowerList(int[] powerList) {
        this.powerList = powerList;
    }

    public int getPowerIndividualFromList(int i) {
        return powerList[i];
    }

    public boolean isPowerListEven() {

        int i;

        for (i = 0; i < powerList.length; i++) {

            if (powerList[i] % 2 != 0) {
                return false;
            }
        }

        return true;
    }
}

public class DixonFactorization {

    private BigInteger big;
    private BigInteger dixonsX;
    private BigInteger dixonsY;
    private final int[] base;
    private int[] baseCounter;
    private final int bLen;
    private int evenPointer;
    private int[] evenPowerList;

    public DixonFactorization() {

        base = new int[]{2, 3, 5, 7};
        baseCounter = new int[]{0, 0, 0, 0};
        bLen = base.length;
        evenPowerList = new int[]{0, 0, 0, 0};
        evenPointer = 0;

    }

    public String callDixon(String s) {

        big = new BigInteger(s);
        
        bigDixon();

        return "Input value: " + big.toString() + "\n"
                + "X = " + dixonsX.toString() + "\n"
                + "Y = " + dixonsY.toString() + "\n"
                + "Check: X * Y = " + checkDixon();
    }

    private void bigDixon() {

        BigInteger bigCandidate;
        BigInteger rndCandidate;
        BigInteger bigX;
        BigInteger bigY;

        List<CandidatePair> listOfCandidatePairs = new ArrayList<>();
        CandidatePair candidatePair = new CandidatePair();

        boolean squareFound = false;

        BigInteger bigRoot = FermatFactorization.getIntSqrt(big);
        
        while (true) {

            // Generate random BigInteger between sqrt(big) to big - 1
            do {
                rndCandidate = new BigInteger(big.bitLength(), new Random());
            } while (rndCandidate.compareTo(bigRoot) >= 0 && rndCandidate.compareTo(big) >= 0);

            // bigCandidate = rndCandidate ^ 2 % big
            bigCandidate = rndCandidate.modPow(BigInteger.valueOf(2), big);

            if (calculateBSmoothPowerSet(bigCandidate)) {
                candidatePair.setBigCandidate(rndCandidate);
                candidatePair.setPowerList(baseCounter);

                // Check for square number
                if (candidatePair.isPowerListEven()) {
                    squareFound = true;
                    break;

                } else if (doesNewPairMakeEven(candidatePair, listOfCandidatePairs)) {
                    break;

                } else {
                    listOfCandidatePairs.add(candidatePair);
                }
            }
            // Reset for next attempt
            candidatePair = new CandidatePair();
//            rndCandidate = rndCandidate.add(BigInteger.ONE);
        }

        // if candidate pair is square
        if (squareFound) {
            bigX = candidatePair.getBigCandidate();
            evenPowerList = candidatePair.getPowerList();

        } else {
            bigX = listOfCandidatePairs.get(evenPointer).getBigCandidate();
            bigX = bigX.multiply(candidatePair.getBigCandidate());
        }

        bigY = simplifyPowerList();

        dixonsX = bigX.subtract(bigY).gcd(big);
        dixonsY = bigX.add(bigY).gcd(big);
    }

    private boolean calculateBSmoothPowerSet(BigInteger n) {

        int i;

        baseCounter = new int[bLen];

        // go through each base
        for (i = 0; i < bLen; i++) {

            // while bigCandidate % base[i] == 0
            while (n.mod(BigInteger.valueOf(base[i])).compareTo(BigInteger.ZERO) == 0) {

                // bigCandidate = bigCandidate / base[i]
                n = n.divide(BigInteger.valueOf(base[i]));

                baseCounter[i] = baseCounter[i] + 1;
            }
        }

        // if the remainder is greater than 1 then n is not B Smooth
        return n.compareTo(BigInteger.ONE) == 0;
    }

    private boolean doesNewPairMakeEven(CandidatePair candidate, List<CandidatePair> candidateList) {

        int i;
        int counter = 0;
        int[] tempPowerList = new int[bLen];
        CandidatePair tempCandidate = new CandidatePair();

        for (CandidatePair c : candidateList) {

            for (i = 0; i < bLen; i++) {
                tempPowerList[i] = c.getPowerIndividualFromList(i)
                        + candidate.getPowerIndividualFromList(i);
            }

            tempCandidate.setPowerList(tempPowerList);

            if (tempCandidate.isPowerListEven()) {
                evenPowerList = tempPowerList;
                evenPointer = counter;
                return true;
            }

            counter++;
        }

        return false;
    }

    private BigInteger simplifyPowerList() {

        int total = 1;
        int i;

        for (i = 0; i < evenPowerList.length; i++) {
            evenPowerList[i] = evenPowerList[i] / 2;

            switch (i) {

                case 0: //base 2
                    total *= Math.pow(2, evenPowerList[i]);
                    break;
                case 1: //base 3
                    total *= Math.pow(3, evenPowerList[i]);
                    break;
                case 2: //base 5
                    total *= Math.pow(5, evenPowerList[i]);
                    break;
                case 3: //base 7
                    total *= Math.pow(7, evenPowerList[i]);
                    break;
            }
        }

        return BigInteger.valueOf(total);
    }

    private String checkDixon() {

        BigInteger check;

        check = dixonsX.multiply(dixonsY);

        if (check.compareTo(big) == 0) {
            return check.toString() + " OK!";
        }

        return check.toString() + " Error!";
    }

}
