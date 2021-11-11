import java.sql.*;

public class TableCreation {
    public TableCreation() {
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS ACM (\n"
                + "name text PRIMARY KEY);";

        tryConnect(sql);
    }

    public void createSubject(String subject) {
        // SQL statement for inserting a subject into the table
        String sql = "INSERT INTO ACM(name) VALUES(?)";

        tryConnect1Val(sql, subject);

        createObject(subject, subject);
    }

    public void grant(String curUser, String fromUser, String toUser, String perm) {
        // SQL statement for getting the value where name = toUser
        String sql = "SELECT * FROM ACM WHERE name = '" + toUser.toLowerCase() + "';";

        String fromDb = null;
        fromDb = tryConnectGetVal1(sql, fromUser);

        if (fromDb != null) {
            // SQL statement for updating the value fromUser with the pre-existing value in the table and the perm where name = toUser
            sql = "UPDATE ACM SET '" + fromUser.toLowerCase() + "' = '" + perm.toLowerCase() + "' ||  ', ' || '" + fromDb.toLowerCase() + "' WHERE name = '" + toUser.toLowerCase() + "';";
        } else {
            // SQL statement for updating the value fromUser to perm where name = toUser
            sql = "UPDATE ACM SET '" + fromUser.toLowerCase() + "' = '" + perm.toLowerCase() + "' WHERE name = '" + toUser.toLowerCase() + "';";
        }

        tryConnect(sql);
    }

    public void createObject(String creator, String object) {
        // SQL statement for adding a object to the table with type 'text'
        String sql = "ALTER TABLE ACM ADD " + object.toLowerCase() + " text";

        tryConnect(sql);

        if (creator.equals(object)) {
            // SQL statement for updating the object value to 'control' where name = creator
            sql = "UPDATE ACM SET " + object.toLowerCase() + " = 'control' WHERE name = ?;";
        } else {
            // SQL statement for updating the object value to 'owner' where name = creator
            sql = "UPDATE ACM SET " + object.toLowerCase() + " = 'owner' WHERE name = ?;";
        }
        tryConnect1Val(sql, creator);
    }

    public void deleteRight(String creator, String fromUser, String toUser, String perm) {
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
            tryConnect(sql);
        } else {
            System.out.println("This right has not been assigned to this subject/object");
        }
    }

    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:../ACM-Project/src/main/db/acm.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void tryConnect(String sql) {
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void tryConnect1Val(String sql, String entry) {
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entry.toLowerCase());
            pstmt.executeUpdate();
            this.connect().close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void tryConnect2Val(String sql, String entry1, String entry2) {
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entry1.toLowerCase());
            pstmt.setString(2, entry2.toLowerCase());
            pstmt.executeUpdate();
            this.connect().close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String tryConnectGetVal1(String sql, String entry) {
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            rs.next();
            return rs.getString(entry.toLowerCase());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void tryConnectGet(String sql) {

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
            System.out.println(e.getMessage());
        }
    }

    public void printTable() {
        String sql = "SELECT * FROM ACM;";

        tryConnectGet(sql);
    }

    public void deleteTable() {
        // SQL statement for deleting the table
        String sql = "DROP TABLE ACM;";

        tryConnect(sql);
    }
}
