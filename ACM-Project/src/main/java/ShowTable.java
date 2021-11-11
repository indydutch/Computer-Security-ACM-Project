/*
 * ShowTable.java
 * Matthew Dutchess & Calvin Hariprasad
 * Section 1
 * Date Last Modified: 11/11/2021
 */

/*
 * File Description: Shows the default generated ACM
 */

/*
 * Run Instructions:
 * Just run the program per usual.
 * The table will be printed to the user.
 * Note: the column names will not be listed.
 */

public class ShowTable {
    public static void main(String[] args) {
        TableCreation deleteTable = new TableCreation();

        deleteTable.deleteTable();

        TableCreation tableCreation = new TableCreation();

        tableCreation.printTable();

        tableCreation.deleteTable();
    }
}
