<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.gift.model.Game, java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Жаңажылдық Сыйлық Есебі</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        h2 { color: #cc0000; }
        .result-box { border: 1px solid #ccc; padding: 15px; margin-bottom: 20px; }
        ul { list-style-type: none; padding: 0; }
        li { margin-bottom: 5px; }
    </style>
</head>
<body>

    <h1>🎁 Жаңажылдық Сыйлық Есебі (Servlet)</h1>
    
    <%
        // Servlet-тен келген деректерді алу
        Double totalWeight = (Double) request.getAttribute("totalWeight");
        List<Game> sortedGames = (List<Game>) request.getAttribute("sortedGames");
        List<Game> foundGames = (List<Game>) request.getAttribute("foundGames");
        double minWeight = (double) request.getAttribute("minWeight");
        double maxWeight = (double) request.getAttribute("maxWeight");
    %>

    <div class="result-box">
        <h2>1. Жалпы Салмақты Анықтау</h2>
        <p>Сыйлықтағы ойындар саны: <strong><%= sortedGames.size() %></strong></p>
        <p>Сыйлықтың жалпы салмағы: <strong><%= String.format("%.2f", totalWeight) %> кг</strong></p>
    </div>

    <div class="result-box">
        <h2>2. Бағасы Бойынша Сұрыптау</h2>
        <p>Ойындар бағасы бойынша (өсу ретімен) сұрыпталды:</p>
        <ul>
            <% for (Game game : sortedGames) { %>
                <li><%= game.getDescription() %></li>
            <% } %>
        </ul>
    </div>

    <div class="result-box">
        <h2>3. Диапазон бойынша Іздеу</h2>
        <h3>Салмағы <%= String.format("%.2f", minWeight) %> кг-дан <%= String.format("%.2f", maxWeight) %> кг-ға дейінгі ойындар:</h3>
        <ul>
            <% if (foundGames.isEmpty()) { %>
                <li>Берілген диапазонға сәйкес келетін ойын табылмады.</li>
            <% } else { %>
                <% for (Game game : foundGames) { %>
                    <li><%= game.getDescription() %></li>
                <% } %>
            <% } %>
        </ul>
    </div>

</body>
</html>