package com.chenbing.Pattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternTest {

    public static void main(String[] args){
        String phonrNul = "${aaabc}";
        Pattern compile = Pattern.compile("\\$\\{([^}]*)\\}");
        Matcher matcher = compile.matcher(phonrNul);
        if(matcher.find()){
                System.out.println("lllllllllll:{}"+matcher.group());
                System.out.println("wwwwwwwwwww:{}"+matcher.group(1));
        }

    }
}
