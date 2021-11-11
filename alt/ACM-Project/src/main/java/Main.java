public class Main {
    public static void main(String[] args) // main function for the command detection
    {
        TableCreation testObj = new TableCreation();


        testObj.createSubject("s1");
        testObj.createSubject("s2");
        testObj.createSubject("s3");
        testObj.grant("s2", "s2", "s1", "owner");
        testObj.grant("s3", "s3", "s1", "owner");
        testObj.grant("s3", "s3", "s1", "control");
        testObj.createObject("s1", "f1");
        testObj.grant("s1", "f1", "s1", "read*");
        testObj.grant("s1", "f1", "s2", "write*");
        testObj.createObject("s1", "f2");
        testObj.grant("s1", "f2", "s1", "read");
        testObj.grant("s1", "f2", "s2", "execute");
        testObj.grant("s1", "f2", "s3", "write");
        testObj.createObject("s1", "p1");
        testObj.grant("s1", "p1", "s1", "wakeup");
        testObj.grant("s1", "p1", "s3", "stop");
        testObj.createObject("s1", "p2");
        testObj.grant("s1", "p2", "s1", "wakeup");
        testObj.createObject("s2", "d1");
        testObj.grant("s2", "d1", "s1", "seek");
        testObj.createObject("s1", "d2");
        testObj.grant("s1", "d2", "s2", "seek*");
        testObj.deleteRight("s1", "f1", "s1", "owner");
        testObj.deleteRight("s1", "p1", "s1", "owner");
        testObj.deleteRight("s1", "p2", "s1", "owner");

        testObj.printTable();

        //testObj.deleteTable();
    }
}
