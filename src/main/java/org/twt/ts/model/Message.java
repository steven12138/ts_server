package org.twt.ts.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "message")
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String desc;

    @Column(name = "file")
    private String file;


    @JoinColumn(name = "sender")
    @ManyToOne(fetch = FetchType.LAZY)
    private Account sender;

    @Column(name = "date")
    private LocalDateTime dateTime = LocalDateTime.now();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Account> disabled;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Account> likes;
}
