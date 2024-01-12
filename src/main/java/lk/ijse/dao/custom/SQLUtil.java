package lk.ijse.dao.custom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;

public class SQLUtil {
    public static <T>T execute(String sql, Object... ob) throws SQLException, ClassNotFoundException {
        Connection connection = dbconnection.getdbconnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        for (int i=0;i< ob.length;i++) {
            pstm.setObject((i+1), ob[i]);
        }
        if (sql.startsWith("SELECT")){
            return (T) pstm.executeQuery();
        }else {
            return (T)(Boolean)(pstm.executeUpdate()>0);
        }
    }
}
