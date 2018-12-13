/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crypto;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marcus
 */
public class TrialDivisionFactorization {
    
    private BigInteger big;
    private BigInteger trialX;
    private BigInteger trialY;

    public TrialDivisionFactorization() {
    }
    
    public String startTrial(String n) {
        
        big = new BigInteger(n);
        
        bigTrial();
        
        return "";
    }
    
    public List<BigInteger> bigTrial() {
        
        List<BigInteger> bigList = new ArrayList<>();
        
        BigInteger factorCandidate = BigInteger.valueOf(2);
        
        // while big is greater than 1
        while(big.compareTo(BigInteger.ONE) > 0) {
            
            // if big is divisible by factorCandidate
            if(big.mod(factorCandidate) == BigInteger.ZERO) {
                
                bigList.add(factorCandidate);
                
                big = big.divide(factorCandidate);
                
            } else {
                factorCandidate = factorCandidate.add(BigInteger.ONE);
            }
        }
        
        return bigList;
        
    }
    
    public String checkTrial() {
        return "";
    }
    
}
