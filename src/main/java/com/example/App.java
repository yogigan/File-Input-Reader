package com.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.MessageFormat;
import java.util.Scanner;

public class App {

    private static final String BNI_BANK_CODE = "BNI";
    private static final String MANDIRI_BANK_CODE = "MDR";

    public static void main(String[] args) {
        try {
            // getting txt file
            File file = new File("input/Data Alert.txt");

            StringBuilder bniEnviList = new StringBuilder();
            StringBuilder mandiriEnviList = new StringBuilder();

            // read file with scanner
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {

                // check if input null
                String text = scanner.nextLine();
                if (null == text || text.isEmpty()) {
                    break;
                }

                // split text by delimiter
                String[] data = text.split(";", 5);
                String bankCode = data[0];

                // check if bank code BNI or MDR
                if (BNI_BANK_CODE.equals(bankCode)) {
                    bniEnviList.append(templateEnvi(data[1], data[2], data[4]));
                } else if (MANDIRI_BANK_CODE.equals(bankCode)) {
                    mandiriEnviList.append(templateEnvi(data[1], data[2], data[4]));
                }
            }
            scanner.close();

            // print result
            System.out.println(templateBodyEmail(BNI_BANK_CODE, bniEnviList.toString()));
            System.out.println(templateBodyEmail(MANDIRI_BANK_CODE, mandiriEnviList.toString()));

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static String templateBodyEmail(String bankCode, String enviList) {
        return MessageFormat.format(
                "Selamat Siang Rekan Bank {0}{1}" +
                        "Mohon bantuan untuk Sign on pada envi berikut :{2}" +
                        "{3}" +
                        "Terima Kasih{4}",
                bankCode, System.lineSeparator(),
                System.lineSeparator(),
                enviList,
                System.lineSeparator()
        );
    }

    public static String templateEnvi(String multiPort, String port, String status) {
        return MessageFormat.format(
                "Envi {0} Port {1} terpantau {2}{3}",
                multiPort, port, status, System.lineSeparator()
        );
    }

}
