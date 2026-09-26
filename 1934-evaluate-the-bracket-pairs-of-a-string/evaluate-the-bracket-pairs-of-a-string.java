import java.util.*;

class Solution {
    public String evaluate(String s, List<List<String>> knowledge) {
        
        // Store key-value pairs in HashMap
        Map<String, String> map = new HashMap<>();
        
        for (List<String> pair : knowledge) {
            map.put(pair.get(0), pair.get(1));
        }

        StringBuilder result = new StringBuilder();
        
        int i = 0;
        
        while (i < s.length()) {
            
            // Normal character
            if (s.charAt(i) != '(') {
                result.append(s.charAt(i));
                i++;
            } 
            
            // Bracket pair
            else {
                int j = i + 1;
                
                // Find closing bracket
                while (s.charAt(j) != ')') {
                    j++;
                }
                
                // Extract key
                String key = s.substring(i + 1, j);
                
                // Get value or '?'
                result.append(map.getOrDefault(key, "?"));
                
                // Move past ')'
                i = j + 1;
            }
        }
        
        return result.toString();
    }
}