package Testing;

import connection.SynkConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class DBTesting {
    public static final int NUMPROJECTS = 20;
    public static final int NUMTASKSPERPROJ = 3;
    public static final int NUMUSERS = 10;
    public static final int NUMTASKS = 60;

    public static void fillDBwithTestData() {
        //assuming connection with db is already established
        //createRandomUserProjAssign();
        //createRandomUserTaskAssign();

    }

    public static void addTestProjs(int numProjs) {
        for (int i = 1; i < numProjs + 1; i++) {
            try {
                PreparedStatement stmt = SynkConnection.con.prepareStatement("INSERT INTO projects VALUES(null,?,?)");
                stmt.setString(1, "Project" + String.valueOf(i));
                stmt.setString(2, ("This is project" + i));
                stmt.executeUpdate();
            } catch (SQLException s) {
                System.out.println(s);
            }
        }
    }

    public static void addTestTasks(int numTasksPerProj) {
        PreparedStatement stmt = null;
        try {
            stmt = SynkConnection.con.prepareStatement("INSERT INTO ? VALUES(?,?,?,?)");
        } catch (SQLException s) {
            System.out.println(s);
        }
        Random r = new Random();
        int rUser;
        for (int i = 1; i <= NUMPROJECTS; i++) {
            for (int j = 1; j < numTasksPerProj + 1; j++) {
                rUser = r.nextInt(NUMUSERS + 1);
                try {
                    stmt.setString(1, "tasks");
                    stmt.setString(2, "null");
                    stmt.setString(3, String.valueOf(i));
                    stmt.setString(4, "Proj_" + String.valueOf(i) + "_task_" + String.valueOf(j));
                    stmt.setString(5, "This is project " + String.valueOf(i) + " task " + String.valueOf(j));
                    stmt.executeUpdate();

                } catch (SQLException s) {
                    System.out.println(s);
                }

            }
        }

    }

    //fill user table
    public static void addTestUsers(int numUsers) {
        String[] usernames = {"Jon", "Jerry", "Tim", "Bill", "Leng", "Jojo", "Brice", "Tina", "Tiffany", "Leslie"};

        int pass_hash = "12345678".hashCode();
        int priv_level = 1;

        for (int i = 0; i < numUsers; i++) {
            StringBuilder email = new StringBuilder("abc@g.com");
            email.insert(3, i);
            try {
                PreparedStatement stmt = SynkConnection.con.prepareStatement("INSERT INTO users VALUES(null,?,?,?,?)");
                stmt.setString(1, usernames[i]);
                stmt.setString(2, email.toString());
                stmt.setString(3, String.valueOf(pass_hash));
                stmt.setString(4, String.valueOf(priv_level));
                stmt.executeUpdate();
            } catch (SQLException s) {
                System.out.println(s);
            }

        }
    }
}
