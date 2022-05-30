package Exceptions;

/** Signifies yet non-functional code parts. Usually caused when extending application functionallity */
public class UnimplementedException extends Exception{
    /** Default message */
    public UnimplementedException()
    {
        super("A block statement is not yet functional");
    }
    /** Detailed message message */
    public UnimplementedException(String message)
    {
        super(message);
    }
}
