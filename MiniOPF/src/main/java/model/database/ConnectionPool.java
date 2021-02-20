package model.database;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionPool
{
    private static DataSource dataSource;

    static
    {
        try
        {
            InitialContext initContext = new InitialContext();
            dataSource = (DataSource) initContext.lookup("java:comp/env/jdbc/miniopf");
        }
        catch (NamingException e)
        {
            e.printStackTrace();
        }
    }

    private ConnectionPool()
    {
    }

    public static Connection getConnection() throws SQLException
    {
        return dataSource.getConnection();
    }

    public static void close(Connection connection)
    {
        try
        {
            if (connection != null)
            {
                connection.close();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
