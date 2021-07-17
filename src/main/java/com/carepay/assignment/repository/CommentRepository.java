package com.carepay.assignment.repository;

import com.carepay.assignment.domain.comments.Comment;
import com.carepay.assignment.domain.posts.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository  extends JpaRepository<Comment, Long> {
    Page<Comment> findCommentByPost(Pageable pageable, Post post);
    List<Comment> findCommentByPost(Post post);
}
