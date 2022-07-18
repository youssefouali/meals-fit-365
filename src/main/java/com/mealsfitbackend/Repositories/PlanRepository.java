package com.mealsfitbackend.Repositories;

import com.mealsfitbackend.entities.Plan;
import com.mealsfitbackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface PlanRepository  extends JpaRepository<Plan, Integer> {

    @Query("select p from Plan p where p.user = ?1 AND p.startDate <= ?2 AND p.endDate >= ?2")
    Plan findByUserIdAndStartDateBeforeAndEndDateAfter(User user, LocalDate today);

    List<Plan> findAllByUserId(long userId);

    List<Plan> findAllByUserIdAndEndDateGreaterThanEqual(long userId, LocalDate date);

    List<Plan> findAllByUserIdAndEndDateLessThan(long userId, LocalDate date);
}
