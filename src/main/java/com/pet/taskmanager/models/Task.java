package com.pet.taskmanager.models;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Data
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @GenericGenerator(name = "native", strategy = "native", parameters = @org.hibernate.annotations.Parameter(name = "hibernate_sequence", value = "0"))
    private Long id;

    @Column
    private String header;

    @Column(columnDefinition = "text")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Task() {
    }

    public Task(String header, String description, User user) {
        this.header = header;
        this.description = description;
        this.user = user;
    }
}


