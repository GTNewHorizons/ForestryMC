package forestry.core.multiblock;

/**
 * An exception thrown when trying to validate a multiblock. Requires a string describing why the multiblock could not
 * assemble.
 * 
 * @author Erogenous Beef
 */
public class MultiblockValidationException extends Exception {

    private static final long serialVersionUID = -3255564315819077747L;

    public MultiblockValidationException(String reason) {
        super(reason);
    }
}
