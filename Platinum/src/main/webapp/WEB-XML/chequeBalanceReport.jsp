<%@ page import="com.aishu.platinum.model.ChequeBalance" %><%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 6/17/2023
  Time: 10:07 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cheque Balance Report</title>
</head>
<body>
<table>
    <tr>
        <th>Cheque Number</th>
        <th>Balance</th>
    </tr>
    <%
        for (ChequeBalance chequeBalance : cheque_balances) {
    %>
    <tr>
        <td><%= chequeBalance.getChequeNumber() %></td>
        <td><%= chequeBalance.getBalance() %></td>
    </tr>
    <% } %>
</table>
</body>
</html>
