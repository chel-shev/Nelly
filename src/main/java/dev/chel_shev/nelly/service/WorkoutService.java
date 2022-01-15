package dev.chel_shev.nelly.service;

import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.entity.workout.UserWorkoutEntity;
import dev.chel_shev.nelly.entity.workout.WorkoutEntity;
import dev.chel_shev.nelly.repository.UserWorkoutRepository;
import dev.chel_shev.nelly.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutService {

    private final WorkoutRepository repository;
    private final UserWorkoutRepository userWorkoutRepository;

    public List<String> getUnusedWorkout(UserEntity user) {
        List<WorkoutEntity> allWorkouts = repository.findAll();
        List<UserWorkoutEntity> byUserWorkouts = userWorkoutRepository.findByUserId(user.getId());
        return allWorkouts.stream().filter(e -> !byUserWorkouts.contains(e)).map(e -> e.getName()).toList();
    }
}
