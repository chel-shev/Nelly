package dev.chel_shev.nelly.entity.users;

import dev.chel_shev.nelly.entity.event.EventEntity;
import dev.chel_shev.nelly.entity.workout.ExerciseEntity;
import dev.chel_shev.nelly.entity.workout.WorkoutEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user_subscription")
@IdClass(value = UserSubscriptionEntity.UserSubscriptionEntityId.class)
public class UserSubscriptionEntity {

    @Id
    @ManyToOne
    private UserEntity user;

    @Id
    @ManyToOne
    private SubscriptionEntity subscription;

    @OneToMany(mappedBy = "userSubscription", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventEntity> eventList;

    @Data
    public static class UserSubscriptionEntityId implements Serializable {
        private long user;
        private long subscription;
    }
}
