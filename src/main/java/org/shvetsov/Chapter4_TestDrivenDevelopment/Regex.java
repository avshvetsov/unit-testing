package org.shvetsov.Chapter4_TestDrivenDevelopment;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public List<String> getThreeOrMoreDigitNumbersFromString(String input) {
        Pattern p = Pattern.compile("\\d{3,}+");
        Matcher m = p.matcher(input);
        List<String> res = new ArrayList<>();
        while(m.find()) {
            res.add(m.group());
        }
        return res;
    }
}
