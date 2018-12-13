package com.juja.core.array;

/**
 * ArrayUtils.
 */
public class ArrayUtils {
    /**
     * Rotate two-dimensional com.juja.core.array clockwise.
     * @param arg - arrays
     * @return rotate com.juja.core.array
     */
    public static int[][] rotateClockwise(final int[][] arg) {
        if (!isValidMatrix(arg)) return null;
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

    /**
     * Checked to valid decimal marix.
     * @param arg - matrix
     * @return true - valid or false - not valid
     */
    private static boolean isValidMatrix(final int[][] arg) {
        if (arg == null || arg.length == 0) {
            return false;
        }
        for (int[] a: arg) {
            if (a == null || a.length != arg.length) {
                return false;
            }
        }
        return true;
    }

    /**
     * То есть
     * [[[1]]] ->
     * [[[1]]]
     *
     * [  [ [1, 2],
     *      [3, 4]],
     *     [[5, 6],
     *      [7, 8]] ] ->
     *     [ [[1, 5],
     *        [2, 6]],
     *     [  [3, 7],
     *        [4, 8]] ]
     * [ [[10, 11, 12], [20, 21, 22], [30, 31, 32]],
     * [  [40, 41, 42], [50, 51, 52], [60, 61, 62]],
     * [  [70, 71, 72], [80, 81, 82], [90, 91, 92]] ] ->
     *
     * [[ [10, 40, 70], [11, 41, 71], [12, 42, 72]],
     * [  [20, 50, 80], [21, 51, 81], [22, 52, 82]],
     * [  [30, 60, 90], [31, 61, 91], [32, 62, 92]] ] ...
     * @param arg
     * @return
     */
    public static int[][][] rotateClockwise(int[][][] arg) {
        /*BODY*/

        return null;
    }

    public static int[][] mul(int[][] fst, int[][] snd) {
        int columnsOfFirstMatrix = fst[0].length;
        int rowsOfSecondMatrix = snd.length;
        int rowsOfFirstMatrix = fst.length;
        int columnsofSecondMatrix = snd[0].length;
        int rows = fst[0].length;
        int column = snd.length;

        if (columnsOfFirstMatrix != rowsOfSecondMatrix) {
            throw new IllegalArgumentException();
        }
//        if (fst == null || snd == null || fst.length == 0 || snd.length == 0) {
//            return null;
//        }
//        int height = fst.length;
//        int weight = snd[0].length;
//        for (int[] f: fst) {
//            if (f == null || f.length != weight) {
//                return null;
//            }
//        }
//        for (int[] s : snd) {
//            if (s == null || s.length != height) {
//                return null;
//            }
//        }
        int size = (rowsOfFirstMatrix > columnsofSecondMatrix) ? rowsOfFirstMatrix : columnsofSecondMatrix;
        int[][] result = new int[rowsOfFirstMatrix][columnsofSecondMatrix];;
        for (int i = 0; i < column; i++) {
            for (int j = 0; j < rows; j++) {
                result[i][j] = 0;
                for (int k = 0; k < size; k++) {
                    result[i][j] += fst[i][k] * snd[k][j];
                }
                System.out.print(result[i][j] + " ");
            }
            System.out.println();
        }
        return result;
    }

    public static void main(String[] args) {
        int a[][] = { { 1, 2}, { 7, 8}, { 3, 4} };

        int b[][] = { { 3, 4, 5}, { 9, 0, 1}};
        int c[][] = mul(a, b);
    }

}
