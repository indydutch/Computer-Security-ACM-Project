/*
 * Main.java
 * Matthew Dutchess & Calvin Hariprasad
 * Section 1
 * Date Last Modified: 11/11/2021
 */

/*
 * File Description: contains the user interface. Can perform actions on the ACM
 */

/*
 * Run Instructions:
 * Simply run the code as normal, enter a username, and peruse the options that are provided
 * The ACM table output will be shown before you exit
 */

import java.util.Scanner;

// Class for main functions of the system
public class Main {
    public static void main(String[] args) { // main function for the command detection
        TableCreation testObj = new TableCreation(); // Creates a new TableCreation object

        System.out.println(); // adds a line of spacing

        //boolean to determine whether program should continue running
        boolean cont = true;

        //request username
        System.out.print("Enter your username: ");

        //scanner to read input from user
        Scanner s1 = new Scanner(System.in);

        //String to hold username and remove whitespace if any to protect from SQL injections
        String username = s1.nextLine().replaceAll("\\s+","");

        if (!(testObj.checkUser(username))) { // checks if user is already in ACM, if they aren't, run the following code
            testObj.createSubject(username); // creates a user subject in ACM
        }

        //print welcome line with username
        System.out.println("\nWelcome, " + username);

        //main TUI that allows user to select their role
        System.out.println("--------------------------------------------------------------------\n"
                + "|                        Please select your role:                  |\n"
                + "|                      1. Author                                   |\n"
                + "|                      2. Editor                                   |\n"
                + "|                      3. Associate Editor                         |\n"
                + "|                      4. Reviewer                                 |\n"
                + "|                      5. Administrator                            |\n"
                + "|                      6. Exit                                     |\n"
                + "--------------------------------------------------------------------");
        //request option
        System.out.print("Enter your number of your role (i.e. 1, 2, 3, 4, 5, or 6): ");
        //assign option
        int role = s1.nextInt();

        String[] roleName = {"author", "editor", "assceditor", "reviewer", "admin"};

        if(role == 1 || role == 2 || role == 3 || role == 4 || role == 5) {
            if(!(testObj.checkPermissions(username, username, roleName[role-1]))) {
                testObj.assignRole(username, username, username, roleName[role - 1]);
            }
        }

        //while program bool is true, continue running program
        while(cont) {
            //switch to hold response for each option
            switch (role) {

                //if user is an Author
                case 1:
                    //String to hold the manuscript name, assigned editor name
                    String manuscript, editor;

                    //TUI for Author
                    System.out.println("\n--------------------------------------------------------------------\n"
                            + "|                        Select an option:                         |\n"
                            + "|                      1. Submit manuscript                        |\n"
                            + "|                      2. Remove manuscript                        |\n"
                            + "|                      3. Assign Editor                            |\n"
                            + "|                      4. Exit                                     |\n"
                            + "--------------------------------------------------------------------");
                    //request option
                    System.out.print("Enter your option (i.e. 1, 2, 3, 4): ");

                    //assign option to int
                    int authorOpt = s1.nextInt();

                    //if Author choose to submit a manuscript
                    if(authorOpt == 1) {
                        //request the name of the manuscript
                        System.out.print("\nEnter the name of your manuscript: ");
                        Scanner s2 = new Scanner(System.in);

                        //store the name of the manuscript in a String without spaces
                        manuscript = s2.nextLine().replaceAll("\\s+","");

                        if(!(testObj.checkObject(manuscript))) {
                            testObj.createObject(username, manuscript); // creates an object

                            //notify the Author that manuscript has been submitted
                            System.out.println("Manuscript submitted successfully!");
                        } else {
                            System.out.println("Manuscript already exists in system!");
                        }
                    }

                    //if Author choose to remove manuscript
                    else if(authorOpt == 2) {
                        //set manuscript String to null to remove any pre-existing names
                        System.out.print("\nEnter the name of your manuscript you wish to remove: ");
                        Scanner s2 = new Scanner(System.in);

                        //store the name of the manuscript in a String without spaces
                        manuscript = s2.nextLine().replaceAll("\\s+","");

                        if(testObj.checkObject(manuscript)) {
                            testObj.deleteObject(username, manuscript); // deletes an object

                            //notify the Author that manuscript has been submitted
                            System.out.println("Manuscript removed successfully!");
                        } else {
                            System.out.println("Manuscript doesn't exist in system!");
                        }
                    }

                    //if Author would like to assign an editor
                    else if(authorOpt == 3) {
                        System.out.print("\nEnter the name of your manuscript you wish to assign: ");
                        Scanner s2 = new Scanner(System.in);

                        //store the name of the manuscript in a String without spaces
                        manuscript = s2.nextLine().replaceAll("\\s+","");

                        //request the name of the Editor they would like to assign
                        System.out.print("\nEnter the name of your editor: ");
                        Scanner s3 = new Scanner(System.in);

                        //store the name of the Editor in a String without spaces
                        editor = s3.nextLine().replaceAll("\\s+","");

                        if(testObj.checkUser(editor)) {
                            if (testObj.checkObject(manuscript)) {
                                testObj.assignRole(username, editor, manuscript, "receive");

                                System.out.println("Editor assigned successfully!"); //notify the Author the Editor has been assigned
                            } else {
                                System.out.println("Manuscript does not exist in system!"); //notify the Author the Manuscript could not be found
                            }
                        } else {
                            System.out.println("Editor does not exist in system!"); //notify the Author the Editor has not been assigned
                        }
                    }

                    //if Author would like to exit the program
                    else if(authorOpt == 4) {
                        //set bool to false and end while loop
                        cont = false;
                    }

                    //if Author enters an invalid option
                    else {
                        //request Author to enter a valid option
                        System.out.println("\nPlease enter a valid option.");
                    }
                    break;

                //if user is an Editor
                case 2:
                    //String to hold Author and Associate Editor names
                    String author, associateEditor;

                    //TUI for Associate Editor
                    System.out.println("\n--------------------------------------------------------------------\n"
                            + "|                        Select an option:                         |\n"
                            + "|                      1. Receive manuscript                       |\n"
                            + "|                      2. Assign Associate Editor                  |\n"
                            + "|                      3. Exit                                     |\n"
                            + "--------------------------------------------------------------------\n");

                    //request option
                    System.out.print("Enter your option (i.e. 1, 2, 3): ");

                    //assign option to int
                    int editorOpt = s1.nextInt();

                    //if Editor wants to receive a manuscript
                    if(editorOpt == 1) {
                        //request the name of the manuscript
                        System.out.print("\nEnter the name of the manuscript you wish to receive: ");
                        Scanner s2 = new Scanner(System.in);

                        //store manuscript name in a String without spaces
                        manuscript = s2.nextLine().replaceAll("\\s+","");

                        if (testObj.checkObject(manuscript)) {
                            testObj.assignRole(username, username, manuscript, "receive");

                            System.out.println("Manuscript received successfully!"); //notify the Editor that the manuscript has been received successfully
                        } else {
                            System.out.println("Manuscript does not exist in system!"); //notify the Editor the Manuscript could not be found
                        }
                    }

                    //if the Editor wants to assign an Associate Editor
                    else if(editorOpt == 2) {
                        //request the name of the manuscript
                        System.out.print("\nEnter the name of the manuscript you wish to send: ");
                        Scanner s2 = new Scanner(System.in);

                        //store manuscript name in a String without spaces
                        manuscript = s2.nextLine().replaceAll("\\s+","");

                        //request the name of the Associate Editor they would like to assign the manuscript to
                        System.out.print("\nEnter the name of the Associate Editor you wish to assign this manuscript to: ");
                        Scanner s3 = new Scanner(System.in);

                        //store the name of the Associate Editor in a String with no spaces
                        associateEditor = s3.nextLine().replaceAll("\\s+","");

                        if(testObj.checkUser(associateEditor)) {
                            if (testObj.checkObject(manuscript)) {
                                testObj.assignRole(username, associateEditor, manuscript, "receive");

                                System.out.println("Associate Editor assigned successfully!"); //notify the Editor that the Associate Editor has been assigned
                            } else {
                                System.out.println("Manuscript does not exist in system!"); //notify the Editor the Manuscript could not be found
                            }
                        } else {
                            System.out.println("Associate Editor does not exist in system!"); //notify the Author the Editor has not been assigned
                        }
                    }

                    //if the Editor wants to exit the program
                    else if(editorOpt == 3) {
                        //set bool to false to close program
                        cont = false;
                    }

                    //if Editor enters an invalid option
                    else {
                        //request valid option to be entered
                        System.out.println("\nPlease enter a valid option.");
                    }
                    break;

                //if user is an Associate Editor
                case 3:
                    //String to hold the name of the Reviewer
                    String reviewer;

                    //TUI for Associate Editor
                    System.out.println("\n--------------------------------------------------------------------\n"
                            + "|                        Select an option:                         |\n"
                            + "|                      1. Assign Reviewer                          |\n"
                            + "|                      2. Read Review                              |\n"
                            + "|                      3. Accept manuscript                        |\n"
                            + "|                      4. Reject manuscript                        |\n"
                            + "|                      5. Exit                                     |\n"
                            + "--------------------------------------------------------------------\n");

                    //request option
                    System.out.print("Enter your option (i.e. 1, 2, 3, 4, or 5): ");

                    //assign option to int
                    int assocEditOpt = s1.nextInt();

                    //if Assoc. Editor would like to Assign Reviewer
                    if(assocEditOpt == 1) {
                        System.out.print("\nEnter the name of your manuscript you wish to assign: ");
                        Scanner s2 = new Scanner(System.in);

                        //store the name of the manuscript in a String without spaces
                        manuscript = s2.nextLine().replaceAll("\\s+","");

                        //Request the name of the Reviewer to be assigned
                        System.out.print("\nEnter the name of the reviewer you wish to assign this manuscript to: ");
                        Scanner s3 = new Scanner(System.in);

                        //Store the name of the Reviewer in a String without spaces
                        reviewer = s3.nextLine().replaceAll("\\s+","");

                        //notify the Author the Editor has been assigned

                        if(testObj.checkUser(reviewer)) {
                            if (testObj.checkObject(manuscript)) {
                                testObj.assignRole(username, reviewer, manuscript, "receive");

                                System.out.println("Reviewer assigned successfully!"); //notify the Associate Editor that the Reviewer has been assigned
                            } else {
                                System.out.println("Manuscript does not exist in system!"); //notify the Associate Editor the Manuscript could not be found
                            }
                        } else {
                            System.out.println("Reviewer does not exist in system!"); //notify the Associate Editor that the Reviewer has not been assigned
                        }
                    }

                    //If Assoc. Editor wants to read the review
                    else if(assocEditOpt == 2) {
                        //Display the review scores
                        System.out.println("The review score is: 10/10!");
                    }

                    //If the Assoc. Editor wants to accept the manuscript
                    else if(assocEditOpt == 3) {
                        System.out.print("\nEnter the name of your manuscript you wish to accept: ");
                        Scanner s2 = new Scanner(System.in);

                        //store the name of the manuscript in a String without spaces
                        manuscript = s2.nextLine().replaceAll("\\s+","");

                        if (testObj.checkPermissions(username, manuscript, "receive") && testObj.checkObject(manuscript)) {
                            testObj.assignRole(username, username, manuscript, "accept");

                            testObj.deleteObjectRight(username, username, manuscript, "receive");

                            System.out.println("Manuscript has been accepted!");
                        } else {
                            System.out.println("No manuscript has been assigned to you!");
                        }
                    }

                    //If the Assoc. Editor wants to reject the manuscript
                    else if(assocEditOpt == 4) {
                        System.out.print("\nEnter the name of your manuscript you wish to reject: ");
                        Scanner s2 = new Scanner(System.in);

                        //store the name of the manuscript in a String without spaces
                        manuscript = s2.nextLine().replaceAll("\\s+","");

                        if (testObj.checkPermissions(username, manuscript, "receive") && testObj.checkObject(manuscript)) {
                            testObj.assignRole(username, username, manuscript, "reject");

                            testObj.deleteObjectRight(username, username, manuscript, "receive");

                            System.out.println("Manuscript has been rejected!");
                        } else {
                            System.out.println("No manuscript has been assigned to you!");
                        }
                    }

                    //If the Associate Editor wants to quit the program
                    else if(assocEditOpt == 5) {
                        //set bool to false to exit program
                        cont = false;
                    }

                    //If Assoc. Editor inputs an invalid option
                    else {
                        //request user to enter a valid option
                        System.out.println("\nPlease enter a valid option.");
                    }
                    break;

                //If user is a Reviewer
                case 4:
                    //TUI for Reviewer
                    System.out.println("\n--------------------------------------------------------------------\n"
                            + "|                        Select an option:                         |\n"
                            + "|                      1. Submit Review                            |\n"
                            + "|                      2. Exit                                     |\n"
                            + "--------------------------------------------------------------------\n");

                    //request option
                    System.out.print("Enter your option (i.e. 1 or 2): ");

                    //assign option to int
                    int reviewerOpt = s1.nextInt();

                    //if Reviewer wants to submit review
                    if(reviewerOpt == 1) {
                        System.out.print("\nEnter the name of your manuscript you wish to submit the review for: ");
                        Scanner s2 = new Scanner(System.in);

                        //store the name of the manuscript in a String without spaces
                        manuscript = s2.nextLine().replaceAll("\\s+","");

                        if (testObj.checkPermissions(username, manuscript, "receive") && testObj.checkObject(manuscript)) {
                            testObj.assignRole(username, username, manuscript, "reviewed");

                            testObj.deleteObjectRight(username, username, manuscript, "receive");

                            System.out.println("Manuscript review submitted successfully!"); //Notify Reviewer review has been submitted
                        } else {
                            System.out.println("No manuscript has been assigned to you!"); //Notify Reviewer that no manuscript has been assigned to them
                        }
                    }

                    //if Reviewer wants to exit the program
                    else if(reviewerOpt == 2) {
                        //set bool to false to exit program
                        cont = false;
                    }

                    //if Reviewer enter an invalid option
                    else {
                        //Request valid option to be entered
                        System.out.println("\nPlease enter a valid option.");
                    }
                    break;

                //if user is an Administrator
                case 5:
                    //TUI for Administrator
                    System.out.println("\n--------------------------------------------------------------------\n"
                            + "|                        Select an option:                         |\n"
                            + "|                      1. Submit manuscript                        |\n"
                            + "|                      2. Remove manuscript                        |\n"
                            + "|                      3. Assign Editor                            |\n"
                            + "|                      4. Receive Manuscript                       |\n"
                            + "|                      5. Assign Associate Editor                  |\n"
                            + "|                      6. Assign Reviewer                          |\n"
                            + "|                      7. Read Review                              |\n"
                            + "|                      8. Accept manuscript                        |\n"
                            + "|                      9. Reject manuscript                        |\n"
                            + "|                      10. Submit Review                           |\n"
                            + "|                      11. Exit                                    |\n"
                            + "--------------------------------------------------------------------\n");

                    //request option
                    System.out.print("Enter your option (i.e. 1-11): ");

                    //assign option to int
                    int adminOpt = s1.nextInt();

                    //if Admin wants to submit manuscript
                    if(adminOpt == 1) {
                        //request the name of the manuscript
                        System.out.print("\nEnter the name of your manuscript: ");
                        Scanner s2 = new Scanner(System.in);

                        manuscript = null;

                        //store the name of the manuscript in a String without spaces
                        manuscript = s2.nextLine().replaceAll("\\s+","");

                        if(!(testObj.checkObject(manuscript))) {
                            testObj.createObject(username, manuscript); // creates an object

                            //notify the Admin that manuscript has been submitted
                            System.out.println("Manuscript submitted successfully!");
                        } else {
                            System.out.println("Manuscript already exists in system!");
                        }
                    }

                    //if Admin wants to remove manuscript
                    else if(adminOpt == 2) {
                        //set manuscript String to null to remove any pre-existing names
                        System.out.print("\nEnter the name of your manuscript you wish to remove: ");
                        Scanner s2 = new Scanner(System.in);

                        //store the name of the manuscript in a String without spaces
                        manuscript = s2.nextLine().replaceAll("\\s+","");

                        if(testObj.checkObject(manuscript)) {
                            testObj.deleteObject(username, manuscript); // deletes an object

                            //notify the Author that manuscript has been submitted
                            System.out.println("Manuscript removed successfully!");
                        } else {
                            System.out.println("Manuscript doesn't exist in system!");
                        }
                    }

                    //if Admin wants to Assign Editor
                    else if(adminOpt == 3) {
                        System.out.print("\nEnter the name of your manuscript you wish to assign: ");
                        Scanner s2 = new Scanner(System.in);

                        //store the name of the manuscript in a String without spaces
                        manuscript = s2.nextLine().replaceAll("\\s+","");

                        //request the name of the Editor they would like to assign
                        System.out.print("\nEnter the name of your editor: ");
                        Scanner s3 = new Scanner(System.in);

                        //store the name of the Editor in a String without spaces
                        editor = s3.nextLine().replaceAll("\\s+","");

                        if(testObj.checkUser(editor)) {
                            if (testObj.checkObject(manuscript)) {
                                testObj.assignRole(username, editor, manuscript, "receive");

                                System.out.println("Editor assigned successfully!"); //notify the Admin the Editor has been assigned
                            } else {
                                System.out.println("Manuscript does not exist in system!"); //notify the Admin the Manuscript could not be found
                            }
                        } else {
                            System.out.println("Editor does not exist in system!"); //notify the Admin the Editor has not been assigned
                        }

                    }

                    //if Admin wants to receive manuscript
                    else if(adminOpt == 4) {
                        //request the name of the manuscript
                        System.out.print("\nEnter the name of the manuscript you wish to receive: ");
                        Scanner s2 = new Scanner(System.in);

                        //store manuscript name in a String without spaces
                        manuscript = s2.nextLine().replaceAll("\\s+","");

                        if (testObj.checkObject(manuscript)) {
                            testObj.assignRole(username, username, manuscript, "receive");

                            System.out.println("Manuscript received successfully!"); //notify the Admin that the manuscript has been received successfully
                        } else {
                            System.out.println("Manuscript does not exist in system!"); //notify the Admin the Manuscript could not be found
                        }
                    }

                    //if Admin wants to assign an Associate Editor
                    else if(adminOpt == 5) {
                        //request the name of the manuscript
                        System.out.print("\nEnter the name of the manuscript you wish to send: ");
                        Scanner s2 = new Scanner(System.in);

                        //store manuscript name in a String without spaces
                        manuscript = s2.nextLine().replaceAll("\\s+","");

                        //request the name of the Associate Editor they would like to assign the manuscript to
                        System.out.print("\nEnter the name of the Associate Editor you wish to assign this manuscript to: ");
                        Scanner s3 = new Scanner(System.in);

                        //store the name of the Associate Editor in a String with no spaces
                        associateEditor = s3.nextLine().replaceAll("\\s+","");

                        if(testObj.checkUser(associateEditor)) {
                            if (testObj.checkObject(manuscript)) {
                                testObj.assignRole(username, associateEditor, manuscript, "receive");

                                System.out.println("Associate Editor assigned successfully!"); //notify the Admin that the Associate Editor has been assigned
                            } else {
                                System.out.println("Manuscript does not exist in system!"); //notify the Admin the Manuscript could not be found
                            }
                        } else {
                            System.out.println("Associate Editor does not exist in system!"); //notify the Admin the Editor has not been assigned
                        }
                    }

                    //if Admin wants to assign a Reviewer
                    else if(adminOpt == 6) {
                        System.out.print("\nEnter the name of your manuscript you wish to assign: ");
                        Scanner s2 = new Scanner(System.in);

                        //store the name of the manuscript in a String without spaces
                        manuscript = s2.nextLine().replaceAll("\\s+","");

                        //Request the name of the Reviewer to be assigned
                        System.out.print("\nEnter the name of the reviewer you wish to assign this manuscript to: ");
                        Scanner s3 = new Scanner(System.in);

                        //Store the name of the Reviewer in a String without spaces
                        reviewer = s3.nextLine().replaceAll("\\s+","");

                        //notify the Author the Editor has been assigned

                        if(testObj.checkUser(reviewer)) {
                            if (testObj.checkObject(manuscript)) {
                                testObj.assignRole(username, reviewer, manuscript, "receive");

                                System.out.println("Reviewer assigned successfully!"); //notify the Admin that the Reviewer has been assigned
                            } else {
                                System.out.println("Manuscript does not exist in system!"); //notify the Admin the Manuscript could not be found
                            }
                        } else {
                            System.out.println("Reviewer does not exist in system!"); //notify the Admin that the Reviewer has not been assigned
                        }
                    }

                    //if Admin wants to read the review
                    else if(adminOpt == 7) {
                        //Display the review scores
                        System.out.println("The review score is: 10/10!");
                    }

                    //if Admin wants to accept the manuscript
                    else if(adminOpt == 8) {
                        System.out.print("\nEnter the name of your manuscript you wish to accept: ");
                        Scanner s2 = new Scanner(System.in);

                        //store the name of the manuscript in a String without spaces
                        manuscript = s2.nextLine().replaceAll("\\s+","");

                        if (testObj.checkPermissions(username, manuscript, "receive") && testObj.checkObject(manuscript)) {
                            testObj.assignRole(username, username, manuscript, "accept");

                            testObj.deleteObjectRight(username, username, manuscript, "receive");

                            System.out.println("Manuscript has been accepted!");
                        } else {
                            System.out.println("No manuscript has been assigned to you!");
                        }
                    }

                    //if Admin wants to reject the manuscript
                    else if(adminOpt == 9) {
                        System.out.print("\nEnter the name of your manuscript you wish to reject: ");
                        Scanner s2 = new Scanner(System.in);

                        //store the name of the manuscript in a String without spaces
                        manuscript = s2.nextLine().replaceAll("\\s+","");

                        if (testObj.checkPermissions(username, manuscript, "receive") && testObj.checkObject(manuscript)) {
                            testObj.assignRole(username, username, manuscript, "reject");

                            testObj.deleteObjectRight(username, username, manuscript, "receive");

                            System.out.println("Manuscript has been rejected!");
                        } else {
                            System.out.println("No manuscript has been assigned to you!");
                        }
                    }

                    //if Admin wants to submit a review
                    else if(adminOpt == 10) {
                        System.out.print("\nEnter the name of your manuscript you wish to submit the review for: ");
                        Scanner s2 = new Scanner(System.in);

                        //store the name of the manuscript in a String without spaces
                        manuscript = s2.nextLine().replaceAll("\\s+","");

                        if (testObj.checkPermissions(username, manuscript, "receive") && testObj.checkObject(manuscript)) {
                            testObj.assignRole(username, username, manuscript, "reviewed");

                            testObj.deleteObjectRight(username, username, manuscript, "receive");

                            System.out.println("Manuscript review submitted successfully!"); //Notify Reviewer review has been submitted
                        } else {
                            System.out.println("No manuscript has been assigned to you!"); //Notify Reviewer that no manuscript has been assigned to them
                        }
                    }

                    //if Admin wants to exit the program
                    else if(adminOpt == 11) {
                        cont = false;
                    }

                    //if Admin selects an invalid option
                    else {
                        System.out.println("\nPlease enter a valid option.");
                    }
                    break;

                //if user enters 6 to exit
                case 6:
                    System.exit(0);
                    break;

                //if user enters an invalid option
                default:
                    System.out.println("\nPlease enter a valid option.");
                    System.out.print("Enter your number of your role (i.e. 1, 2, 3, 4, 5, or 6): ");
                    role = s1.nextInt();
            }
        }
    }
}