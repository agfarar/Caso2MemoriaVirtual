import java.util.Scanner;

public class Main {
/*
    Goals:
           - Write a program that simulates de memory admin and calculates the # of page faults and
             the % of data hits in RAM.
           - Calculate the time with hit and misses vs the time. When a page is on RAM vs all the R lead to failure.
                                                                                            -> (Need to be written implement code to see the time)

    Definitions:
        hit : Is the fetch of a page that is in RAM.
        miss :  Is the fetch of a page that generates a fault page.

    Restrictions: There is only one process in the simulation.




        First Option:

            Ask user size of the page
            Ask user size of the matrix
                -> Generates a file with the corresponding references (with multiplication method)
                -> The file need to be with this info:

                        1. TP: size of page
                        2. NF: #rows in the matrix
                        3. NC: #columns in the matrix
                        4. NR: #references in the file
                        5. NP: #virtual pages in the process
                        6. All the generated references

        Second Option:

            Ask user # of frames
            Ask user the name of the file with the references
            The program needs to simulate
                1. The process behavior - charge the references one by one (i.e. reading the file???)
                2. The behavior of paginating system
                                                -> identify when a fault page occurs
                                                -> take decisions based on NRU algorithm or LRU or Aging Algorithm

                3. Have the count of the number of hits and faults in the course of the program (only lecture)

                    Additional things:
                        -> A thread need to update the state of the pages and the frames in RAM (it need to run every 1 millisecond)
                        -> Another thread is in charge of the R bit updating (aging algorithm) and it needs to run every 4 millisecond
                        -> To deduct the variation of the results search the first entry of DS

         */

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("You have two options:");
            System.out.println("1. Generate references");
            System.out.println("2. Calculate references");
            System.out.println("3. Exit the program");
            System.out.print("Enter your choice: ");

            int option = scanner.nextInt();

            if (option == 3) {
                System.out.println("Exiting the program...");
                break;
            } else if (option == 1) {
                System.out.print("Enter the page size in KB: ");
                int pageSize = scanner.nextInt();

                System.out.print("Enter the size of the matrix: ");
                int sizeMatrix = scanner.nextInt();

                // NOTE: I don't know what initial matrix do I need to pass to the Process. Its random numbers only ones???
                // NOTE: I repeated the data sizeMatrix because I don't know if the rows and columns are the same or not
                //Process process = new Process(matrix1,sizeMatrix,sizeMatrix,pageSize);

                //it generates the file of references within the constructor

                System.out.println("References generated successfully!");
            } else if (option == 2) {
                System.out.println("Enter the number of page frames: ");
                int numberOfPageFrames = scanner.nextInt();

                System.out.print("Enter the name of the file with the references (including extension): ");
                String fileName = scanner.next();

                //NOTE: Same thing here
                //NOTE: But we have an issue because we need a p
                PagingSystem pagingSystem = new PagingSystem(numberOfPageFrames, fileName);
                //NOTE: I don't know where to run the simulatePagingSystem() as another thread as a principal thread or at the constructor
                pagingSystem.runSimulationWithThreads();

                System.out.println("Hits: " + pagingSystem.getHits());
                System.out.println("Misses: " + pagingSystem.getMisses());
            } else {
                System.out.println("Not a valid option!!");
            }
        }
        scanner.close();
    }

}
