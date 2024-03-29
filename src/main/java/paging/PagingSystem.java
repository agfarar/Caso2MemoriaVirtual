package paging;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PagingSystem {
    private int[][] pageTable;
    private int frames;
    private String referenceFile;
    private int hits;
    private int misses;

    public PagingSystem(int frames, String referenceFile) {
        this.frames = frames;
        this.referenceFile = referenceFile;
        this.pageTable = new int[frames][2]; // The page table has two columns for frame number and R bit
        this.hits = 0;
        this.misses = 0;
    }

    public void simulatePagingSystem() {

    }

    public void runSimulationWithThreads() {
        ExecutorService executor = Executors.newFixedThreadPool(2); // It easier to manage the threads and the pool of threads
                                                                            // -> But is replaceable

        executor.submit(this::updatePageTableAndRAM);
        executor.submit(this::executeClockAlgorithm);


        executor.shutdown();
    }

    private void updatePageTableAndRAM() {

    }

    private void executeClockAlgorithm() {

    }

    public int getHits() {
        return hits;
    }

    public int getMisses() {
        return misses;
    }
}




    /*
    public void example(){
        for (int i = 0; i < nf-1; i++) {
            for (int j = 0; j < nc-1; j++) {
                //Recorrer los vecinos y aplicar el filtro
                //mat1 : matriz de datos
                //mat2 : matriz con el filtro (usaremos un filtro de 3x3 para resaltar bordes)
                //mat3 : matriz resultante

                int acum = 0;
                for (int a = -1; a <=1 ; a++) {
                    for (int b = -1; b <=1 ; b++) {
                        int i2 = i+a;
                        int j2 = j+b;
                        int i3 = 1+a;
                        int j3 = 1+b;

                        acum += (mat2[i3][j3]*mat1[i2][j2]);
                    }
                }
                if (acum >= 0 && acum <= 255) mat3[i][j] = acum;
                else if (acum<0) mat3[i][j] = 0;
                else mat3[i][j] = 255;

                // se asigna un valor predefinido a los bordes

                for(int i=0; i < nc ; i++){
                    mat3[0][i] = 0;
                    mat3[nf-1][i] = 255;
                }
                for (int i = 1; i < nf-1; i++){
                    mat3[i][0] = 0;
                    mat3[i][nc-1] = 255;
                }
            }

        }
    }

     */