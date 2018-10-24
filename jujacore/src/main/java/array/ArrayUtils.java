package array;

import java.util.Arrays;

public class ArrayUtils {

    public static int[][] rotateClockwise(final int[][] arg) {
        int[][] result = arg;
        if (result == null ) {
            return null;
        }
        for (int[] ar : result) {
            if (ar == null || ar.length == 0 || ar.length != result.length) {
                return null;
            }
        }
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
    /**
     * Код не прошел тесты:
     *
     * Код теста который вызвал ошибку:
     * public class Test {
     *     public static void main(String[] args) {
     *         Object res = ArrayUtils.rotateClockwise(new int[0][]);
     *         if (res != null) {
     *         	throw new AssertionError();
     *         }
     *         System.out.print("OK");
     *     }
     * }
     */
    public static void main(String[] args) {
        int[][] mat = { { 1, 2, 3 }, { 4, 5, 6}, { 7, 8, 9} };
        int[][] newMatx = rotateClockwise(mat);
        System.out.println(Arrays.deepToString(newMatx));


        Object res = ArrayUtils.rotateClockwise(new int[0][]);
        System.out.println(res); // как вернуть null?

    }
}
