package array;

/**
 * ArrayUtils.
 */
public class ArrayUtils {
    /**
     * Rotate two-dimensional array clockwise.
     * @param arg - arrays
     * @return rotate array
     */
    public static int[][] rotateClockwise(final int[][] arg) {
        if (arg == null || arg.length == 0) {
            return null;
        }
        for (int[] a: arg) {
            if (a == null || a.length != arg.length) {
                return null;
            }
        }
        int[][] result = arg.clone();

        int len = result.length;
        int temp;
        for (int x = 0; x < len / 2; x++) {
            for (int y = 0; y < len - 2 * x - 1; y++) {
                temp = result[x][x + y];
                result[x][x + y] = result[len - x - y - 1][x];
                result[len - x - y - 1][x] = result[len - x - 1][len - x - y - 1];
                result[len - x - 1][len - x - y - 1] = result[x + y][len - x - 1];
                result[x + y][len - x - 1] = temp;
            }
        }
            return result;
    }

}
