package forestry.core.utils;

public class MathUtil {

    public static float safeMultiply(float a, float b) {
        if (a > 0 && b > 0 && a > Float.MAX_VALUE / b) {
            return Float.MAX_VALUE;
        } else if (a > 0 && b < 0 && a > Float.MIN_VALUE / b) {
            return Float.MIN_VALUE;
        } else if (a < 0 && b > 0 && a < Float.MIN_VALUE / b) {
            return Float.MIN_VALUE;
        } else if (a < 0 && b < 0 && a < Float.MAX_VALUE / b) {
            return Float.MAX_VALUE;
        } else {
            return a * b;
        }
    }

    public static float safeAdd(float a, float b) {
        if (a > 0 && b > 0 && a > Float.MAX_VALUE - b) {
            return Float.MAX_VALUE;
        } else if (a < 0 && b < 0 && a < Float.MIN_VALUE - b) {
            return Float.MIN_VALUE;
        } else {
            return a + b;
        }
    }

}
