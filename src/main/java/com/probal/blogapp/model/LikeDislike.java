package com.probal.blogapp.model;


import com.probal.blogapp.enums.VoteType;
import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "like_dislike")
public class LikeDislike {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private VoteType voteType;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    private Post post;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    public LikeDislike(VoteType voteType, Post post, User user) {
        this.voteType = voteType;
        this.post = post;
        this.user = user;
    }

    public LikeDislike() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VoteType getVoteType() {
        return voteType;
    }

    public void setVoteType(VoteType voteType) {
        this.voteType = voteType;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
