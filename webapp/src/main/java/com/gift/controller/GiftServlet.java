package com.gift.controller;

import com.gift.model.BoardGame;
import com.gift.model.Game;
import com.gift.model.Gift;
import com.gift.model.SoftToy;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

// Сервлеттің URL мекенжайын анықтаймыз
@WebServlet("/gift-report")
public class GiftServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Сыйлықты құру және объектілерді қосу
        Gift christmasGift = createGift();

        // 2. Жалпы салмақты есептеу
        double totalWeight = christmasGift.calculateTotalWeight();

        // 3. Сұрыптау (Бағасы бойынша)
        christmasGift.sortGames(Comparator.comparing(Game::getPrice));

        // 4. Диапазон бойынша іздеу (Салмағы 0.4 кг-дан 0.9 кг-ға дейін)
        double minWeight = 0.40;
        double maxWeight = 0.90;
        List<Game> foundGames = christmasGift.findGamesByWeightRange(minWeight, maxWeight);

        // 5. Деректерді JSP бетіне жіберу
        request.setAttribute("gift", christmasGift);
        request.setAttribute("totalWeight", totalWeight);
        request.setAttribute("sortedGames", christmasGift.getGames());
        request.setAttribute("foundGames", foundGames);
        request.setAttribute("minWeight", minWeight);
        request.setAttribute("maxWeight", maxWeight);

        // Нәтижелерді көрсету үшін JSP бетіне бағыттау
        request.getRequestDispatcher("/giftResult.jsp").forward(request, response);
    }

    // Деректерді (ойын объектілерін) құру әдісі
    private Gift createGift() {
        Gift gift = new Gift();
        
        // Объектілерді құру және қосу
        gift.addGame(new BoardGame("Monopoliya", 0.85, 5500.00, 2));
        gift.addGame(new SoftToy("Jumsak Ayu", 0.45, 4000.00, "Poliester"));
        gift.addGame(new BoardGame("Shakhmat", 1.10, 8000.00, 2));
        
        return gift;
    }
}