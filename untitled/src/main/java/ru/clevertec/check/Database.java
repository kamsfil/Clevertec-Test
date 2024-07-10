package ru.clevertec.check;

import java.util.ArrayList;
import java.util.List;

public class Database {

    protected static final List<Integer> idProducts = new ArrayList<>();
    protected static final List<String> descriptionProducts = new ArrayList<>();
    protected static final List<Double> priceProducts = new ArrayList<>();
    protected static final List<Integer> quantityInStockProducts = new ArrayList<>();
    protected static final List<String> wholesaleProductProducts = new ArrayList<>();


    protected static final List<Integer> idDiscountCards = new ArrayList<>();
    protected static final List<Integer> numberDiscountCards = new ArrayList<>();
    protected static final List<Integer> discountAmount = new ArrayList<>();


    protected static List<List<Integer>> listsIdQuantity = new ArrayList<>();
    protected static List<Integer> listDiscountCard = new ArrayList<>();
    protected static List<Integer> listBalanceDebitCard = new ArrayList<>();


    protected static List<StringBuilder> listCheckProducts = new ArrayList<>();
    protected static List<StringBuilder> exceptionWork = new ArrayList<>();
}

