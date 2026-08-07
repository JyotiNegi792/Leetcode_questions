import java.util.*;

class Solution {
    private static final List<Map<Integer, Integer>> FACTOR_COUNTS = Arrays.asList(
        Collections.emptyMap(), // 0
        Collections.emptyMap(), // 1
        Map.of(2, 1),           // 2
        Map.of(3, 1),           // 3
        Map.of(2, 2),           // 4
        Map.of(5, 1),           // 5
        Map.of(2, 1, 3, 1),     // 6
        Map.of(7, 1),           // 7
        Map.of(2, 3),           // 8
        Map.of(3, 2)            // 9
    );

    public String smallestNumber(String num, long t) {
        Map<Integer, Integer> primeCount = new HashMap<>();
        primeCount.put(2, 0);
        primeCount.put(3, 0);
        primeCount.put(5, 0);
        primeCount.put(7, 0);

        for (int prime : new int[]{2, 3, 5, 7}) {
            while (t % prime == 0) {
                t /= prime;
                primeCount.put(prime, primeCount.get(prime) + 1);
            }
        }
        if (t > 1) return "-1";

        Map<Integer, Integer> factorCount = getFactorCount(primeCount);
        if (sumValues(factorCount) > num.length()) {
            return construct(factorCount);
        }

        Map<Integer, Integer> primeCountPrefix = getPrimeCount(num);
        int firstZeroIndex = num.indexOf('0');
        if (firstZeroIndex == -1) {
            firstZeroIndex = num.length();
            if (isSubset(primeCount, primeCountPrefix)) {
                return num;
            }
        }

        for (int i = num.length() - 1; i >= 0; --i) {
            int d = num.charAt(i) - '0';
            primeCountPrefix = subtract(primeCountPrefix, FACTOR_COUNTS.get(d));
            int spaceAfterThisDigit = num.length() - 1 - i;

            if (i > firstZeroIndex) continue;

            for (int biggerDigit = d + 1; biggerDigit < 10; ++biggerDigit) {
                Map<Integer, Integer> factorsAfterReplacement = getFactorCount(
                    subtract(subtract(primeCount, primeCountPrefix), FACTOR_COUNTS.get(biggerDigit))
                );

                if (sumValues(factorsAfterReplacement) <= spaceAfterThisDigit) {
                    int fillOnes = spaceAfterThisDigit - sumValues(factorsAfterReplacement);
                    StringBuilder sb = new StringBuilder();
                    sb.append(num, 0, i);
                    sb.append(biggerDigit);
                    sb.append("1".repeat(fillOnes));
                    sb.append(construct(factorsAfterReplacement));
                    return sb.toString();
                }
            }
        }

        int fillOnes = num.length() + 1 - sumValues(factorCount);
        return "1".repeat(fillOnes) + construct(factorCount);
    }

    private Map<Integer, Integer> getPrimeCount(String s) {
        Map<Integer, Integer> count = new HashMap<>();
        count.put(2, 0);
        count.put(3, 0);
        count.put(5, 0);
        count.put(7, 0);
        for (char c : s.toCharArray()) {
            int d = c - '0';
            if (d > 0) {
                for (Map.Entry<Integer, Integer> entry : FACTOR_COUNTS.get(d).entrySet()) {
                    count.put(entry.getKey(), count.get(entry.getKey()) + entry.getValue());
                }
            }
        }
        return count;
    }

    private Map<Integer, Integer> getFactorCount(Map<Integer, Integer> count) {
        int c2 = count.get(2);
        int c3 = count.get(3);
        int c5 = count.get(5);
        int c7 = count.get(7);

        int count8 = c2 / 3;
        int remaining2 = c2 % 3;
        int count9 = c3 / 2;
        int count3 = c3 % 2;
        int count4 = remaining2 / 2;
        int count2 = remaining2 % 2;

        int count6 = 0;
        if (count2 == 1 && count3 == 1) {
            count2 = 0;
            count3 = 0;
            count6 = 1;
        }
        if (count3 == 1 && count4 == 1) {
            count2 = 1;
            count6 = 1;
            count3 = 0;
            count4 = 0;
        }

        Map<Integer, Integer> res = new LinkedHashMap<>();
        res.put(2, count2);
        res.put(3, count3);
        res.put(4, count4);
        res.put(5, c5);
        res.put(6, count6);
        res.put(7, c7);
        res.put(8, count8);
        res.put(9, count9);
        return res;
    }

    private String construct(Map<Integer, Integer> factors) {
        StringBuilder sb = new StringBuilder();
        for (int digit = 2; digit <= 9; ++digit) {
            int freq = factors.getOrDefault(digit, 0);
            sb.append(String.valueOf(digit).repeat(freq));
        }
        return sb.toString();
    }

    private boolean isSubset(Map<Integer, Integer> a, Map<Integer, Integer> b) {
        for (int key : a.keySet()) {
            if (a.get(key) > b.getOrDefault(key, 0)) {
                return false;
            }
        }
        return true;
    }

    private Map<Integer, Integer> subtract(Map<Integer, Integer> a, Map<Integer, Integer> b) {
        Map<Integer, Integer> res = new HashMap<>(a);
        for (Map.Entry<Integer, Integer> entry : b.entrySet()) {
            res.put(entry.getKey(), Math.max(0, res.getOrDefault(entry.getKey(), 0) - entry.getValue()));
        }
        return res;
    }

    private int sumValues(Map<Integer, Integer> count) {
        int sum = 0;
        for (int val : count.values()) {
            sum += val;
        }
        return sum;
    }
}