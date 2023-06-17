package com.aishu.platinum.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class ReceiptServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String customerName = request.getParameter("customerName");

        double totalSettleAmount = 0.0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            Class.forName("your_database_driver");
            connection = DriverManager.getConnection("your_database_url", "username", "password");

            String updateInvoiceQuery = "UPDATE invoices SET settled_amount = ? WHERE invoice_number = ?";
            preparedStatement = connection.prepareStatement(updateInvoiceQuery);

            String[] invoiceNumbers = request.getParameterValues("invoiceSettleAmount");
            for (String invoiceNumber : invoiceNumbers) {
                double settleAmount = Double.parseDouble(request.getParameter("invoiceSettleAmount_" + invoiceNumber));
                totalSettleAmount += settleAmount;

                preparedStatement.setDouble(1, settleAmount);
                preparedStatement.setString(2, invoiceNumber);
                preparedStatement.executeUpdate();
            }

            String chequeBalanceQuery = "SELECT cheque_number, balance FROM cheques";
            preparedStatement = connection.prepareStatement(chequeBalanceQuery);
            resultSet = preparedStatement.executeQuery();

            out.println("<h1>Customer Receipt</h1>");
            out.println("<p>Customer Name: " + customerName + "</p>");

            out.println("<h2>Settled Invoices</h2>");
            out.println("<table>");
            out.println("<tr><th>Invoice Number</th><th>Settle Amount</th></tr>");

            String settledInvoiceQuery = "SELECT invoice_number, settled_amount FROM invoices WHERE settled_amount > 0";
            preparedStatement = connection.prepareStatement(settledInvoiceQuery);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String invoiceNumber = resultSet.getString("invoice_number");
                double settledAmount = resultSet.getDouble("settled_amount");
                out.println("<tr><td>" + invoiceNumber + "</td><td>$" + settledAmount + "</td></tr>");
            }
            out.println("</table>");

            out.println("<h2>Cheque Balance Report</h2>");
            out.println("<table>");
            out.println("<tr><th>Cheque Number</th><th>Balance</th></tr>");
            while (resultSet.next()) {
                String chequeNumber = resultSet.getString("cheque_number");
                double balance = resultSet.getDouble("balance");
                out.println("<tr><td>" + chequeNumber + "</td><td>$" + balance + "</td></tr>");
            }

            out.println("</table>");

            out.println("<p>Total Settle Amount: $" + totalSettleAmount + "</p>");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (resultSet != null)
                    resultSet.close();
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
