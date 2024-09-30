package org.example.service;

import org.example.api.ApiClient;
import org.example.model.Todo;
import org.example.model.User;
import io.restassured.response.Response;

import java.util.List;

public class UserService {
    private final ApiClient apiClient = new ApiClient();

    public List<User> getUsers() {
        Response response = apiClient.getUsers();
        return response.jsonPath().getList("$", User.class);
    }

    public List<Todo> getTodosByUserId(int userId) {
        Response response = apiClient.getTodosByUserId(userId);
        return response.jsonPath().getList("$", Todo.class);
    }

    public void validateFanCodeUsers() {
        List<User> users = getUsers();

        for (User user : users) {
            if (isFanCodeCity(user)) {
                List<Todo> todos = getTodosByUserId(user.getId());
                double completedPercentage = calculateCompletedPercentage(todos);
                System.out.printf("User %s has %.2f%% tasks completed.%n", user.getName(), completedPercentage);
                if (completedPercentage <= 50) {
                    System.out.printf("User %s does NOT have more than 50%% tasks completed.%n", user.getName());
                }
            }
        }
    }

    private boolean isFanCodeCity(User user) {
        double lat = user.getAddress().getGeo().getLat();
        double lng = user.getAddress().getGeo().getLng();
        return lat >= -40 && lat <= 5 && lng >= 5 && lng <= 100;
    }

    private double calculateCompletedPercentage(List<Todo> todos) {
        long totalTasks = todos.size();
        long completedTasks = todos.stream().filter(Todo::isCompleted).count();
        return totalTasks > 0 ? (double) completedTasks / totalTasks * 100 : 0;
    }
}
