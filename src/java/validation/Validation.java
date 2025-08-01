/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validation;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class Validation {

    private static Scanner scan = new Scanner(System.in);

    public static boolean pressYNToContinue(String mess) {
        while (true) {
            String input = getStringByRegex(mess, "Y/N Only!!!", "[YNyn]{1}");
            return input.equalsIgnoreCase("Y");
        }
    }

    public static String removeUnnecessaryBlank(String input) {
        return input.trim().replaceAll("\\s+", " ");
    }

    public static String removeAllBlank(String input) {
        return input.trim().replaceAll("\\s+", "");
    }

    public static String getStringByRegex(String mess, String error, String regex) {
        String output = null;
        while (true) {
            System.out.print(mess);
            output = scan.nextLine();
            if (output.matches(regex)) {
                return output;
            } else {
                System.err.println();//có thể sửa thành String errorMess
            }
        }
    }

    public static String getNonEmptyString(String mess) {
        String ret = "";
        while (true) {
            System.out.print(mess);
            ret = removeUnnecessaryBlank(scan.nextLine());
            if (ret.equalsIgnoreCase("")) {
                System.err.println("Please input Non-Empty String!!!");
                continue;
            }
            return ret;
        }
    }

    public static String getEmail(String mess) {
        String regex = "^[A-Za-z](.*)([@]{1})(.{2,})(\\.)(.{2,})";//phai bat dau bang chu cai
        String email = getStringByRegex(mess, "Please enter email with format <account name>@<domain>", regex);
        return email;
    }

    public static String getPhone(int minLength, String mess) {
        String regex = "[0-9 -]+";
        while (true) {
            String phoneNum = getStringByRegex(mess, "Please enter number only!!", regex).replaceAll("\\s+", "");
            if (phoneNum.length() < minLength) {
                System.err.println("Phone number must be at least 10 characters");
            } else {
                return phoneNum;
            }
        }
    }

    public static int getRandomInt(int min, int max) {
        return min + (int) (Math.random() * (max - min + 1));
    }

    //hàm nhập số kiểu int
    public static int getInt1(String mess, int min, int max) {
        while (true) {
            System.out.print(mess);
            try {
                int input = Integer.parseInt(scan.nextLine());
                if (input < min || input > max) {
                    throw new IndexOutOfBoundsException();
                }
                return input;
            } catch (NumberFormatException e) {
                System.out.println("Please enter number!!!");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Please enter number in range [" + min + "," + max + "]");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static int getInt(String input, int min, int max) {
        if (input == null || input.trim().isEmpty()) {
            return -1; // hoặc giá trị mặc định
        }
        try {
            Scanner sc = new Scanner(input);
            if (sc.hasNextLine()) {
                int value = Integer.parseInt(sc.nextLine().trim());
                if (value >= min && value <= max) {
                    return value;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static double getDouble(String mess, double min, double max) {
        while (true) {
            System.out.print(mess);
            try {
                double input = Double.parseDouble(scan.nextLine());
                if (input < min || input > max) {
                    throw new IllegalArgumentException("Please enter number in range [" + min + "," + max + "]");
                }

                //Đây là câu lệnh chỉ chọn những input có .5 VD 1.5 2.5... còn 3.4.. k hợp lệ
                if (input * 2 != Math.floor(input * 2)) {
                    throw new IllegalArgumentException("Please input .5 or integer number.");
                }
                return input;
            } catch (NumberFormatException e) {
                System.out.println("Please enter number!!!");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static float getFloat(String mess, float min, float max) {
        while (true) {
            System.out.println(mess);
            try {
                float input = Float.parseFloat(scan.nextLine());
                if (input < min || input > max) {
                    throw new IllegalArgumentException("Please enter number in range [" + min + "," + max + "]");
                }

                //Đây là câu lệnh chỉ chọn những input có .5 VD 1.5 2.5... còn 3.4.. k hợp lệ
                if (input * 2 != Math.floor(input * 2)) {
                    throw new IllegalArgumentException("Please input .5 or integer number.");
                }
                return input;
            } catch (NumberFormatException e) {
                System.out.println("Please enter number!!!");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static int parseStringToInt(String input) {
        try {
            return Integer.parseInt(input.trim());
        } catch (Exception e) {
            System.err.println("Không thể chuyển thành số nguyên: " + input);
            return -1; // hoặc throw exception tùy bạn muốn xử lý ra sao
        }

    }

    public static String trimSafe(String str) {
        return (str == null) ? "" : str.trim();
    }

    public static double parseStringToDouble(String input) {
        try {
            double a = Double.parseDouble(input.trim());
            return a;
        } catch (Exception e) {
            System.err.println("Không thể chuyển thành số thực: " + input);
            return -1.0; // hoặc throw exception tùy theo logic của bạn
        }
    }

    public static java.sql.Date parseStringToSqlDate(String date, String regex) {
        if (date == null || date.trim().isEmpty()) {
            return null; // hoặc throw exception tùy nhu cầu
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(regex);
            java.util.Date utilDate = sdf.parse(date);
            return new java.sql.Date(utilDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // hoặc throw
        }
    }

    public static Date parseSqlDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        try {
            return Date.valueOf(dateStr.trim());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static java.sql.Timestamp parseStringToSqlTimestamp(String dateStr, String pattern) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null; // hoặc throw new IllegalArgumentException("Date string is empty");
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            sdf.setLenient(false); // chặn parse kiểu "32/13/2024" thành ngày hợp lệ

            java.util.Date parsedDate = sdf.parse(dateStr);
            return new java.sql.Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // hoặc throw new RuntimeException("Sai định dạng ngày giờ");
        }
    }

    public static BigDecimal validateBigDecimal(String value, BigDecimal min, BigDecimal max) throws IllegalArgumentException {
        try {
            BigDecimal input = new BigDecimal(value);

            if (input.compareTo(min) < 0 || input.compareTo(max) > 0) {
                throw new IllegalArgumentException("Please enter number in range [" + min + ", " + max + "]");
            }

            // Chỉ chấp nhận .5 hoặc số nguyên
            BigDecimal multiplied = input.multiply(BigDecimal.valueOf(2));
            if (multiplied.stripTrailingZeros().scale() > 0) {
                throw new IllegalArgumentException("Please input .5 or integer number.");
            }

            return input;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format: " + value);
        }
    }
}
