package array;

import java.util.Arrays;
import java.util.Random;

public class Arr {

    public static void invert(int[] arr) {
        if (arr != null && arr.length > 1) {
            for (int k = arr.length - 1; k >= arr.length / 2; k--) {
                int tmp = arr[k];
                arr[k] = arr[arr.length - k - 1];
                arr[arr.length - k - 1] = tmp;
            }
        }
    }

    /**
     * Исходящий массив, для простоты, должен быть
     * того же размера, что и входящий. Например,
     * для {4, 3, 5, 6, 7, 9} -> {4, 6, 0, 0, 0, 0}.
     * @param nums - int
     * @return result
     */
    public static int [] filterEven(int [] nums){
        int[] result = new int[nums.length];
        int idxNewArr = 0;
        for (int n : nums) {
            if (n % 2 == 0) {
                result[idxNewArr++] = n;
            }

        }
        return result;
    }

    public static int[] merge(int[] fst, int[] snd) {
        if (fst.length == 0) {
            return snd;
        }
        if (snd.length == 0) {
            return fst;
        }

        int[] result = new int[fst.length + snd.length];
        System.arraycopy(fst, 0, result, 0, fst.length);
        System.arraycopy(snd, 0, result, fst.length, snd.length);
        Arrays.sort(result);
        return result;
    }

    public static int[] merge2(int[] fst, int[] snd) {
        if (fst.length == 0) {
            return snd;
        }
        if (snd.length == 0) {
            return fst;
        }
        int[] result = new int[fst.length + snd.length];
        int fstIndex = 0;
        int sndIndex = 0;
        while (fstIndex + sndIndex != result.length) {
            if (fstIndex == fst.length) {
                result[fstIndex + sndIndex] = snd[sndIndex++];
            } else if (sndIndex == snd.length) {
                result[fstIndex + sndIndex] = fst[fstIndex++];
            } else if ((fst[fstIndex] < snd[sndIndex])) {
                result[fstIndex + sndIndex] = fst[fstIndex++];
            } else {
                result[fstIndex + sndIndex] = snd[sndIndex++];
            }
        }
        return result;
    }

    private static int[] randomArr(int capacity) {
        Random rdm = new Random();
        if (capacity < 0) {
            return new int[0];
        }
        int[] result = new int[capacity];
        for (int i = 0; i < capacity; i++) {
            result[i] = rdm.nextInt(1000);
        }
        Arrays.sort(result);
        return result;
    }
    public static void main(String[] args) {

        int[] array = randomArr(20_000_000);
        int[] array2 = randomArr(20_000_000);

        long start1 = System.currentTimeMillis();

        merge2(array, array2);

        long end1 = System.currentTimeMillis();

        System.out.println(end1 - start1);

        long start2 = System.currentTimeMillis();

        merge(array, array2);

        long end2 = System.currentTimeMillis();

        System.out.println(end2 - start2);
    }
}
