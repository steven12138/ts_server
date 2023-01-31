package org.twt.ts.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "friend")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eid" )
    private int eid;

    @ToString.Exclude
    @JoinColumn(name = "u", nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private Account u;

    @JoinColumn(name = "v", nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private Account v;

}
