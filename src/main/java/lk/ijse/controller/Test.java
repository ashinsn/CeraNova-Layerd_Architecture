package lk.ijse.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) {
        Pattern compile = Pattern.compile("//d{10}");
        Matcher matcher = compile.matcher("4587963298");
        boolean isValid = matcher.matches();
        System.out.println(isValid);

        /*boolean matches = Pattern.compile("\\d", "nhfdjkn").matches();
        System.out.println(matches);*/

        /*boolean matches = Pattern.matches("\\d", "nhfdjkn");
        System.out.println(matches);*/

    }
}