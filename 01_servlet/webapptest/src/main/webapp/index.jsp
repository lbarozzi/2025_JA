<%@page contentType="text/html" pageEncoding="UTF-8" %>
<head>
    <title>WebApp Test</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        h2 { color: #333; }
        a { color: #007cba; text-decoration: none; padding: 10px; display: inline-block; }
        a:hover { text-decoration: underline; }
    </style>
</head>
<body>
<h2><%= "Hello World!" %></h2>
<p>Benvenuto nella mia applicazione web con Hibernate!</p>

<div>
    <h3>Navigazione:</h3>
    <ul>
        <li><a href="todos.jsp">Gestione Todo List (JSP)</a></li>
        <li><a href="todos">Gestione Todo List (Servlet - deprecata)</a></li>
        <li><a href="hello">Servlet Hello</a></li>
    </ul>
</div>
</body>
</html>
