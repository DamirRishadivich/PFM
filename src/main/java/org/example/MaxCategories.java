package org.example;

import com.opencsv.exceptions.CsvValidationException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MaxCategories {
    public static Map.Entry<String, Long> maxCategories() throws CsvValidationException, IOException {
        HashMap<String, Long> map = new HashMap<>();
        JSONParser parser = new JSONParser();
        try (BufferedReader reader = new BufferedReader(new FileReader("logger.json"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Object obj = parser.parse(line);
                JSONObject jsonObject = (JSONObject) obj;
                String cat = CategoriesGetter.getCategories((String) jsonObject.get("title"));
                long amount = (long) jsonObject.get("sum");
                check(map, cat, amount);
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return maximumCheck(map);
    }

    public static Map.Entry<String, Long> maximumCheck (HashMap < String, Long > map) {
        Map.Entry<String, Long> maxEntry = null;
        for (Map.Entry<String, Long> entry : map.entrySet()) {
            if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
                maxEntry = entry;
            }
        }
        return maxEntry;
    }

    public static void check (HashMap < String, Long > map, String cat, Long amount){
        if (map.containsKey(cat)) {
            map.put(cat, map.get(cat) + amount);
        } else {
            map.put(cat, amount);
        }
    }
}
