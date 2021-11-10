public class IsInteger extends Main {
    public boolean isInteger(String s) { // function for checking if a passed string is all integers
        boolean isValidInteger = false; // creates a boolean variable and initializes it to false
        try // attempts the following code if there are no Number Format exceptions
        {
            Integer.parseInt(s); // parses the integers in the passed string

            // s is a valid integer

            isValidInteger = true; // sets the value of isValidInteger to true if the passed string is all integers
        }
        catch (NumberFormatException ex) // catches any Number Format exceptions
        {
            // s is not an integer
        }

        return isValidInteger; // returns ture or false depending on if the passed string is all integers
    }
}
