package org.twt.ts.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table
@Entity
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrivateMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "private_message_id")
    private int pe_id;
    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String desc;

    @Column(name = "file")
    private String file;


    @JoinColumn(name = "sender")
    @ManyToOne(fetch = FetchType.LAZY)
    private Account sender;

    @JoinColumn(name = "receiver")
    @ManyToOne(fetch = FetchType.LAZY)
    private Account receiver;

    @Column(name = "date")
    private LocalDateTime dateTime = LocalDateTime.now();
}
