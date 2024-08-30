package forestry.core.utils;

import java.math.BigDecimal;

public class MathUtil {

    private static final BigDecimal MAX_FLOAT = BigDecimal.valueOf(Float.MAX_VALUE);
    private static final BigDecimal MIN_FLOAT = BigDecimal.valueOf(-Float.MAX_VALUE);

    private static float clampToFloatRange(BigDecimal result) {
        if (result.compareTo(MAX_FLOAT) > 0) {
            return Float.MAX_VALUE;
        } else if (result.compareTo(MIN_FLOAT) < 0) {
            return -Float.MAX_VALUE;
        }
        return result.floatValue();
    }

    public static float safeMultiply(float a, float b) {
        BigDecimal bigA = BigDecimal.valueOf(a);
        BigDecimal bigB = BigDecimal.valueOf(b);
        return clampToFloatRange(bigA.multiply(bigB));
    }

    public static float safeAdd(float a, float b) {
        BigDecimal bigA = BigDecimal.valueOf(a);
        BigDecimal bigB = BigDecimal.valueOf(b);
        return clampToFloatRange(bigA.add(bigB));
    }
}
