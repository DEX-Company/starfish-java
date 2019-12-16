package sg.dex.starfish.exception;

/**
 * Exception class covering Resolver related exceptions
 *
 * @author Ilia
 */
@SuppressWarnings("serial")
public class ResolverException extends RuntimeException {
    /**
     * API to create Resolver Exception instance
     *
     * @param e Throwable
     */
    public ResolverException(Throwable e) {
        super(e);
    }

    /**
     * API to create Resolver Exception instance
     *
     */
    public ResolverException() {
        super();
    }
}
