<%@ page import="java.sql.*" %><%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 6/17/2023
  Time: 9:13 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Customer Receipt</title>
</head>
<body>
    <h1>Customer Receipt</h1>
    <form action="ReceiptServlet" method="post">
        <label for="customerName">Customer Name: </label>
        <input type="text" name="customerName" id="customerName" required><br><br>

        <h2>Settle Invoices</h2>
        <table>
            <tr>
                <th>Invoice Number</th>
                <th>Amount</th>
                <th>Settle Amount</th>
            </tr>

            <%
                Connection connection = null;
                Statement statement = null;
                ResultSet resultSet = null;
                try {
                    Class.forName("your_database_driver");
                    connection = DriverManager.getConnection("your_database_url", "username", "password");
                    statement = connection.createStatement();
                    String query = "SELECT * FROM invoices";
                    resultSet = statement.executeQuery(query);
                    while (resultSet.next()) {
                        String invoiceNumber = resultSet.getString("invoice_number");
                        double amount = resultSet.getDouble("amount");
            %>

            <tr>
                <td><%= invoiceNumber %></td>
                <td>$<%= amount %></td>
                <td><input type="number" name="invoiceSettleAmount_<%= invoiceNumber %>" value="0"></td>
            </tr>

            <%
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (resultSet != null)
                            resultSet.close();
                        if (statement != null)
                            statement.close();
                        if (connection != null)
                            connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            %>

        </table>
        <br>

        <h2>Cheque Balance Report</h2>
        <table>
            <tr>
                <th>Cheque Number</th>
                <th>Balance</th>
            </tr>

            <%
                try {
                    Class.forName("your_database_driver");
                    connection = DriverManager.getConnection("your_database_url", "username", "password");
                    statement = connection.createStatement();
                    String query = "SELECT * FROM cheques";
                    resultSet = statement.executeQuery(query);
                    while (resultSet.next()) {
                        String chequeNumber = resultSet.getString("cheque_number");
                        double balance = resultSet.getDouble("balance");
            %>

            <tr>
                <td><%= chequeNumber %></td>
                <td>$<%= balance %></td>
            </tr>

            <%
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                    try {
                        if (resultSet != null)
                            resultSet.close();
                        if (statement != null)
                            statement.close();
                        if (connection != null)
                            connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            %>
        </table>
        <input type="submit" value="Generate Receipt">

    </form>

</body>
</html>
