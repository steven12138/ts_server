package org.twt.ts.model;

import jakarta.persistence.*;

@Entity
@Table(name = "friend_request")
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rid")
    private int rid;


}
