package com.mealsfitbackend.controllers;

import com.mealsfitbackend.entities.Plan;
import com.mealsfitbackend.entities.User;
import com.mealsfitbackend.enumerations.MealType;
import com.mealsfitbackend.services.PlanService;
import com.mealsfitbackend.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class LoginController {
    @Autowired
    PlanService planService;

    //@Autowired
    UserService userService;

    @GetMapping("showLogin")
    public String showLogin() {
        return "login";
    }

    @GetMapping("home")
    public String showHome(Model model) {
        User user = userService.getCurrentUser();
        Plan plan = planService.getCurrentPlan(user);
        if (plan == null)
            log.debug("User doesn't have an active plan");
        if (plan != null) {
            model.addAttribute("meals", plan.getMealsForToday());
            model.addAttribute("planId", plan.getId());
        }
        model.addAttribute("mealTypes", MealType.values());
        return "home";
    }

    @GetMapping("/")
    public String showLandingPage(Authentication authentication) {
//		//uncomment if logged user shouldn't see index(landing) page
//		if (authentication != null)
//			if (authentication.isAuthenticated())
//				return "home";
//
        return "index";
    }

}
