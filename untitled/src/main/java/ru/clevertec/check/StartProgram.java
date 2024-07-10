package ru.clevertec.check;

public class StartProgram {

    public static void startWork (String string) {

        ReadingAndWritingFile.copyTextProducts();

        ReadingAndWritingFile.copyTextDiscountCards();



        try {
            GenerationInputData.print(string);
            CalculationCheck.timeAndDateCheck();
            ReadingAndWritingFile.WritFileCSV(Database.listCheckProducts);

        } catch (NotEnoughMoneyException | BadRequestException l) {
            ReadingAndWritingFile.WritFileCSV(Database.exceptionWork);
        }


    }
}
