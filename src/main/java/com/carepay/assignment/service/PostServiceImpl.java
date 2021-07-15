package com.carepay.assignment.service;

import javax.validation.Valid;

import com.carepay.assignment.domain.posts.CreatePostRequest;
import com.carepay.assignment.domain.posts.PostDetails;
import com.carepay.assignment.domain.posts.PostInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {
    @Override
    public PostDetails createPost(@Valid CreatePostRequest createPostRequest) {
        throw new IllegalArgumentException("Not implemented"); // TODO: Implement
    }

    @Override
    public Page<PostInfo> getPosts(Pageable pageable) {
        throw new IllegalArgumentException("Not implemented"); // TODO: Implement
    }

    @Override
    public PostDetails getPostDetails(Long id) {
        throw new IllegalArgumentException("Not implemented"); // TODO: Implement
    }

    @Override
    public void deletePost(Long id) {
        throw new IllegalArgumentException("Not implemented"); // TODO: Implement
    }
}
