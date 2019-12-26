package cz.etyka.exam.pub.exception;

public class NotAdultException extends IllegalArgumentException {
    public NotAdultException(String errMsg) {
        super(errMsg);
    }
}
