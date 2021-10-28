package com.probal.blogapp.service;

import com.probal.blogapp.dao.CommentDao;
import com.probal.blogapp.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    CommentDao commentDao;

    public void saveComment(Comment comment) {
        commentDao.save(comment);
    }
}
