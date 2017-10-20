package pl.pawc.exchangerates;

public class Datafeed{
    public static void main(String[] args){
        IParser parser = new Parse();
        parser.parse("PLN");
    }
}