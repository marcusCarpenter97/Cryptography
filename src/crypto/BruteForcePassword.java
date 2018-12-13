/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crypto;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author marcus
 */
public class BruteForcePassword {

    private String password;
    private String SHA1hash;
    private final String[] alphabet;
    private final int passSize;
    private final String alpha;

    public BruteForcePassword() {
        password = "";
        SHA1hash = "";
        alphabet = new String[] {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "x", "w", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        alpha = "0123456789abcdefghijklmnopqrstuvxwyz";
//        alpha = "0a";
        passSize = 6;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        try {
            setSHA1hash(SHA1(password));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
        }
    }

    public String getSHA1hash() {
        return SHA1hash;
    }

    public void setSHA1hash(String SHA1hash) {
        this.SHA1hash = SHA1hash;
    }

    private String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9)) {
                    buf.append((char) ('0' + halfbyte));
                } else {
                    buf.append((char) ('a' + (halfbyte - 10)));
                }
                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public String SHA1(String text)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md;
        md = MessageDigest.getInstance("SHA-1");
        byte[] sha1hash = new byte[40];
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        sha1hash = md.digest();
        return convertToHex(sha1hash);
    }

    public String genPass() {
        String pass = "";
        int i, newChar;

        for (i = 0; i < passSize; i++) {
            newChar = (int) (Math.random() * alphabet.length);
            pass += alphabet[newChar];
        }

        setPassword(pass);

        return pass;
    }

    public String bruteForce() {
        int[] charIndex = new int[passSize];
        String candidatePassword;
        String candidateHash = "";
        int i, lastIndex = 0, indexSum = 0;
        
        if(SHA1hash.equals("da39a3ee5e6b4b0d3255bfef95601890afd80709")){
            return "Your password is an empty string!";
        }
        
        for (i = 1; i < passSize; i++) {
            charIndex[i] = -1;
        }

        while (true) {

            //create candidate password
            candidatePassword = "";
            for (i = 0; i < passSize; i++) {
                if (charIndex[i] > -1) {
                    candidatePassword += alphabet[charIndex[i]];
                }
            }
            
            //create candidate hash
            try {
                candidateHash = SHA1(candidatePassword);
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            }

            //if the hashes match the password is found
            if (candidateHash.equals(SHA1hash)) {
                return candidatePassword;
            }

            //increment the indicies
            //find last index used and increment it
            for (i = passSize - 1; i >= 0; i--) {
                if (charIndex[i] > -1) {
                    charIndex[i]++;
                    lastIndex = i;
                    break;
                }
            }

            //if index is now out of alphabet range set it back to 0
            //and increment prevoius index
            //repeat last two steps until all indicies are adjusted
            for (i = lastIndex; i >= 0; i--) {
                if (charIndex[i] > alphabet.length - 1) {
                    charIndex[i] = 0;
                    if (i > 0) {  //not required for the first index
                        charIndex[i - 1]++;
                    }
                }
                indexSum += charIndex[i];
            }

            //if all indicies end up set to 0 start new index at the end
            if (indexSum == 0 && lastIndex < passSize - 1) {
                charIndex[lastIndex + 1]++;
            }

            indexSum = 0;
        }
    }

    //???
    public String recursiveBruteForce(String w){
        int n;
        char c;
        n = w.length();
        if(n==0) return "a";       // when w is an empty string
        if(w.charAt(n-1) < 'z') {
            c = (char)((int)w.charAt(n-1)+1);     // move to the next letter, e.g. a -> b
            return w.substring(0,n-1) + String.valueOf(c); // substring(i,j) returns  chars(i,j-1)
        }
        else { // the last letter must be "z"
            return recursiveBruteForce(w.substring(0, n-1)) + "a";   // use recursion instead of loop
        }
    }
    
    //has to be called with empty string to cover all combinations
    //https://stackoverflow.com/questions/37629135/brute-force-java-recursive-password-crack
    public String RBF(String w){
        int n;
        char c;
        char tempC;
        n = w.length();
        String newW;
        String candidateHash = "";
        //create candidate hash
        try {
            candidateHash = SHA1(w);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {}

        //if the hashes match the password is found
        if (candidateHash.equals(SHA1hash)) {
            return w;
        }
        
        if(n==0) newW = "0"; // when w is an empty string
        else if(w.charAt(n-1) < 'z') {
                c = (char)((int)w.charAt(n-1)+1);     // move to the next letter, e.g. a -> b
                newW =  w.substring(0,n-1) + String.valueOf(c); // substring(i,j) returns  chars(i,j-1)
                       
        }
    else { // the last letter must be "z"
                newW = w + "0";   // use recursion instead of loop
                
                // loop through newW until all z are gone
                char[] temp;
                for(int i = newW.length()-1; i >= 0; i--){
                    if((int)newW.charAt(i) == 'z'){
                        temp = newW.toCharArray();
                        temp[i] = '0';
                        newW = String.valueOf(temp);
                    }
                }
            }
        return RBF(newW);
    }

}
