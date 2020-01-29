package com.scoutzknifez.weatherdataanalysis.sql;

import com.scoutzknifez.weatherdataanalysis.utility.HiddenConstants;
import com.scoutzknifez.weatherdataanalysis.utility.Utils;
import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@Getter
public abstract class Worker implements Runnable {
    public static Connection connection;
    private Statement statement;
    @Setter private Table table;

    public Worker(Table table) {
        setTable(table);
        ready();
    }

    /**
     * Gets the MySQL connector ready to work.
     */
    private void ready() {
        if (connection == null) {
            if (!connectToDriver()) {
                Utils.log("Could not connect driver for MySQL.");
                return; // TODO should instead throw exceptions rather than returning
            }

            connection = connectToMySQLDatabase();
            if (connection == null) {
                Utils.log("Could not connect to MySQL Database.");
                return;
            }
        }

        statement = getSQLStatement();
        if (statement == null)
            Utils.log("Could not get MySQL statement.");
    }

    /**
     * Gets the driver activated and fails if not present
     * @return  Fail/Success
     */
    private boolean connectToDriver() {
        Utils.log("Connecting driver!");
        try {
            Class.forName(HiddenConstants.JDBC_DRIVER);

            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Connects directly to the MySQL database with given credentials.
     * @return  The connection to the database.
     */
    private Connection connectToMySQLDatabase() {
        try {
            return DriverManager.getConnection(HiddenConstants.DATABASE_URL, HiddenConstants.USERNAME, HiddenConstants.PASSWORD);
        } catch (Exception e) {
            Utils.log("Could not connect to MySQL database at " + HiddenConstants.DATABASE_URL);
            return null;
        }
    }

    /**
     * Creates a new instance of a statement for the connected database.
     * @return  A statement for the connected database.
     */
    private Statement getSQLStatement() {
        try {
            return connection.createStatement();
        } catch (Exception e) {
            Utils.log("Could not create MySQL statement.");
            return null;
        }
    }

    /**
     * Closes the currently open statement.
     */
    protected void closeStatement() {
        try {
            statement.close();
        } catch (Exception e) {
            Utils.log("Could not close statement for connection.");
        }
    }

    /**
     * Closes the connection to the database safely, ending all open statements.
     */
    protected void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            Utils.log("Could not close connection.");
        }
    }
}
