package cz.etyka.exam.pub.exception;

public class NotEnoughCashException extends Exception {
    public NotEnoughCashException(String errorMsg){
        super(errorMsg);
    }

}
