import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ListFunctions {
    public ListFunctions() {
        System.out.println("Object Created");
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

    public void delete(String input) {
        // SQL statement for creating a new table
        String sql = "DROP TABLE " + input + ";";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void remove(String input) {
        String sql = "DELETE FROM " + input + " WHERE id = ?";

        //sql = "DELETE FROM " + placeholder + " WHERE item = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param

            pstmt.setInt(1, Integer.parseInt("10"));

            pstmt.setString(1, "PH");
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void view(String input) {
        String sql = "SELECT * FROM " + input;

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {

                System.out.println(rs.getInt("id") + "\t" +
                        rs.getString("item"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void add(String input) {
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS " + input + " (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	item text NOT NULL\n"
                + ");";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            //MessageChannel channel = event.getChannel(); // gets channel that the message was sent from
            //channel.sendMessage("List was successfully created.").queue(); // sends a query message to the channel that the original command was run from
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        sql = "INSERT INTO " + input + "(item) VALUES(?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "PH");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
