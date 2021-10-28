package com.probal.blogapp.enums;

public enum VoteType {
    LIKE(0),
    DISLIKE(1);

    private int id;

    VoteType(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }


}
