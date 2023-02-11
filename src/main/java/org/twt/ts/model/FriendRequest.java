package org.twt.ts.model;

import jakarta.persistence.*;

@Entity
@Table(name = "friend_request")
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rid")
    private int rid;

    @JoinColumn(name = "request_from")
    @OneToOne(fetch = FetchType.LAZY)
    private Account from;

    @JoinColumn(name = "request_to")
    @OneToOne(fetch = FetchType.LAZY)
    private Account to;

    @Column(name = "description")
    private String desc;

    @Column(name = "accept")
    private boolean accept = false;

}
