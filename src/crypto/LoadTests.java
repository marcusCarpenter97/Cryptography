package crypto;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LoadTests {

    public String[] factList() throws FileNotFoundException {

        String[] tempData = new String[18];
        int i = 0;

        File f = new File("/home/marcus/NetBeansProjects/cryptography/src/crypto/factorisationTests");
        Scanner data = new Scanner(f);

        while (data.hasNext()) {

            tempData[i] = data.next();
            i++;
        }

        return tempData;

    }

    public void testFermat() throws FileNotFoundException {
        
        String[] testCases = factList();
        
        FermatFactorization ff = new FermatFactorization();
        
        String ret;
        
        long timer;
        
        int i;
        
        for (i = 0; i < testCases.length; i++) {
             
            timer = System.nanoTime();
            
            ret = ff.callFermat(testCases[i]);
            
            timer = System.nanoTime() - timer;
            
            System.out.println(ret);
            System.out.println("Time taken is " + timer + "  nanoseconds");
            
        }
    }

    public void testDixon() throws FileNotFoundException {
        
        String[] testCases = factList();
        
        DixonFactorization df = new DixonFactorization();
        
        String ret;
        
        long timer;
        
        int i;
        
        for (i = 0; i < testCases.length; i++) {
            
            timer = System.nanoTime();
            
            ret = df.callDixon(testCases[i]);
            
            timer = System.nanoTime() - timer;
            
            System.out.println(ret);
            System.out.println("Time taken is " + timer + "  nanoseconds");
        }
    }

    public static void main(String[] args) {
        
        LoadTests lt = new LoadTests();
        
        try {
            
//            lt.testFermat();
            lt.testDixon();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

}
