/**
 * This class provides a solution for finding the error numbers in an array. Error numbers are numbers that are missing and numbers that are repeated.
 * Provided only one is missing and one is repeated and the array contains only positive numbers from 1 to n, where n is the length of the array.
 */
public class Solution {
    /**
     * Finds the error numbers in the given array.
     * 
     * @param nums The array of integers.
     * @return An array containing the error numbers, where the first element is the missing number and the second element is the repeating number.
     */
    public int[] findErrorNums(int[] nums) {
        int xor = 0;

        // XOR all the numbers from 1 to nums.length and all the numbers in the nums array.
        // The result is the XOR of the missing and repeating numbers.
        for(int i = 1; i <= nums.length; i++) {
            xor ^= i;
            xor ^= nums[i-1];
        }

        // Initialize a counter i and a variable xor1 with the value of xor.
        // Start a loop that will run up to 31 times. This is because the maximum value of n is 2^31 - 1(integer limit).
        // In each iteration, check if the least significant bit of xor1 is 1.
        // If it is, break the loop as we've found the first set bit.
        // If it's not, shift the bits of xor1 one place to the right, effectively dividing it by 2.
        int i = 0;
        int xor1 = xor;
        for(i = 0; i < 30; i++) {
            if((xor1 & 1) == 1)
                break;
            xor1 >>= 1;
        }
        
        // This code separates the numbers into two groups based on the checker bit.
        // If the checker bit in a number is 1, it is XORed with xor1.
        // If the checker bit in a number is 0, it is XORed with xor2.
        // This is done for both the numbers from 1 to nums.length and the numbers in the nums array.
        // The result is that xor1 and xor2 hold the missing and repeating numbers, but it's not known which is which.
        int checker = i;
        int xor2 = 0;
        xor1 = 0;
        for(i = 1; i <= nums.length; i++) {
            if(((i >> checker) & 1) == 1)
                xor1 ^= i;
            else
                xor2 ^= i;
            
            if(((nums[i-1] >> checker) & 1) == 1)
                xor1 ^= nums[i-1];
            else
                xor2 ^= nums[i-1];
        }

        // This code finds the missing and repeating numbers.
        // It does so by checking if the missing number is in the nums array.
        // If it is, then the missing number is xor1 and the repeating number is xor2.
        // If it's not, then the missing number is xor2 and the repeating number is xor1.
        for(i = 0; i < nums.length; i++) {
            if(xor1 == nums[i])
                return new int[] {xor1, xor2};
        }
        return new int[] {xor2, xor1};
    }
}