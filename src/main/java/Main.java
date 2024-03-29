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
 */
    public void menu(){
        /*

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
                -> Calculates the resultant data
                -> ... and more

         */

    }
    public static void main(String[] args){


        while (true) {

            Scanner scanner = new Scanner(System.in);
            System.out.println("You have to options:\n1. Generate the references\n2. Calculate the references\n3. Exit the program");

            int option = scanner.nextInt();

            if (option == 3)break;

            else if (option == 1){
                System.out.print("Enter the size of the page in KB: ");
                int pageSize = scanner.nextInt();

                System.out.print("Enter the size of the matrix: ");
                int sizeMatrix = scanner.nextInt();

            }

            else if (option == 2) {

                System.out.println("");

            }
        }
    }
}
