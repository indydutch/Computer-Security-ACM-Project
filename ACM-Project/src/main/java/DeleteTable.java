/*
 * DeleteTable.java
 * Matthew Dutchess & Calvin Hariprasad
 * Section 1
 * Date Last Modified: 11/11/2021
 */

/*
 * File Description: deletes the table from the database
 */

/*
 * Run Instructions:
 * Just run the program per usual. No user output is expect
 */

public class DeleteTable {
    public static void main(String[] args) // main function for the command detection
    {
        TableCreation testObj = new TableCreation(); // Creates a new TableCreation object

        testObj.deleteTable();
    }
}
