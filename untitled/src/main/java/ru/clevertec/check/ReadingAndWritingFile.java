package ru.clevertec.check;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ReadingAndWritingFile {

    private final static List<List<String>> clearedTextList = new ArrayList<>();

    private final static String inputFileNameProducts = "src/main/resources/products.csv";
    private final static String inputFileNameDiscountCards = "src/main/resources/discountCards.csv";


    public static void copyTextProducts (){
        ReadingAndWritingFile.copyText(inputFileNameProducts);
        ReadingAndWritingFile.divideIntoSheetsProducts(clearedTextList);
        clearedTextList.clear();
    }


    public static void copyTextDiscountCards (){
        ReadingAndWritingFile.copyText(inputFileNameDiscountCards);
        ReadingAndWritingFile.divideIntoSheetsDiscountCards(clearedTextList);
        clearedTextList.clear();
    }


    private static void copyText(String inputFileName) {

        List<String> textWithoutEditing = new ArrayList<>();
        String[][] clearedTextArray;

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                textWithoutEditing.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        clearedTextArray = new String[textWithoutEditing.size()][];

        ReadingAndWritingFile.writeToArray(textWithoutEditing,clearedTextArray);
        ReadingAndWritingFile.twoDArrayToList(clearedTextArray,clearedTextList);
    }



    private static void writeToArray(List<String> list, String [][] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i).split(",");
        }
    }


    private static void twoDArrayToList(String[][] twoDArray, List <List<String>> list) {
        for (String[] strings : twoDArray) {
            List<String> textWithoutEditing = new ArrayList<>(Arrays.asList(strings));
            list.add(textWithoutEditing);
        }
    }


    private static void divideIntoSheetsProducts (List<List<String>> clearedTextList) {
        for (List<String> list : clearedTextList) {

            Database.idProducts.add(Integer.parseInt(list.get(0)));
            Database.descriptionProducts.add(list.get(1));
            Database.priceProducts.add(Double.parseDouble(list.get(2)));
            Database.quantityInStockProducts.add(Integer.parseInt(list.get(3)));
            Database.wholesaleProductProducts.add(list.get(4));
        }
    }


    private static void divideIntoSheetsDiscountCards (List<List<String>> clearedTextList) {
        for (List<String> list : clearedTextList) {

            Database.idDiscountCards.add(Integer.parseInt(list.get(0)));
            Database.numberDiscountCards.add(Integer.parseInt(list.get(1)));
            Database.discountAmount.add(Integer.parseInt(list.get(2)));

        }
    }

    private static void gg(){
        Database.idDiscountCards.forEach(System.out::println);
    }


    public static void WritFileCSV(List<StringBuilder> list) {
        try {
            FileWriter writer = new FileWriter("result.csv");

            for(StringBuilder stringBuilder : list){
                writer.write(stringBuilder.toString()+"\n");
            }

            writer.close();

            System.out.println("CSV файл успешно создан!");
        } catch (IOException e) {
            System.err.println("Ошибка при записи файла: " + e.getMessage());
        }
    }
}