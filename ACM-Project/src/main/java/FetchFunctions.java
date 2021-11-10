public class FetchFunctions {
    public void fetchList() {
        try {
            if () { // delete
                ListFunctions listFunctions = new ListFunctions();
                listFunctions.delete();
            } else if () { // remove
                ListFunctions listFunctions = new ListFunctions();
                listFunctions.remove();
            } else if () { // view
                ListFunctions listFunctions = new ListFunctions();
                listFunctions.view();
            } else { // add
                ListFunctions listFunctions = new ListFunctions();
                listFunctions.add();
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Array Index Out of Bounds Exception");
        }
    }
}
