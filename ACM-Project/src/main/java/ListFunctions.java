import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ListFunctions {
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

    public void delete(MessageReceivedEvent event, User author) {
        // SQL statement for creating a new table
        String sql = "DROP TABLE " + author.getName() + ";";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);

            MessageChannel channel = event.getChannel(); // gets channel that the message was sent from
            channel.sendMessage("List deleted successfully!").queue(); // sends a query message to the channel that the original command was run from
        } catch (SQLException e) {
            MessageChannel channel = event.getChannel(); // gets channel that the message was sent from
            channel.sendMessage("List could not be deleted.").queue(); // sends a query message to the channel that the original command was run from
            System.out.println(e.getMessage());
        }
    }

    public void remove(MessageReceivedEvent event, String[] msgStr, User author) {
        if (msgStr[2] != null) {
            String sql;
            IsInteger isInt = new IsInteger();
            boolean boolInt = isInt.isInteger(msgStr[2]); // creates a boolean integer to test if the second passed parameter are all integers

            if (boolInt)
                sql = "DELETE FROM " + author.getName() + " WHERE id = ?";
            else                        sql = "DELETE FROM " + author.getName() + " WHERE item = ?";

            try (Connection conn = this.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                // set the corresponding param
                if(boolInt)
                    pstmt.setInt(1, Integer.parseInt(msgStr[2]));
                else
                    pstmt.setString(1, msgStr[2]);
                // execute the delete statement
                pstmt.executeUpdate();

                MessageChannel channel = event.getChannel(); // gets channel that the message was sent from
                channel.sendMessage("Item " + msgStr[2] + " removed successfully!").queue(); // sends a query message to the channel that the original command was run from
            } catch (SQLException e) {
                MessageChannel channel = event.getChannel(); // gets channel that the message was sent from
                channel.sendMessage("Error: Item " + msgStr[2] + " could not be removed.").queue(); // sends a query message to the channel that the original command was run from
                System.out.println(e.getMessage());
            }
        } else {
            MessageChannel channel = event.getChannel(); // gets channel that the message was sent from
            channel.sendMessage("Error: Please specify an item to delete in the third parameter.").queue(); // sends a query message to the channel that the original command was run from
        }
    }

    public void view(MessageReceivedEvent event, String[] msgStr, User author) {
        String sql = "SELECT id, item FROM " + author.getName();

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            MessageChannel channel = event.getChannel(); // gets channel that the message was sent from
            channel.sendMessage("**" + author.getName() + "'s List:**").queue(); // sends a query message to the channel that the original command was run from
            // loop through the result set
            while (rs.next()) {

                System.out.println(rs.getInt("id") + "\t" +
                        rs.getString("item"));

                channel.sendMessage(rs.getInt("id") + ": " + rs.getString("item")).queue(); // sends a query message to the channel that the original command was run from
            }
        } catch (SQLException e) {
            MessageChannel channel = event.getChannel(); // gets channel that the message was sent from
            channel.sendMessage("Error: List could not be viewed.").queue(); // sends a query message to the channel that the original command was run from
            System.out.println(e.getMessage());
        }
    }

    public void add(MessageReceivedEvent event, String[] msgStr, User author) {
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS " + author.getName() + " (\n"
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
            MessageChannel channel = event.getChannel(); // gets channel that the message was sent from
            channel.sendMessage("Error: List could not be created.").queue(); // sends a query message to the channel that the original command was run from
            System.out.println(e.getMessage());
        }

        sql = "INSERT INTO " + author.getName() + "(item) VALUES(?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, msgStr[1]);
            pstmt.executeUpdate();
            MessageChannel channel = event.getChannel(); // gets channel that the message was sent from
            channel.sendMessage("Item was added to list successfully.").queue(); // sends a query message to the channel that the original command was run from
        } catch (SQLException e) {
            MessageChannel channel = event.getChannel(); // gets channel that the message was sent from
            channel.sendMessage("Error: Item could not be added to the list.").queue(); // sends a query message to the channel that the original command was run from
            System.out.println(e.getMessage());
        }
    }
}
