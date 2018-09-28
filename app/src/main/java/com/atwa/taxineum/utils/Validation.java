package com.atwa.taxineum.utils;

import android.widget.EditText;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ahmed Atwa on 3/3/2018.
 */

public class Validation {

   private static Pattern usrNamePtrn = Pattern.compile("^[a-z0-9_-]{6,14}$");


   public static Boolean isNameValid(String name) {
      Matcher mtch = usrNamePtrn.matcher(name);
      return (mtch.matches());
   }

   public static Boolean isPhoneValid(String phone) {
      boolean isValid = false;
      if (phone.length() == 11) {
         String[] x = phone.split("");
         if (x[1].equals("0") && x[2].equals("1")) {
            if (x[3].equals("1") || x[3].equals("0") || x[3].equals("2") || x[3].equals("5")) {
               isValid = true;
            }
         }
      }
      return isValid;
   }

   public static boolean isEmailValid(String email) {
      boolean isValid = false;

      String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
      CharSequence inputStr = email;

      Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
      Matcher matcher = pattern.matcher(inputStr);
      if (matcher.matches()) {
         isValid = true;
      } else {

      }
      return isValid;
   }

   public static boolean isVisaValid(String visa) {
      boolean isValid = false;
      if (visa.length() == 0 || visa.length() == 14)
         isValid = true;
      return isValid;
   }

   public static boolean isPasswordValid(String password) {
      boolean isValid = false;
      if (password.length() > 5)
         isValid = true;
      return isValid;
   }

   public static boolean isPasswordMatch(String password, String rePasssword) {
      boolean isValid = false;

      if (password.equals(rePasssword)) {
         isValid = true;
      }
      return isValid;
   }
}
