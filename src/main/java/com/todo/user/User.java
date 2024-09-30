package com.todo.user;

import com.todo.company.Company;
import com.todo.task.Task;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany
    private List<Task> tasks;
    @ManyToOne
    private Company company;
    @Enumerated(EnumType.STRING)
    private Role role;
}
