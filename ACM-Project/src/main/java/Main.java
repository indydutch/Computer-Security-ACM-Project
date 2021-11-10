public class Main {
    public static void main(String[] args) // main function for the command detection
    {
        ListFunctions testObj = new ListFunctions();
        
        testObj.add("TestTable");

        testObj.view("TestTable");

        testObj.delete("TestTable");
    }
}
