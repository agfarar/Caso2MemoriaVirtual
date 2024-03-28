package process;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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

    public void generateReferenceColumnsToFile(String filePath) {
        try {
            File file = new File(filePath);
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Calculate number of referecolumnses
            int numberReferences = rows * columns;

            // Calculate number of virtual pages needed
            int numberPages = (int) Math.ceil((double) numberReferences * 4 / pageSize); // 4 bytes per integer

            bufferedWriter.write("pageSize: " + pageSize + "\n");
            bufferedWriter.write("rows: " + rows + "\n");
            bufferedWriter.write("columns: " + columns + "\n");
            bufferedWriter.write("NR: " + numberReferences + "\n");
            bufferedWriter.write("NP: " + numberPages + "\n");

            // Generate reference columns by pageIndex
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    int pageIndex = (i * columns + j) * 4 / pageSize;
                    bufferedWriter.write(pageIndex + "\n");
                }
            }

            bufferedWriter.close();
            System.out.println("Success!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
