/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Usuario;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Mateus
 */
public class Conexao {

    //  Database credentials
    private static final String USER = "root";
    private static final String PASS = "";
    private static final String SERVERNAME = "localhost";
    private static final String PORT = "3306";
    private static final String DATABASE = "exemplojsp";

    public static JSONArray convertToJSON(ResultSet resultSet)
            throws Exception {
        JSONArray jsonArray = new JSONArray();
        while (resultSet.next()) {
            int total_rows = resultSet.getMetaData().getColumnCount();
            for (int i = 0; i < total_rows; i++) {
                JSONObject obj = new JSONObject();
                obj.put(resultSet.getMetaData().getColumnLabel(i + 1)
                        .toLowerCase(), resultSet.getObject(i + 1));
                jsonArray.put(obj);
            }
        }
        return jsonArray;
    }

    public static String convertToXML(ResultSet resultSet)
            throws Exception {
        StringBuffer xmlArray = new StringBuffer("&lt;results&gt;");
        while (resultSet.next()) {
            int total_rows = resultSet.getMetaData().getColumnCount();
            xmlArray.append("&lt;result ");
            for (int i = 0; i < total_rows; i++) {
                xmlArray.append(" " + resultSet.getMetaData().getColumnLabel(i + 1)
                        .toLowerCase() + "='" + resultSet.getObject(i + 1) + "'");
            }
            xmlArray.append(" /&gt;");
        }
        xmlArray.append("&lt;/results&gt;");
        return xmlArray.toString();
    }

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }
        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", USER);
        connectionProps.put("password", PASS);

        try {
            conn = DriverManager.getConnection("jdbc:mysql://"
                    + SERVERNAME + ":" + PORT + "/" + DATABASE, connectionProps);
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
            conn = null;
        }
        return conn;
    }

    public static void main(String[] args) throws SQLException, Exception {
        /*Connection connection = Conexao.getConnection();

        Statement stmt = connection.createStatement();
        String sql;
        sql = "SELECT * FROM usuario";
        ResultSet rs = stmt.executeQuery(sql);
        JSONArray convertToJSON = convertToJSON(rs);
        System.out.println(convertToJSON);*/

        UsuarioDao uDao = new UsuarioDao();

        Usuario u1 = new Usuario("Mateus", "mateus@email.com");
        uDao.insert(u1);

        Usuario u2 = new Usuario("Morgan", "morgan@email.com");
        uDao.insert(u2);

    }
}
