/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.foodrecommendationapp;

/**
 *
 * @author Niegel
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FoodRecommendationApp extends JFrame {
    private final JTextField budgetField;
    private final JTextField calorieField;
    private JTextArea recommendationArea;
    private JButton recommendButton;

    // Sample food data (name, price, calories)
    private String[][] foodData = {
            {"Rice", "2.50", "200"},
            {"Pasta", "4.50", "250"},
            {"Oatmeal", "2.00", "150"},
            {"Peanut Butter", "3.50", "180"},
            {"Potatoes", "2.00", "160"},
            {"Beans", "1.50", "200"},
            {"Eggs", "3.00", "150"},
            {"Bananas", "0.50", "105"},
            {"Bread", "2.00", "200"},
            {"Pasta Sauce", "1.50", "70"},
            // Add more budget-friendly, high-calorie foods here...
    };

    public FoodRecommendationApp() {
        setTitle("Food Recommendation App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel budgetLabel = new JLabel("Budget:");
        budgetField = new JTextField(10);
        JLabel calorieLabel = new JLabel("Calorie Needs:");
        calorieField = new JTextField(10);
        recommendButton = new JButton("Recommend");

        inputPanel.add(budgetLabel, constraints);
        constraints.gridx = 1;
        inputPanel.add(budgetField, constraints);
        constraints.gridy = 1;
        constraints.gridx = 0;
        inputPanel.add(calorieLabel, constraints);
        constraints.gridx = 1;
        inputPanel.add(calorieField, constraints);
        constraints.gridy = 2;
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        inputPanel.add(recommendButton, constraints);

        add(inputPanel, BorderLayout.NORTH);

        recommendationArea = new JTextArea();
        recommendationArea.setEditable(false);
        recommendationArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(recommendationArea);
        add(scrollPane, BorderLayout.CENTER);

        // Customizing button appearance
        recommendButton.setBackground(new Color(0, 123, 255));
        recommendButton.setForeground(Color.WHITE);
        recommendButton.setFocusPainted(false);
        recommendButton.setBorderPainted(false);
        recommendButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect for the button
        recommendButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                recommendButton.setBackground(new Color(30, 144, 255));
            }

            public void mouseExited(MouseEvent e) {
                recommendButton.setBackground(new Color(0, 123, 255));
            }
        });

        recommendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recommendFood();
            }
        });
    }

    private void recommendFood() {
        double budget = Double.parseDouble(budgetField.getText());
        int calorieNeeds = Integer.parseInt(calorieField.getText());

        StringBuilder recommendations = new StringBuilder("Recommended Foods:\n");
        double totalCost = 0.0;
        int totalCalories = 0;

        for (String[] food : foodData) {
            String foodName = food[0];
            double foodPrice = Double.parseDouble(food[1]);
            int foodCalories = Integer.parseInt(food[2]);

            if (totalCost + foodPrice <= budget && foodCalories <= calorieNeeds) {
                recommendations.append(foodName).append(" ($").append(foodPrice).append(", ").append(foodCalories).append(" calories)\n");
                totalCost += foodPrice;
                totalCalories += foodCalories;
            }
        }

        recommendations.append("\nTotal Cost: $").append(totalCost);
        recommendations.append("\nTotal Calories: ").append(totalCalories);
        recommendationArea.setText(recommendations.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                FoodRecommendationApp app = new FoodRecommendationApp();
                app.setVisible(true);
            }
        });
    }
}

