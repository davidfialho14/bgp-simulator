package policies.exceptions;

public class InvalidTagException extends Exception {

    protected String tag;

    public InvalidTagException(String tag, String message) {
        super(message);
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}
