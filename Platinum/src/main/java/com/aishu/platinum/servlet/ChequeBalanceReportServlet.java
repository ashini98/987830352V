package com.aishu.platinum.servlet;

import com.aishu.platinum.model.ChequeBalance;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChequeBalanceReportServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3307/platinum_db?useSSL=false&amp;allowPublicKeyRetrieval=true";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "1234";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            String retrieveChequeBalancesQuery = "SELECT * FROM cheque_balances";
            stmt = conn.prepareStatement(retrieveChequeBalancesQuery);
            rs = stmt.executeQuery();

            List<ChequeBalance> chequeBalances = new ArrayList<>();

            while (rs.next()) {
                ChequeBalance chequeBalance = new ChequeBalance();
                chequeBalance.setChequeNumber(rs.getString("cheque_number"));
                chequeBalance.setBalance(rs.getDouble("balance"));
                chequeBalances.add(chequeBalance);
            }

            request.setAttribute("chequeBalances", chequeBalances);
            request.getRequestDispatcher("cheque_balance_report.jsp").forward(request, response);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
