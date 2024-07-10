package ru.clevertec.check;


import java.util.*;

public class GenerationInputData {


    public static void print (String string) throws BadRequestException {
        final String REGEX = "(\\d+-\\d+\\s)+(discountCard=\\d+\\s)(balanceDebitCard=\\d+)";
        final String [] STRING_REGEX = {"(\\d+-\\d+)","(discountCard=\\d+)","(balanceDebitCard=\\d+)"};

        String[] splitStrings = string.split(" ");

        if (!string.matches(REGEX)) {
            throw new BadRequestException("Не верно заполнены данные");
        }

        for(String str : splitStrings){
            if(str.matches(STRING_REGEX[0])){
                String[] splitIdQuantity = str.split("-");
                Database.listsIdQuantity.add(new ArrayList<>(Arrays.asList(Integer.parseInt(splitIdQuantity[0]),Integer.parseInt(splitIdQuantity[1]))));

            } else if(str.matches(STRING_REGEX[1])){
                Database.listDiscountCard.add(Integer.parseInt(str.replaceAll("(discountCard=)","")));

            } else if(str.matches(STRING_REGEX[2])){
                Database.listBalanceDebitCard.add(Integer.parseInt(str.replaceAll("(balanceDebitCard=)","")));
            }
        }
    }
}
