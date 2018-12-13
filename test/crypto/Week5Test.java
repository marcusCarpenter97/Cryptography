///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package crypto;
//
//import java.math.BigInteger;
//import java.util.ArrayList;
//import java.util.List;
//import org.junit.AfterClass;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import static org.junit.Assert.*;
//
///**
// *
// * @author marcus
// */
//public class Week5Test {
//
//    static FermatFactorization instance;
//    static CandidatePair can;
//    static List<CandidatePair> pairList;
//
//    public Week5Test() {
//    }
//
//    @BeforeClass
//    public static void setUpClass() {
//        instance = new FermatFactorization();
//        can = new CandidatePair();
//        pairList = new ArrayList<>();
//
//        CandidatePair testPair1 = new CandidatePair();
//        testPair1.setBigCandidate(BigInteger.valueOf(25));
//        testPair1.setPowerList(new int[]{0, 3, 0, 0});
//
////        CandidatePair testPair2 = new CandidatePair();
////        testPair2.setBigCandidate(BigInteger.valueOf(29));
////        testPair2.setPowerList(new int[]{0, 5, 0, 0});
//        pairList.add(testPair1);
//    }
//
//    @AfterClass
//    public static void tearDownClass() {
//    }
//
//    /**
//     * Test of callDixon method, of class FermatFactorization.
//     */
//    @Test
//    public void testCallDixon() {
//        String s = "299";
//        String expResult = "Input value: 299\nX = 23\nY = 13";
//        String result = instance.callDixon(s);
//        System.out.println("Name: testCallDixon");
//        System.out.println("expResult" + expResult + "result" + result);
//        assertEquals(expResult, result);
//    }
//
//    /**
//     * Test of bigDixon method, of class FermatFactorization.
//     */
//    @Test
//    public void testBigDixon() {
//        fail();
//    }
//
//    /**
//     * Test of calculateBSmoothPowerSet method, of class FermatFactorization.
//     */
//    @Test
//    public void calculateBSmoothPowerSet_bSmoothNumber_true() {
//
//        BigInteger n = BigInteger.valueOf(16);
//
//        boolean expResult = true;
//
//        boolean result = instance.calculateBSmoothPowerSet(n);
//        assertEquals(expResult, result);
//        System.out.println("Name: calculateBSmoothPowerSet_bSmoothNumber_true");
//        System.out.println("expResult " + expResult + " result " + result);
//    }
//
//    @Test
//    public void calculateBSmoothPowerSet_NotBSmoothNumber_false() {
//
//        BigInteger n = BigInteger.valueOf(299);
//
//        boolean expResult = false;
//
//        boolean result = instance.calculateBSmoothPowerSet(n);
//        assertEquals(expResult, result);
//        System.out.println("Name: calculateBSmoothPowerSet_NotBSmoothNumber_false");
//        System.out.println("expResult " + expResult + " result " + result);
//    }
//
//    /**
//     * Test of doesNewPairMakeEven method, of class FermatFactorization.
//     */
//    @Test
//    public void doesNewPairMakeEven_makesEven_true() {
//
//        CandidatePair testPair2 = new CandidatePair();
//        testPair2.setBigCandidate(BigInteger.valueOf(29));
//        testPair2.setPowerList(new int[]{0, 5, 0, 0});
//
//        boolean expResult = true;
//
//        boolean result = instance.doesNewPairMakeEven(testPair2, pairList);
//
//        assertEquals(expResult, result);
//        System.out.println("Name: doesNewPairMakeEven_makesEven_true");
//        System.out.println("expResult " + expResult + " result " + result);
//    }
//
//    @Test
//    public void doesNewPairMakeEven_makesOdd_false() {
//
//        CandidatePair testPair2 = new CandidatePair();
//        testPair2.setBigCandidate(BigInteger.valueOf(29));
//        testPair2.setPowerList(new int[]{0, 4, 0, 0});
//
//        boolean expResult = false;
//
//        boolean result = instance.doesNewPairMakeEven(testPair2, pairList);
//
//        assertEquals(expResult, result);
//        System.out.println("Name: doesNewPairMakeEven_makesOdd_false");
//        System.out.println("expResult " + expResult + " result " + result);
//    }
//
//    /**
//     * Test of simplifyPowerList method, of class FermatFactorization.
//     */
//    @Test
//    public void testSimplifyPowerList() {
////        FermatFactorization instance = new FermatFactorization();
////        BigInteger expResult = null;
////        BigInteger result = instance.simplifyPowerList();
////        assertEquals(expResult, result);
//        fail();
//    }
//
//    /**
//     * Test of callFermat method, of class FermatFactorization.
//     */
//    @Test
//    public void testCallFermat() {
//        fail();
//    }
//
//}
