## 645. Set Mismatch
### Brief Description

- Given a set of integers `nums`, which originally contains all the numbers from 1 to n. 
- Due to some error, one of the numbers in `nums` got duplicated to another number in the set
- This results in repetition of one number and loss of another number.

Find the number that occurs twice and the number that is missing and return them in the form of an array.

**Example:**
>**Input:** nums = [1,2,2,4] \
**Output:** [2,3]

[set-mismatch on leetcode](https://leetcode.com/problems/set-mismatch/description/)

## Solution
### Intuition
Use xor to find the repeated and missing values.

### Approach
since all the number are from 1 - n we traverse from 1 - n and xor all the values and xor them with the array elements, we will end up with the xor of the missing element and repeated element

```x
example:
arr = [6,4,5,2,1,7,4]

result_xor = 
    (1 ^ 2 ^ 3 ^ 4 ^ 5 ^ 6 ^ 7) ^ 
        (6 ^ 4 ^ 5 ^ 2 ^ 1 ^ 7 ^ 4) 
    = 3 ^ 4
```

now based on the least significant set bit ($i^{th}$ bit) in the resulting XOR we can divide the number array into 2 parts one part with the $i^{th}$ bit set and the other set with $i^{th}$ bit is not set.

```x 
Why does this work?
```
Well since if the ith bit is set in the result_xor it means that $i^{th}$ bit is set in only one of the 2 numbers forming the xor hence when we partition the array into 2 parts we will end up with 2 xors one having the missing number and one having the repeated number.

```x
from the above example we have 
result_xor = 3 ^ 4 = 7
in 7 0th bit is set hence i = 0

so we divide the array and index into 2 parts based 
on the rightmost bit (p1 is part1(right bit 0) and 
p2 is part2(right bit 1))

the array         ---  the indices(1-indexed)
6  - 110  - p1               1  - 001 - p2
4  - 100  - p1               2  - 010 - p1
5  - 101  - p2               3  - 011 - p2
2  - 010  - p1               4  - 100 - p1
1  - 001  - p2               5  - 101 - p2
7  - 111  - p2               6  - 110 - p1
4  - 100  - p1               7  - 111 - p2

xor_p1 = (6 ^ 4 ^ 2 ^ 4) ^ (2 ^ 4 ^ 6) = 4
xor_p2 = (5 ^ 1 ^ 7) ^ (1 ^ 3 ^ 5 ^ 7) = 3
```

Iterate over the array again to check which one of xor_p1 and xor_p2 is present in the array and return answer appropriately.

### Time Complexity
$O(n)$ -> since the array is being traversed in one direction

### Space Complexity
$O(1)$ -> no extra space used only integers to store XOR values