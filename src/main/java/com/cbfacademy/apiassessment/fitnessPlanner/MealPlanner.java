package com.cbfacademy.apiassessment.fitnessPlanner;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface MealPlanner {

    public List<Ideas> mealType(String type) throws IOException;
    public Ideas generateMealPlan(String mealType) throws IOException;

    public HashMap<String, Ideas> generateFullDayMeal() throws IOException;
}
