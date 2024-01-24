## 818. Race Car
### Brief Description
- Your car starts at position 0 and speed +1 on an infinite number line. 
- Your car can go into negative positions. 
- Your car drives automatically according to a sequence of instructions `'A'` (accelerate) and `'R'` (reverse):

When you get an instruction `'A'`, your car does the following:
- `position += speed`
- `speed *= 2`

When you get an instruction `'R'`, your car does the following:
- `speed > 0? then speed = -1`
- `otherwise speed = 1`
- Your position stays the same.

**Example:**
- sequence: `AAR`
- your car goes to positions `0 --> 1 --> 3 --> 3`
- your speed goes to `1 --> 2 --> 4 --> -1`

Given a target position target, return the length of the shortest sequence of instructions to get there.

[race-car on leetcode](https://leetcode.com/problems/race-car/description/)

## Solution
### Intuition
```x
pattern -> target
A       -> 1
AA      -> 3
AAA     -> 7
AAAA    -> 15
So, if we keep increasing A's in the pattern the result is 2^k - 1
where k is the number of A's in the pattern 
```

Now, notice these
```x
pattern  -> target
AARA     -> 2 (3{AA} - 1{A}) 
AAARA    -> 6 (7{AA} - 1{A})
AAARARAA -> 9 (7 + 2) -> (7 + 3 - 1) -> (7{AAA} - 1{A} + 3{AA})
So, R represents simply a sign change
```

Now, notice these
```x
pattern -> target
AARRA   -> 4 (3{AA} + 1{A})  
AAARRA  -> 8 (7{AAA} + 1{A})
So, RR represents a reset(same sign but set back to 1 
i.e. start counting again from 1)
```

So for each target we could get the best solution based on these patterns seen above

One last pattern for better understanding the approach:
```x
target = 23
23 -> (15 + 8) or (31 - 8)
8  -> (7 + 1)  or (15 - 7)

so
(15 + 8) -> (15 + 7 + 1) or (15 + 15 - 7)
rearraging 
(15 + 8) -> (15 + 7 + 1) or (15 - 7 + 15)

(15 + 7 + 1)  -> AAAA RR AAA RR A -> len = 12
(15 - 7 + 15) -> AAAA R AAA R AAAA -> len = 13

and
(31 - 8) -> (31 - 7 - 1) or (31 - 15 + 7)

(31 - 7 - 1) -> AAAAA R AAA RR A -> len = 12
(31 - 15 + 7) -> AAAAA R AAAA R AAA -> len = 14

so racecar(23) = 12 {AAAARRAAARRA or AAAAARAAARRA}
notice how the signs/numbers were rearranged to get 
the smallest possible length
```

### Approach
for a normal case (input `n`):
```x
k = number of bits in n
nextPow2 = 2^(k+1) - 1
prevPow2 = 2^k - 1 {i.e. nextPow2 >> 1}
sequenceLength(n) -> min of
    k + sequenceLength(n - prevPow2)
    k + 1 + sequenceLength(nextPow2 - n)
```
for base case input `n` of the form $2^x-1$
```x
sequenceLength(n) = x
    if n = 2^x - 1 
```

The above is only a part of the logic, we would also need to propagate the signs to get the smallest possible lengths and finally add it to the base-case

so the base case would become
```x 
sequenceLength(n) = x + f(p, m)
    if    n = 2^x - 1 
    where m = number of '-' signs
          p = number of '+' signs
we shall define te function f later
```

For example
```x
for 9 in the above example 
if we consider
9 -> 7 + 2 -> 7 + 3 - 1 
If we do not rearrange it would turn out to be 
  7  +   3 - 1
AAA RR  AA R A -> len = 9
but if we rearrange
  7 - 1 +  3
AAA R A R AA -> len = 8

so, we need to keep track of the signs until we reach the base case
```

How to propagate the signs
```x
1. We will have to have a boolean to mark the sign changes.
2. 2 integers to store the number of plus signs (p) and the 
   number of minus signs (m). 
```

so recurrence relation changes to
```x
k = number of bits in n
nextPow2 = 2^(k+1) - 1
prevPow2 = 2^k - 1 {i.e. nextPow2 >> 1}
sequenceLength(n, isNeg, p, m) -> min of
    // sign changes here i.e. if + earlier change it to - and viceversa
    // p' is p + 1 if old isNeg is true (since - of - is +)
    // else m' = m + 1
    --> k + 1 + sequenceLength(nextPow2 - n, !isNeg, p', m')
    
    // sign is same. i.e. + remains +, - remains -
    // m' = m + 1 if old isNeg is true, else p' = p + 1
    --> k + sequenceLength(n - prevPow2, isNeg, p', m')
```

and for the base case we would need to define f(p, m)
```x
we'll have 3 cases for p and m 
1. p == m
2. p > m
3. m > p

take the following equation
(x + z + b - y - a) ---rearranging---> x - y + z - a + b
p = 2
m = 2
here the number of plus(p) and number of minus(m) signs are equal 
so, the extra characters added in the pattern will be an 'R' 
for each sign so, the count of additional characters would be 
4 which is
1. twice the number of plus signs(or minus signs)
Thus f(p, m) = 2 * p (when p == m)

consider another equation
x + y + a - z ---rearranging---> x - z + y + a
the first - and + reduce to only one character each('R') in the 
result. However, the second + becomes 'RR'
so the count of additional characters would be 4 which is 
1. twice the number of minus signs(m) in terms of 1 char ('R')
2. the difference (p - m) in terms of 2 char('RR')

so f(p, m) = 2 * m + 2 * (p - m) 
    (Note the 2 in the second term since it adds 2 characters ('RR')
        for each of the sign)

f(p, m) = 2m + 2p - 2m = 2 * p (when p > m)

Thus so far f(p, m) = 2 * p (when p >= m)

finally consider
x + z - y - a ---rearranging---> x - y + z - a
p = 1
m = 2
In this case the m > p but still we don't need 'RR' pattern since
sign changes alternatively 
hence we would need 3 additional characters = m + p
so, f(p, m) = m + p (when p == m - 1)
f(p, m) = m + (m - 1) (when p == m - 1)
f(p, m) = 2m - 1 (when p == m - 1)

but
x + z - y - a - b ---rearranging---> x - y + z - a - b
in this case the first and second '-' and the first(and only) '+'
reduce to one character each 'R' and the remaining '-' signs 
reduce to two characters 'RR'
so the number of additional characters is 
1. 2 * p + 1 in terms of 'R' s (p +'s and p+1 -'s)
2. (m - (p + 1)) in terms of 'RR' s (remaining -'s)

f(p, m) = 2p + 1 + 2 * (m - (p + 1)) (when p < m - 1)
    (Note the 2 in the second term since it adds 2 characters ('RR')
        for each of the sign)

f(p, m) = 2p + 1 + 2(m - p - 1)
        = 2p + 1 + 2m - 2p - 2
        = 2m - 1 (when p < m - 1)

combining last 2 
f(p, m) = 2m - 1 (when p < m)
```

So finally we can define The recurrance equation as
```x
k = number of bits in n
nextPow2 = 2^(k+1) - 1
prevPow2 = 2^k - 1 {i.e. nextPow2 >> 1}
sequenceLength(n, isNeg, p, m) -> min of
    --> k + 1 + sequenceLength(nextPow2 - n, !isNeg, p', m')
        (where if isNeg p'=p+1, m'=m else m'=m+1, p'=p)
    --> k + sequenceLength(n - prevPow2, isNeg, p', m')
        (where if isNeg p'=p, m'=m+1 else m'=m, p'=p+1)
and
sequenceLength(n, isNeg, p, m) = x + f(p, m)
    if    n = 2^x - 1
    where m = number of '-' signs
          p = number of '+' signs
and
f(p, m) = 2m - 1 (when p < m)
          2p (when p >= m)
```

### Time Complexity
The recurrance relation for time complexity can be defined as follows
`T(n) = T(2k - n) + T(n - k) + c`
where k = $2 ^ {floor(log_2(n))}$
and $T(2^x -1) = O(1)$ for all $x >= 0$

so if we visualise the recursion tree each node will have 2 children at max and it will grow upto $log_2(n)$ i.e. the depth is $log_2(n)$ so the 
complexity is $O(2^{floor(log_2(n))})$ which is close to `O(n)`

although the solution works for $n = 10^{18}$ we can say `O(n)` since what it really means is `complexity(our_sol) <= O(n)`

### Space Complexity
Depth of the recursion tree is $log_2(n)$ so the space complexity is `O(logn)`
