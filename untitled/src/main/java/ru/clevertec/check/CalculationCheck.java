package ru.clevertec.check;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CalculationCheck {

    static DecimalFormat decimalFormat = new DecimalFormat("0.00");
    static Integer diskont = 2;


    static double totalPrice = 0;
    static double totalDiscount = 0;

    static Integer[] id;
    static Integer[] quantityId;

    static Integer skidka = 0;



    public static void timeAndDateCheck () throws NotEnoughMoneyException, BadRequestException {

        CalculationCheck.priceOne();
        CalculationCheck.CSV_FILE();

        String dateCheck = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String timeCheck = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        Database.listCheckProducts.add(new StringBuilder()
                .append("Date").append(";").append("Time"));

        Database.listCheckProducts.add(new StringBuilder()
                .append(dateCheck).append(";").append(timeCheck).append("\n"));

        Database.listCheckProducts.add(new StringBuilder()
                .append("QTY").append(";").append("DESCRIPTION").append(";").append("PRICE").append(";").append("DISCOUNT").append(";").append("TOTAL"));
        CalculationCheck.CSV();


        Database.listCheckProducts.add(new StringBuilder()
                .append("DISCOUNT CARD").append(";").append("DISCOUNT PERCENTAGE"));
        CalculationCheck.CSV_DISCOUNT();

        Database.listCheckProducts.add(new StringBuilder()
                .append("TOTAL PRICE").append(";").append("TOTAL DISCOUNT").append(";").append("TOTAL WITH DISCOUNT"));
        CalculationCheck.CSV_TOTAL();
    }



    private static void priceOne() throws BadRequestException {

        id = new Integer[Database.listsIdQuantity.size()];
        quantityId = new Integer[id.length];

        //записал в массивы пары значений
        int in = 0;
        for (List<Integer> list : Database.listsIdQuantity) {
            id[in] = list.get(0);
            quantityId[in] = list.get(1);
            in++;
        }

        for(Integer id : id) {
            if(id > (Database.idProducts.size()-1) || id < 1) {
                throw new BadRequestException ("Данного товара нет для продажи");
            }
        }


        for (int p = 0; p < Database.numberDiscountCards.size(); p++) {
            if (Database.listDiscountCard.getFirst().equals(Database.numberDiscountCards.get(p))) {
                skidka = Database.discountAmount.get(p);
                break;
            } else {
                skidka = 2;
            }
        }
    }


    private static void CSV_FILE () throws NotEnoughMoneyException {
        for (int i = 0; i < id.length; i++) {
            for (int k = 0; k < Database.idProducts.size(); k++) {
                if (Database.idProducts.get(k).equals(id[i])) {
                    totalPrice = totalPrice + Database.priceProducts.get(k) * quantityId[i];
                    if (Database.wholesaleProductProducts.get(k).equals("+")) {
                        if (quantityId[i] > 4) {
                            totalDiscount = totalDiscount + Database.priceProducts.get(k) * quantityId[i] * 10 / 100;
                        }
                    }else {
                        totalDiscount = totalDiscount + Database.priceProducts.get(k) * quantityId[i] * skidka / 100;
                    }
                }
            }
        }
        if(totalPrice-totalDiscount>Database.listBalanceDebitCard.getFirst()){
            throw new NotEnoughMoneyException("Недостаточно средств на дебетовой карте");
        }
    }


    private static void CSV (){
        for (int i = 0; i < id.length; i++) {
            for (int k = 0; k < Database.idProducts.size(); k++) {
                if (id[i].equals(Database.idProducts.get(k))) {

                    Integer idTut = id[i];
                    Integer quantityTut = quantityId[i];

                    diskont = skidka;
                    if (Database.wholesaleProductProducts.get(k).equals("+")) {
                        if (quantityId[i] > 4) {
                            diskont = 10;
                        }
                    }

                    Database.listCheckProducts.add(new StringBuilder()
                            .append(quantityTut)
                            .append(";")
                            .append(Database.descriptionProducts.get(k))
                            .append(";")
                            .append(decimalFormat.format(Database.priceProducts.get(k))).append("$")
                            .append(";")
                            .append(decimalFormat.format(Database.priceProducts.get(k) * quantityTut * diskont / 100)).append("$")
                            .append(";")
                            .append(decimalFormat.format(Database.priceProducts.get(k) * quantityTut)).append("$")
                            .append(";"));
                }
            }
        }
        Database.listCheckProducts.add(new StringBuilder());
    }



    private static void CSV_DISCOUNT (){
        Database.listCheckProducts.add(new StringBuilder()
                .append((Database.listDiscountCard.getFirst()))
                .append(";")
                .append(skidka).append("%").append("\n"));
    }



    private static void CSV_TOTAL (){
        Database.listCheckProducts.add(new StringBuilder()
                .append(decimalFormat.format(totalPrice)).append("$")
                .append(";")
                .append(decimalFormat.format(totalDiscount)).append("$")
                .append(";")
                .append(decimalFormat.format(totalPrice - totalDiscount)).append("$"));
    }
}
