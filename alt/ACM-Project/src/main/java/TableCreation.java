import java.sql.*;
import java.util.Locale;

public class TableCreation {
    public TableCreation() {
        String url = "jdbc:sqlite:../ACM-Project/src/main/db/acm.db";
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();

            // SQL statement for creating a new table
            String sql = "CREATE TABLE IF NOT EXISTS ACM (\n"
                    + " top text PRIMARY KEY \n" // needs a comma if below is commented out
                /*
                + "	s1 text NULL, \n"
                + " s2 text NULL, \n"
                + " s3 text NULL, \n"
                + " f1 text NULL, \n"
                + " f2 text NULL, \n"
                + " p1 text NULL, \n"
                + " p2 text NULL, \n"
                + " d1 text NULL, \n"
                + " d2 text NULL, \n"
                 */
                    + ");";

            stmt.executeUpdate(sql);

            sql = "INSERT INTO ACM (top) VALUES ('test')";

            stmt.executeUpdate(sql);

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createSubject(String subject) {
        String sql = "INSERT INTO ACM (top) VALUES (?)";

        tryConnect1Val(sql, subject);

        //createObject(subject, subject);
    }

    public void grant(String creator, String fromUser, String toUser, String perm) {

    }

    public void createObject(String creator, String object) {
        String sql = "ALTER TABLE ACM ADD ? text";

        tryConnect1Val(sql, object);

        if (creator.equals(object)) {
            sql = "UPDATE ACM SET " + object.toLowerCase() + " = 'control' WHERE rowname = ?;";

            tryConnect1Val(sql, creator);
        }
    }

    public void deleteRight(String creator, String fromUser, String toUser, String perm) {

    }

    public Connection connect() {
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
            System.out.println("tryConnect: pass");
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

    public void deleteTable() {
        String sql = "DROP TABLE ACM;";

        tryConnect(sql);
    }
}
