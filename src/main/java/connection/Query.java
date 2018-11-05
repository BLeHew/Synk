package connection;

import tableobjects.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Query {
    public static void setAndRunStatement(Project p,Connection conn) throws SQLException{
        PreparedStatement stmt;
        stmt = conn.prepareStatement("INSERT INTO project VALUES(null,?,?)");
        stmt.setString(1, p.getName());
        stmt.setString(2, p.getDesc());
        stmt.executeUpdate();
    }

}
