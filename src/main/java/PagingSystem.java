import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PagingSystem {

    private volatile boolean agingAlgorithmStop;
    private int[][] pageTable;
    private int frames;
    private String referenceFile;
    private int hits;
    private int misses;
    private int[] ageBits;
    //private Process process;

    public PagingSystem(int frames, String referenceFile) {
        this.frames = frames;
        this.referenceFile = referenceFile;
        this.pageTable = new int[frames][2]; // The page table has two columns for frame number and R bit
        this.hits = 0;
        this.misses = 0;
        this.ageBits = new int[frames];

    }

    public int[][] readReferencesFromFile(String fileName) {
        try {
            File file = new File("data/" + fileName);
            Scanner scanner = new Scanner(file);

            int numberReferences = 0;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.startsWith("NR=")) {
                    numberReferences = Integer.parseInt(line.substring(3));
                    break;

                }
            }

            int[][] references = new int[numberReferences][4];

            // Re-read the file
            scanner = new Scanner(file);

            int index = 0;
            while (scanner.hasNextLine()) {
                String newLine = scanner.nextLine();
                String[] parts = newLine.split(",");
                for (int i = 0; i < parts.length; i++) {
                    references[index][i] = Integer.parseInt(parts[i].trim());
                }
                index++;
            }

            scanner.close();
            return references;
            } catch(IOException e){
                e.printStackTrace();
                return null;
            }

    }

    public void simulatePagingSystem() {
        int[][] references = readReferencesFromFile(referenceFile); // Read references from file

        // Process each reference from the file
        for (int[] reference : references) {
            String action = reference[3] == 'R' ? "R" : "W"; // if-else shortcut
            int pageIndex = reference[1];
            int frameIndex = -1;

            // Check if the page is in RAM
            for (int i = 0; i < frames; i++) {
                if (pageTable[i][0] == pageIndex) {
                    frameIndex = i;
                    break;
                }
            }

            if (frameIndex == -1) { // If the page is not found, it's a miss
                misses++;
                frameIndex = agingAlgorithmPageReplacement();
            } else { // If the page is found, it's a hit
                hits++;
                ageBits[frameIndex] >>= 1; // Right shift to 'age' the bit
                ageBits[frameIndex] |= 0x80; // Set leftmost bit to 1
            }

            // Simulate time taken for reading or writing from/to RAM
            try {
                Thread.sleep(30*1000000); // Sleep for 30 nanoseconds (simulation of RAM access time)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private int agingAlgorithmPageReplacement() {
        int minAge = ageBits[0];
        int minIndex = 0;
        for (int i = 1; i < frames; i++) {
            if (ageBits[i] < minAge) {
                minAge = ageBits[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    public void runSimulationWithThreads() {

        // Execute the aging algorithm
        ScheduledExecutorService agingScheduler = Executors.newScheduledThreadPool(1);
        agingScheduler.scheduleAtFixedRate(this::updateAgeBits, 0, 4, TimeUnit.MILLISECONDS);

        // Execute the page update thread
        ScheduledExecutorService pageUpdateScheduler = Executors.newScheduledThreadPool(1);
        pageUpdateScheduler.scheduleAtFixedRate(this::updatePageTable, 0, 1, TimeUnit.MILLISECONDS);

        // Wait for both threads to finish
        try {
            agingScheduler.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            pageUpdateScheduler.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void updateAgeBits() {
        for (int i = 0; i < frames; i++) {
            ageBits[i] >>= 1; // Aging by moving to the right
        }
    }

    private void updatePageTable() {
        for (int i = 0; i < frames; i++) {
            if (ageBits[i] == 0) {
                pageTable[i][0] = -1;
            }
        }
        agingAlgorithmStop = true;
    }

    public int getHits() {
        return hits;
    }

    public int getMisses() {
        return misses;
    }
}





