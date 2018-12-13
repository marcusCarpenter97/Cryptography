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
public class ISBNandCard {

    private final int isbnLen;
    private final int cardLen;
    private final String isbnOK;
    private final String isbnEr;
    private final String cardOK;
    private final String cardEr;

    public ISBNandCard() {
        isbnLen = 10;
        cardLen = 16;
        isbnOK = "ISBN is valid!";
        isbnEr = "ISNB is not valid!";
        cardOK = "Credit card number is valid!";
        cardEr = "Credit card number is not valid!";
    }

    /**
     * Checks if an ISBN number is valid or not by using the ISBN-10 formula
     *
     * @param isbnString a 10 digit ISBN number to be checked
     * @return message saying if the ISBN number is valid or not
     */
    public String isbnCheck(String isbnString) {
        int sum = 0;

        if (isbnString.isEmpty() || isbnString.length() != isbnLen) {
            return isbnEr;
            
        } else {
            
            for (int i = 0; i < isbnLen; i++) {
                
                //Sometimes ISBN numbers end in X which represents 10
                if (String.valueOf(isbnString.charAt(i)).equalsIgnoreCase("X")) {
                    sum += (i + 1) * 10;
                    
                } else {
                    
                    try {
                        sum += (i + 1) * Integer.parseInt(String.valueOf(isbnString.charAt(i)));
                    } catch (NumberFormatException e) {
                        return isbnEr + " " + e;
                    }
                }
            }

            if (sum % 11 == 0) {
                return isbnOK;
            } else {
                return isbnEr;
            }
        }
    }

    /**
     * Uses the Luhn algorithm to verify a credit card number.
     *
     * @param cardNumString the 16 digit card number to be checked
     * @return message saying if card number is valid or invalid
     */
    public String cardNumCheck(String cardNumString) {
        int sum = 0;
        int temp;

        if (cardNumString.isEmpty() || cardNumString.length() != cardLen) {
            return cardEr;
            
        } else {
            
            for (int i = 0; i < cardLen; i++) {
                
                try {
                    temp = Integer.parseInt(String.valueOf(cardNumString.charAt(i)));
                } catch (NumberFormatException e) {
                    return cardEr + " " + e;
                }
                
                if (i % 2 == 0) {
                    temp *= 2;
                }
                if (temp > 9) {
                    temp -= 9;
                }
                sum += temp;
            }

            if (sum % 10 == 0) {
                return cardOK;
            } else {
                return cardEr;
            }
        }
    }
 
}
