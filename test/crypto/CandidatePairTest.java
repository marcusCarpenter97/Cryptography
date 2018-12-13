/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crypto;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marcus
 */
public class CandidatePairTest {
    
    static CandidatePair instance;
    static int[] evenList;
    static int[] oddList;
    
    public CandidatePairTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        instance = new CandidatePair();
        evenList = new int[] {2, 0 ,6, 4};
        oddList = new int[] {2, 0, 1, 4};
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of isPowerListEven method, of class CandidatePair.
     */
    @Test
    public void isPowerListEven_evenList_true() {
        
        boolean expResult = true;
        
        instance.setPowerList(evenList);
        
        boolean result = instance.isPowerListEven();
        
        assertEquals(expResult, result);
    }
    @Test
    public void isPowerListEven_oddList_false() {
        
        boolean expResult = false;
        
        instance.setPowerList(oddList);
        
        boolean result = instance.isPowerListEven();
        
        assertEquals(expResult, result);
    }
}
