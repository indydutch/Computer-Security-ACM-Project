/*
 * Main.java
 * Matthew Dutchess & Calvin Hariprasad
 * Section 1
 * Date Last Modified: 11/11/2021
 */

/*
 * File Description: used to perform all ACM functions
 */

import java.sql.*;
import java.util.Objects;

// Class for table creation and query handling
public class TableCreation {
    // Function for default object initialization, creates table if a table doesn't already exist
    public TableCreation() {
        boolean exists = this.checkTable();

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS ACM (\n"
                + "name text PRIMARY KEY);";

        tryConnect(sql); // queries the statement

        if (!(exists)) { // runs if the table doesn't exist
            generateACM(); // generates the basic ACM
        }
    }

    // Function for creating subjects in the table
    public void createSubject(String subject) {
        // SQL statement for inserting a subject into the table
        String sql = "INSERT INTO ACM(name) VALUES(?)";

        tryConnect1Val(sql, subject); // queries the statement

        createObject(subject, subject); // queries the statement
    }

    // Function for granting rights in the table
    public void grant(String curUser, String fromUser, String toUser, String perm) {
        // SQL statement for getting the value where name = toUser
        String sql = "SELECT * FROM ACM WHERE name = '" + toUser.toLowerCase() + "';";

        String fromDb = null;
        fromDb = tryConnectGetVal1(sql, fromUser); // Gets value from table

        if (fromDb != null) { // runs if the resulting data from the previous query is not empty
            // SQL statement for updating the value fromUser with the pre-existing value in the table and the perm where name = toUser
            sql = "UPDATE ACM SET '" + fromUser.toLowerCase() + "' = '" + perm.toLowerCase() + "' ||  ', ' || '" + fromDb.toLowerCase() + "' WHERE name = '" + toUser.toLowerCase() + "';";
        } else {
            // SQL statement for updating the value fromUser to perm where name = toUser
            sql = "UPDATE ACM SET '" + fromUser.toLowerCase() + "' = '" + perm.toLowerCase() + "' WHERE name = '" + toUser.toLowerCase() + "';";
        }

        tryConnect(sql); // queries the statement
    }

    // Function for creating objects in the table
    public void createObject(String creator, String object) {
        // SQL statement for adding a object to the table with type 'text'
        String sql = "ALTER TABLE ACM ADD " + object.toLowerCase() + " text";

        tryConnect(sql); // queries the statement

        if (creator.equals(object)) { // runs if the creator is the same as the object
            // SQL statement for updating the object value to 'control' where name = creator
            sql = "UPDATE ACM SET " + object.toLowerCase() + " = 'control' WHERE name = ?;";
        } else {
            // SQL statement for updating the object value to 'owner' where name = creator
            sql = "UPDATE ACM SET " + object.toLowerCase() + " = 'owner' WHERE name = ?;";
        }
        tryConnect1Val(sql, creator); // queries the statement
    }

    // Function for deleting rights in the table
    public void deleteRight(String curUser, String fromUser, String toUser, String perm) {
        if(checkPermissions(curUser, toUser, "control") || checkPermissions(curUser, toUser, "owner")) {
            // SQL statement for getting the value where name = toUser
            String sql = "SELECT * FROM ACM WHERE name = '" + toUser.toLowerCase() + "';";

            String fromDb = null;
            fromDb = tryConnectGetVal1(sql, fromUser);

            if (fromDb != null) {
                String delete1 = perm.toLowerCase() + ", ";
                String delete2 = ", " + perm.toLowerCase();
                fromDb = fromDb.replace(delete1, "");
                fromDb = fromDb.replace(delete2, "");
                // SQL statement for updating the fromUser to fromDb where name = toUser
                sql = "UPDATE ACM SET '" + fromUser.toLowerCase() + "' = '" + fromDb.toLowerCase() + "' WHERE name = '" + toUser.toLowerCase() + "';";
                tryConnect(sql); // queries the statement
            }
        }
    }

    // Function for deleting object rights in the table
    public void deleteObjectRight(String curUser, String toUser, String object, String perm) {
        // SQL statement for getting the value where name = toUser
        String sql = "SELECT * FROM ACM WHERE name = '" + toUser.toLowerCase() + "';";

        String fromDb = null;
        fromDb = tryConnectGetVal1(sql, object);

        if (fromDb != null) {
            String delete1 = perm.toLowerCase() + ", ";
            String delete2 = ", " + perm.toLowerCase();
            fromDb = fromDb.replace(delete1, "");
            fromDb = fromDb.replace(delete2, "");
            // SQL statement for updating the fromUser to fromDb where name = toUser
            sql = "UPDATE ACM SET '" + object.toLowerCase() + "' = '" + fromDb.toLowerCase() + "' WHERE name = '" + toUser.toLowerCase() + "';";
            tryConnect(sql); // queries the statement
        }
    }

    // function for setting up a connection to the database
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:../ACM-Project/src/main/db/acm.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    // Function for trying to query a passed SQL statement
    public void tryConnect(String sql) {
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // function for trying to query a passed SQL statement and entry value
    public void tryConnect1Val(String sql, String entry) {
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entry.toLowerCase());
            pstmt.executeUpdate();
            this.connect().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Function for trying to query a passed SQL statement and entry values
    public void tryConnect2Val(String sql, String entry1, String entry2) {
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entry1.toLowerCase());
            pstmt.setString(2, entry2.toLowerCase());
            pstmt.executeUpdate();
            this.connect().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Function for trying to query a passed SQL statement and entry value, returns the entry value located in table
    public String tryConnectGetVal1(String sql, String entry) {
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            rs.next();
            return rs.getString(entry.toLowerCase());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Function for trying to query a passed SQL statement (for table printing purposes)
    public void tryPrint(String sql) {
        try {
            Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();

            int columnsNumber = rsmd.getColumnCount();

            while (rs.next()) { // Print one row
                for(int i = 1; i <= columnsNumber; i++) {
                    System.out.format("%-16s", rs.getString(i));
                }
                System.out.println();//Move to the next line to print the next row.
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Function for trying to query a passed SQL statement using an object and perm. Returns a boolean value based on the result
    public boolean checkPermissions(String user, String object, String perm) {
        // SQL statement for getting the perm values at the specified user and object
        String sql = "SELECT * FROM ACM WHERE name = '" + user.toLowerCase() + "';";

        String[] strarray = null;
        String fromDb = tryConnectGetVal1(sql, object);

        if (fromDb != null) {
            strarray = fromDb.split(", ");

            for (String s : strarray) {
                if (s.equalsIgnoreCase(perm)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Function for trying to query whether a user exists in the system. Returns a boolean value based on the result
    public boolean checkUser(String user) {
        // SQL statement for checking if a user exists
        String sql = "SELECT (count(*) > 0) as found FROM ACM WHERE name = ?;";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user);

            try (ResultSet rs = pstmt.executeQuery()) {
                // Only expecting a single result
                if (rs.next()) {
                    return rs.getBoolean(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Function for trying to query whether an object exists in the system. Returns a boolean value based on the result
    public boolean checkObject(String object) {
        String sql = "SELECT COUNT(*) AS CNTREC FROM pragma_table_info('ACM') WHERE name=?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, object);

            try (ResultSet rs = pstmt.executeQuery()) {
                // Only expecting a single result
                if (rs.next()) {
                    return rs.getBoolean(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Function for printing the table
    public void printTable() {
        String sql = "SELECT * FROM ACM;";

        tryPrint(sql); // queries the statement for printing purposes
    }

    // Function for trying to query a statement for deleting a passed object, only runs if the calling user has owner or control permissions over the object
    public void deleteObject(String user, String object) {
        if (checkPermissions(user, object, "owner") || checkPermissions(user, object, "control")) {
            String sql = "ALTER TABLE ACM DROP COLUMN '" + object.toLowerCase() + "';";

            tryConnect(sql); // queries the statement
        } else {
            System.out.println("\nYou do not have permissions to delete this object.");
        }
    }

    // Function for trying to delete the table
    public void deleteTable() {
        // SQL statement for deleting the table
        String sql = "DROP TABLE ACM;";

        tryConnect(sql); // queries the statement
    }

    // Function for trying to query a statement for checking if the table exists. Returns a boolean based on the results.
    public boolean checkTable() {
        // SQL statement for finding the name of the table
        String sql = "SELECT name as found FROM sqlite_master WHERE type='table' AND name='ACM';";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                // Only expecting a single result
                if (rs.next()) {
                    return Objects.equals(rs.getString(1), "ACM");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Function for trying to query a statement for assigning roles to a specific user
    public void assignRole(String curUser, String toUser, String object, String perm) {
        // SQL statement for finding the role of toUser
        String sql = "SELECT * FROM ACM WHERE name = '" + toUser.toLowerCase() + "';";

        String fromDb = null;
        fromDb = tryConnectGetVal1(sql, object); // queries the statement

        if (fromDb != null) { // runs if the resulting data from the previous query is not empty
            // SQL statement for updating the value fromUser with the pre-existing value in the table and the perm where name = toUser
            sql = "UPDATE ACM SET '" + object.toLowerCase() + "' = '" + perm.toLowerCase() + "' ||  ', ' || '" + fromDb.toLowerCase() + "' WHERE name = '" + toUser.toLowerCase() + "';";
        } else {
            // SQL statement for updating the value fromUser to perm where name = toUser
            sql = "UPDATE ACM SET '" + object.toLowerCase() + "' = '" + perm.toLowerCase() + "' WHERE name = '" + toUser.toLowerCase() + "';";
        }

        tryConnect(sql); // queries the statement
    }

    // Function for trying to query a statement for getting the role of a specific user on an object
    public String getRole(String user, String object) {
        // SQL statement for finding the role of toUser
        String sql = "SELECT * FROM ACM WHERE name = '" + user.toLowerCase() + "';";

        String[] strarray = null;
        String fromDb = tryConnectGetVal1(sql, object); // queries the statement

        if (fromDb != null) { // runs if the resulting data from the previous query is not empty
            strarray = fromDb.split(", "); // splits the string after every comma and space

            for (String s : strarray) { // loops through the array
                if (s.equals("author") || s.equals("editor") || s.equals("assceditor") || s.equals("reviewer") || s.equals("admin")) { // checks to see if the user already has a role
                    return s.toLowerCase(); // returns the role name
                }
            }
        }
        return null;
    }

    // Function for generating the default ACM
    public void generateACM() {
        this.createSubject("s1"); // creates a subject and adds it to the ACM
        this.createSubject("s2");
        this.createSubject("s3");
        this.grant("s2", "s2", "s1", "owner"); // grants a right (if the user has the correct permissions) and adds it to the ACM
        this.grant("s3", "s3", "s1", "owner");
        this.grant("s3", "s3", "s1", "control");
        this.createObject("s1", "f1"); // creates an object and adds it to the ACM
        this.grant("s1", "f1", "s1", "read*");
        this.grant("s1", "f1", "s2", "write*");
        this.createObject("s1", "f2");
        this.grant("s1", "f2", "s1", "read");
        this.grant("s1", "f2", "s2", "execute");
        this.grant("s1", "f2", "s3", "write");
        this.createObject("s1", "p1");
        this.grant("s1", "p1", "s1", "wakeup");
        this.grant("s1", "p1", "s3", "stop");
        this.createObject("s1", "p2");
        this.grant("s1", "p2", "s1", "wakeup");
        this.createObject("s2", "d1");
        this.grant("s2", "d1", "s1", "seek");
        this.createObject("s1", "d2");
        this.grant("s1", "d2", "s2", "seek*");
        this.deleteRight("s1", "f1", "s1", "owner"); // removes a right (if the user has the correct permissions) and updates it in the ACM
        this.deleteRight("s1", "p1", "s1", "owner");
        this.deleteRight("s1", "p2", "s1", "owner");
    }
}