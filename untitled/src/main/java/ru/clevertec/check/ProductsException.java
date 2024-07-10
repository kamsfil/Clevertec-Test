package ru.clevertec.check;

public class ProductsException extends Exception{
    public ProductsException(String message){
        super(message);
    }
}

class BadRequestException extends Exception{
    public BadRequestException(String message){
        super(message);
        Database.exceptionWork.add(new StringBuilder()
                .append("ERROR")
                .append(";")
                .append("\n")
                .append("BAD REQUEST")
                .append(";"));
    }
}

class NotEnoughMoneyException extends Exception{
    public NotEnoughMoneyException(String message){
        super(message);
        Database.exceptionWork.add(new StringBuilder()
                .append("ERROR")
                .append(";")
                .append("\n")
                .append("NOT ENOUGH MONEY")
                .append(";"));
    }
}

class InternalServerMoeyException extends Exception{
    public InternalServerMoeyException(String message){
        super(message);
        Database.exceptionWork.add(new StringBuilder()
                .append("ERROR")
                .append(";")
                .append("\n")
                .append("INTERNAL SERVER ERROR ")
                .append(";"));
    }
}


