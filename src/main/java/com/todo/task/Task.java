package com.todo.task;

import com.todo.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Task implements Comparable<Task> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private String title;
    @ManyToOne
    private User owner;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Task group)) {
            return false;
        }
        return Objects.equals(this.getDescription(), group.getDescription())
                && Objects.equals(this.getOwner(), group.getOwner())
                && Objects.equals(this.getId(), group.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, id, owner);
    }

    @Override
    public String toString() {
        if (id != null) {
            return id + ": " + description;
        } else {
            return description;
        }
    }

    @Override
    public int compareTo(Task o) {
        return Long.compare(this.getId(), o.getId());
    }
}
