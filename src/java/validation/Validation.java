/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validation;

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
    public static int getInt(String mess, int min, int max) {
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

    

    public static double parseStringToDouble(String input) {
        try {
            double a = Double.parseDouble(input.trim());
            return a;
        } catch (Exception e) {
            System.err.println("Không thể chuyển thành số thực: " + input);
            return -1.0; // hoặc throw exception tùy theo logic của bạn
        }
    }

}
