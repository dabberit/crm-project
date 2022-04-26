package com.dabbler.crm.test;

import java.util.Scanner;

public class HomeWork {

    public static void main(String[] args) {
        testDate();
    }

    public void method() {
        String reg = "073[123]\\d{7}";

        Scanner sc = new Scanner(System.in);
        while (true) {
            String number = sc.next();
            System.out.println("输入为：" + number);
            boolean matches = number.matches(reg);
            if (!matches) {
                System.out.println("请输入正确的号码格式！");
            } else {
                String newNum = "";
                if (number.charAt(3) == '1') {
                    newNum = "0731" + number.substring(4) + "8";
                }
                if (number.charAt(3) == '2') {
                    newNum = "0731" + number.substring(4) + "5";
                }
                if (number.charAt(3) == '3') {
                    newNum = "0731" + number.substring(4) + "2";
                }
                System.out.println("新号码是：" + newNum);
            }
        }
    }

    public static void testDate() {
        String reg1 = "19990[1-9]";
        String reg2 = "19991[0-2]";
        String reg3 = "20[0-2][0-9]0[1-9]";
        String reg4 = "20[0-2][0-9]1[0-2]";

        Scanner sc = new Scanner(System.in);
        while (true) {
            String date = sc.next();
            System.out.println("输入为：" + date);
            boolean matches = date.matches(reg1)||date.matches(reg2)||date.matches(reg3)||date.matches(reg4);
            if (!matches) {
                System.out.println("请输入正确的日期格式！");
            } else {
                System.out.println("日期合格！");
            }
        }
    }
}
