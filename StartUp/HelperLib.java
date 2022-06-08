package StartUp;

/* Emulation of static class */
public final class HelperLib {
    private HelperLib() {}

    /** Concise way of writing in the console */
    public static final void log(Object message)
    {
        System.out.println(message);
    }
}
