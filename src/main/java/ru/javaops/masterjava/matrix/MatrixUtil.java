package ru.javaops.masterjava.matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * gkislin
 * 03.07.2016
 */
public class MatrixUtil {

    public static int[][] concurrentMultiply(int[][] matrixA, int[][] matrixB, ExecutorService executor) {
        final CompletionService<Integer> completionService = new ExecutorCompletionService<>(executor);
        final int matrixSize = matrixA.length;
        final int[][] matrixC = new int[matrixSize][matrixSize];
        List<Future<Integer>> futures = new ArrayList<>(matrixSize);

        for (int j = 0; j < matrixSize; j++) {
            int[] curColumn = new int[matrixSize];
            int columnIndex = j;

            Future<Integer> future = completionService.submit(() -> {
                for (int k = 0; k < matrixSize; k++) {
                    curColumn[k] = matrixB[k][columnIndex];
                }

                for (int i = 0; i < matrixSize; i++) {
                    int[] curRow = matrixA[i];
                    int sum = 0;
                    for (int k = 0; k < matrixSize; k++) {
                        sum += curRow[k] * curColumn[k];
                    }
                    matrixC[columnIndex][i] = sum;
                }
                return columnIndex;
            });

            futures.add(future);
        }

        while(!futures.isEmpty()) {
            Future<Integer> completeFuture = completionService.poll();
            if (completeFuture != null) {
                futures.remove(completeFuture);
            }
        }
        return matrixC;
    }

    public static int[][] singleThreadMultiply(int[][] matrixA, int[][] matrixB) {
        final int matrixSize = matrixA.length;
        final int[][] matrixC = new int[matrixSize][matrixSize];
        int[] curColumn = new int[matrixSize];

        for (int j = 0; j < matrixSize; j++) {
            for (int k = 0; k < matrixSize; k++) {
                curColumn[k] = matrixB[k][j];
            }

            for (int i = 0; i < matrixSize; i++) {
                int[] curRow = matrixA[i];
                int sum = 0;
                for (int k = 0; k < matrixSize; k++) {
                    sum += curRow[k] * curColumn[k];
                }
                matrixC[j][i] = sum;
            }
        }
        return matrixC;
    }

    public static int[][] create(int size) {
        int[][] matrix = new int[size][size];
        Random rn = new Random();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = rn.nextInt(10);
            }
        }
        return matrix;
    }

    public static boolean compare(int[][] matrixA, int[][] matrixB) {
        final int matrixSize = matrixA.length;
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                if (matrixA[i][j] != matrixB[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
