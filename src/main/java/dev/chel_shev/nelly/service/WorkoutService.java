package dev.chel_shev.nelly.service;

import dev.chel_shev.fast.FastMarkdown;
import dev.chel_shev.fast.entity.FastCommandEntity;
import dev.chel_shev.fast.entity.user.FastUserSubscriptionEntity;
import dev.chel_shev.fast.event.workout.FastWorkoutEvent;
import dev.chel_shev.fast.repository.UserSubscriptionRepository;
import dev.chel_shev.fast.service.FastCommandService;
import dev.chel_shev.fast.type.SubscriptionType;
import dev.chel_shev.nelly.entity.workout.ExerciseEntity;
import dev.chel_shev.nelly.entity.workout.WorkoutEntity;
import dev.chel_shev.nelly.entity.workout.WorkoutExerciseEntity;
import dev.chel_shev.nelly.repository.workout.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static dev.chel_shev.nelly.type.KeyboardType.*;
import static dev.chel_shev.nelly.type.KeyboardType.INLINE_DONE;
import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class WorkoutService {
    private final WorkoutRepository repository;
    private final UserSubscriptionRepository userSubscriptionRepository;
    private final ExerciseService exerciseService;
    private final FastCommandService commandService;

    public List<String> getAvailableWorkouts(String chatId) {
        Set<String> allWorkouts = repository.findAll().stream().map(WorkoutEntity::getName).collect(Collectors.toSet());
        List<String> userWorkouts = getUserWorkouts(chatId);
        userWorkouts.forEach(allWorkouts::remove);
        return allWorkouts.stream().toList();
    }

    public List<String> getUserWorkouts(String chatId) {
        FastCommandEntity command = commandService.getCommand("/workout");
        return userSubscriptionRepository.findAllByFastUser_ChatIdAndParentCommandAndType(chatId, command, SubscriptionType.SUB).stream().map(FastUserSubscriptionEntity::getName).toList();
    }

    public WorkoutEntity getByName(String workoutName) {
        return repository.findByName(workoutName);
    }


    public void updateExercise(WorkoutEntity workoutEntity, Integer step, String fileId) {
        List<WorkoutExerciseEntity> exerciseList = exerciseService.getExerciseList(workoutEntity.getId());
        ExerciseEntity exerciseEntity = exerciseList.get(step).getExercise();
        exerciseEntity.setFileId(fileId);
        exerciseService.save(exerciseEntity);
    }

    public void updateWorkout(WorkoutEntity workoutEntity, String fileId) {
        workoutEntity.setFileId(fileId);
        repository.save(workoutEntity);
    }

    public String getWorkoutTitle(ExerciseEntity exercise, int countExercise, int step, int level) {
        return FastMarkdown.code((step + 1) + " / " + countExercise) + " \\| " +
                FastMarkdown.code(exercise.getName()) +
                FastMarkdown.code((!isNull(exercise.getComment()) ? ("(" + exercise.getComment() + ")") : "")) + " \\| " +
                FastMarkdown.code(exercise.getReps() * level + exercise.getType().getLabel());
    }

    public ExerciseEntity getExercise(Long id, Integer step) {
        List<WorkoutExerciseEntity> exerciseList = exerciseService.getExerciseList(id);
        return exerciseList.get(step).getExercise();
    }

    public List<String> getWorkoutProcess(FastWorkoutEvent event) {
        List<String> buttons = new ArrayList<>();
        int allSteps = event.getWorkout().getCountExercise();
        int step;
        if (event.getWorkout().isProgressable())
            step = event.getStep();
        else
            step = event.getStep() + 1;
        if (allSteps > 0 && step == -1) {
            buttons.add(INLINE_START.getLabel());
        } else if (allSteps > step && step > 0) {
            buttons.add(INLINE_PREV.getLabel());
            buttons.add(INLINE_NEXT.getLabel());
        } else if (allSteps > step) {
            buttons.add(INLINE_NEXT.getLabel());
        } else {
            if (event.getWorkout().isProgressable()) {
                buttons.add(INLINE_UP.getLabel());
                buttons.add(INLINE_DOWN.getLabel());
            } else {
                buttons.add(INLINE_PREV.getLabel());
            }
            buttons.add(INLINE_DONE.getLabel());
        }
        return buttons;
    }
}
