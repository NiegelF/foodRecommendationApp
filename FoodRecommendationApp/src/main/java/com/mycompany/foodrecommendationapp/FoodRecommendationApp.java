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
    private final JTextField heightField;
    private final JTextField weightField;
    private final JComboBox<String> genderComboBox;
    private JTextArea recommendationArea;
    private JButton recommendButton;

    // Sample food data (name, price, calories, where to find, macros)
    private String[][] foodData = {
            {"Rice", "2.50", "200", "Grocery Store A", "Carbs: 45g, Protein: 5g, Fat: 0.5g"},
            {"Pasta", "4.50", "250", "Grocery Store B", "Carbs: 50g, Protein: 10g, Fat: 2g"},
            {"Oatmeal", "2.00", "150", "Grocery Store A", "Carbs: 30g, Protein: 5g, Fat: 2.5g"},
            {"Peanut Butter", "3.50", "180", "Grocery Store B", "Carbs: 7g, Protein: 8g, Fat: 14g"},
            {"Potatoes", "2.00", "160", "Local Market", "Carbs: 37g, Protein: 2g, Fat: 0.2g"},
            {"Beans", "1.50", "200", "Local Market", "Carbs: 35g, Protein: 7g, Fat: 0.5g"},
            {"Eggs", "3.00", "150", "Grocery Store A", "Carbs: 1g, Protein: 13g, Fat: 10g"},
            {"Bananas", "0.50", "105", "Local Market", "Carbs: 27g, Protein: 1g, Fat: 0.3g"},
            {"Bread", "2.00", "200", "Grocery Store B", "Carbs: 40g, Protein: 10g, Fat: 1g"},
            {"Pasta Sauce", "1.50", "70", "Grocery Store B", "Carbs: 14g, Protein: 2g, Fat: 0.5g"},
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
        JLabel heightLabel = new JLabel("Height (cm):");
        heightField = new JTextField(10);
        JLabel weightLabel = new JLabel("Weight (kg):");
        weightField = new JTextField(10);
        JLabel genderLabel = new JLabel("Gender:");
        String[] genderOptions = {"Male", "Female"};
        genderComboBox = new JComboBox<>(genderOptions);
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
        inputPanel.add(heightLabel, constraints);
        constraints.gridx = 1;
        inputPanel.add(heightField, constraints);
        constraints.gridy = 3;
        constraints.gridx = 0;
        inputPanel.add(weightLabel, constraints);
        constraints.gridx = 1;
        inputPanel.add(weightField, constraints);
        constraints.gridy = 4;
        constraints.gridx = 0;
        inputPanel.add(genderLabel, constraints);
        constraints.gridx = 1;
        inputPanel.add(genderComboBox, constraints);
        constraints.gridy = 5;
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
        int height = Integer.parseInt(heightField.getText());
        double weight = Double.parseDouble(weightField.getText());
        String gender = (String) genderComboBox.getSelectedItem();

        // Calculate daily calorie needs based on user input (a basic calculation)
        int dailyCalorieNeeds;
        if (gender.equals("Male")) {
            dailyCalorieNeeds = (int) (88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * 25));
        } else {
            dailyCalorieNeeds = (int) (447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * 25));
        }

        StringBuilder recommendations = new StringBuilder("Recommended Foods:\n");
        double totalCost = 0.0;
        int totalCalories = 0;

        for (String[] food : foodData) {
            String foodName = food[0];
            double foodPrice = Double.parseDouble(food[1]);
            int foodCalories = Integer.parseInt(food[2]);
            String whereToFind = food[3];
            String macros = food[4];

            double calorieToPriceRatio = foodCalories / foodPrice;

            if (totalCost + foodPrice <= budget && foodCalories <= calorieNeeds && totalCalories + foodCalories <= dailyCalorieNeeds) {
                recommendations.append(foodName).append(" ($").append(foodPrice).append(", ").append(foodCalories).append(" calories, ").append(macros).append(")\n");
                recommendations.append("Where to Find: ").append(whereToFind).append("\n\n");
                totalCost += foodPrice;
                totalCalories += foodCalories;
            }
        }

        recommendations.append("\nTotal Cost: $").append(totalCost);
        recommendations.append("\nTotal Calories: ").append(totalCalories);
        recommendations.append("\nDaily Calorie Needs: ").append(dailyCalorieNeeds);
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


