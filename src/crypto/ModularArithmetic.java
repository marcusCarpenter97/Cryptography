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
public class ModularArithmetic {

    /**
     * Selects correct function according to user input for modular arithmetic
     *
     * @param xString
     * @param yString
     * @param mString
     * @param op
     * @return
     */
    public String checkInputAndCallFunc(String xString, String yString, String mString, char op) {
        int x, y, mod, ans;

        if (xString.isEmpty() || yString.isEmpty() || mString.isEmpty()) {
            return "X, y or mod not found!";
        }
        try {
            x = Integer.parseInt(xString);
            y = Integer.parseInt(yString);
            mod = Integer.parseInt(mString);
        } catch (NumberFormatException e) {
            return "X, y or mod have to be integers!";
        }

        switch (op) {
            case '+':
                ans = add(x, y, mod);
                break;
            case '-':
                ans = subtract(x, y, mod);
                break;
            case '*':
                ans = multiply(x, y, mod);
                break;
            case '/':
                ans = divide(x, y, mod);
                break;
            default:
                return "Only valid operations are +, -, * and / !";
        }
        return String.valueOf(ans);
    }

    /**
     * Adds two values and returns the modulus of the result
     *
     * @param x first value to be added
     * @param y second value to be added
     * @param m the modulo value
     * @return (x + y)mod m
     */
    public int add(int x, int y, int m) {
        //return xModuloY(x, m) + xModuloY(y, m);
        return xModY((x + y), m);
    }

    /**
     * Subtracts two values and returns the modulus of the result The last part
     * is added to keep the value from being negative
     *
     * @param x first value to be subtracted
     * @param y second value to be subtracted
     * @param m the modulo value
     * @return (x - y)mod m
     */
    public int subtract(int x, int y, int m) {
        return ((xModY(x, m) - xModY(y, m)) + m) % m;
    }

    /**
     * Multiplies two values and returns the modulus of the result
     *
     * @param x first value to be multiplied
     * @param y second value to be multiplied
     * @param m the modulo value
     * @return (x * y)mod m
     */
    public int multiply(int x, int y, int m) {
        //return xModuloY(x, m) * xModuloY(y, m);
        return xModY((x * y), m);
    }

    /**
     * Divides two values and returns the modulus of the result
     *
     * @param x first value to be divided
     * @param y second value to be divided
     * @param m the modulo value
     * @return (x / y)mod m
     */
    public int divide(int x, int y, int m) {
        int inverseY = inverse(y, m);
        return multiply(x, inverseY, m);
    }

    /**
     * Gets the modulo for x and y, this works for positive and negative numbers
     * because the x % y will return a number between -y and 0, the +y will make
     * it positive and the %y will keep it under y even if it was a negative
     * number to begin with.
     *
     * @param x is the divided
     * @param y is the divisor
     * @return the remainder of the division between x and y
     */
    public int xModY(int x, int y) {
//        int ret = (x % y);
//        ret += y;
//        ret %= y;
//        return ret;
        return ((x % y) + y) % y;
    }

    /**
     * Extended Euclidean Algorithm for calculating inverse
     *
     * @param a number
     * @param n mod
     * @return inverse of number
     */
    public int inverse(int a, int n) {
        int t = 0;
        int newt = 1;
        int r = n;
        int newr = a;
        int q, temp;
        while (newr != 0) {
            q = r / newr;
            /* integer division */
            temp = newt;
            /* remember newt    */
            newt = t - q * newt;
            t = temp;
            temp = newr;
            /* remember newr    */
            newr = r - q * newr;
            r = temp;
        }
        if (r > 1) {
            return -1;
            /* not invertible */
        }
        if (t < 0) {
            t = t + n;
            /* change to positive */
        }
        return t;
    }

}
