package process;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Process {
    private int[][] matrix1;
    private int rows;
    private int columns;
    private int pageSize;

    public Process(int[][] matrix1, int rows, int columns, int pageSize) {
        this.matrix1 = matrix1;
        this.rows = rows;
        this.columns = columns;
        this.pageSize = pageSize;
    }

    public void generateReferenceColumnsToFile(String fileName) {
        try {
            File file = new File("data/" + fileName);
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Apply the filter to the input matrix
            int[][] filteredMatrix = applyFilter(matrix1, rows, columns);

            // Calculate number of references
            int numberReferences = rows * columns * 2;
            int numberPages = (int) Math.ceil((double) numberReferences * 4 / pageSize); // 4 bytes per integer

            bufferedWriter.write("TP=" + pageSize + "\n");
            bufferedWriter.write("NF=" + rows + "\n");
            bufferedWriter.write("NC=" + columns + "\n");
            bufferedWriter.write("NF_NC_Filtro=3\n"); // Assuming filter size is always 3
            bufferedWriter.write("NR=" + numberReferences + "\n");
            bufferedWriter.write("NP=" + numberPages + "\n");

            // Generate reference columns for matrix of data
            generateReferenceColumns(bufferedWriter, matrix1, "M");

            // Generate reference columns for Result matrix
            generateReferenceColumns(bufferedWriter, filteredMatrix, "R");

            bufferedWriter.close();
            System.out.println("Success on creating the references!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to generate reference columns for a matrix
    private void generateReferenceColumns(BufferedWriter writer, int[][] matrix, String prefix) throws IOException {
        Random random = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                String action = random.nextBoolean() ? "R" : "W";
                int pageIndex = (matrix[i][j] * rows * columns + i * columns + j) * 4 / pageSize;
                writer.write(prefix + "[" + i + "][" + j + "]," + pageIndex + "," + (i * columns + j) * 4 % pageSize + "," + action + "\n");
            }
        }
    }

    // Method for applying filter to matrix
    private int[][] applyFilter(int[][] mat1, int nf, int nc) {
        int[][] mat3 = new int[nf][nc];
        int[][] mat2 = {
                {1, 1, 1},
                {1, 1, 1},
                {1, 1, 1}
        };

        for (int i = 0; i < nf - 1; i++) {
            for (int j = 0; j < nc - 1; j++) {

                int acum = 0;
                for (int a = -1; a <= 1; a++) {
                    for (int b = -1; b <= 1; b++) {
                        int i2 = i + a;
                        int j2 = j + b;
                        int i3 = 1 + a;
                        int j3 = 1 + b;

                        if (i2 >= 0 && i2 < nf && j2 >= 0 && j2 < nc)
                            acum += (mat2[i3][j3] * mat1[i2][j2]);
                    }
                }
                if (acum >= 0 && acum <= 255)
                    mat3[i][j] = acum;
                else if (acum < 0)
                    mat3[i][j] = 0;
                else
                    mat3[i][j] = 255;
            }
        }

        for (int i = 0; i < nc; i++) {
            mat3[0][i] = 0;
            mat3[nf - 1][i] = 255;
        }
        for (int i = 1; i < nf - 1; i++) {
            mat3[i][0] = 0;
            mat3[i][nc - 1] = 255;
        }

        return mat3;
    }
}




