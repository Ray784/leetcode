/**
 * This class represents a solution for the race car problem.
 * The race car problem involves finding the minimum number of steps required for a car to reach a target position on a track.
 * The car can move forward or backward, and its movement is subject to certain rules.
 */
public class Solution {
    /**
     * Calculates the minimum number of instructions required to reach the target position.
     * 
     * @param target The target position.
     * @return The minimum number of instructions required.
     */
    public int racecar(int target) {
        return sequenceLength(target, false, 0, 0);
    }

    /**
     * Calculates the length of the sequence required to reach the target value.
     * 
     * @param val    The current value.
     * @param isNeg  Indicates if the current sign is negative.
     * @param p      The number of steps taken in the positive direction.
     * @param m      The number of steps taken in the negative direction.
     * @return       The length of the sequence required to reach the target value.
     */
    private int sequenceLength(int val, boolean isNeg, int p, int m) {
        if(is2PowerOneLess(val)) {
            if(m <= p)
                return numBits(val) + (2 * p);
            return numBits(val) + (2 * m) - 1; 
        }

        // using the next power of 2 to arrive at solution
        // sign changes
        // since sign is reversed, if isNeg is true, increment p else increment m
        int pow2 = getNext2Power(val);
        int len1 = numBits(pow2) + sequenceLength(pow2 - val, !isNeg, p + (isNeg? 1: 0), m + (isNeg? 0: 1));

        // using the previous power of 2 to arrive at solution
        // no sign change
        // since no sign change, if isNeg is true, increment m else increment p
        pow2 >>= 1;
        int len2 = numBits(pow2) + sequenceLength(val - pow2, isNeg, p + (isNeg? 0: 1), m + (isNeg? 1: 0));

        return Math.min(len1, len2);
    }
    
    /**
     * Calculates the next power of 2 that is one less than the given number.
     *
     * @param num the number for which the next power of 2 is to be calculated
     * @return the next power of 2 that is one less than the given number
     */
    private int getNext2Power(int num) {
        int temp = num;
        // keep or-ing with num/2 until all bits are set
        while(!is2PowerOneLess(temp))
            temp = (temp | (temp >> 1));
        return temp;
    }
    
    /**
     * Checks if the given number is one less than a power of 2.
     *
     * @param num the number to be checked
     * @return true if the number is one less than a power of 2, false otherwise
     */
    private boolean is2PowerOneLess(int num) {
        // and with num+1 to get 0 => num+1 is a power of 2
        return (num & (num + 1)) == 0;
    }

    /**
     * Calculates the number of bits required to represent a given number.
     *
     * @param num The number for which the number of bits is to be calculated.
     * @return The number of bits required to represent the given number.
     */
    private int numBits(int num) {
        // count the number of bits until the max set bit
        int temp = num;
        int count = 0;
        while(temp > 0) {
            temp >>= 1;
            count++;
        }
        return count;
    }
}