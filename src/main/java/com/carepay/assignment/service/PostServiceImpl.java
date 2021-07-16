package com.carepay.assignment.service;

import javax.validation.Valid;

import com.carepay.assignment.domain.posts.CreatePostRequest;
import com.carepay.assignment.domain.posts.Post;
import com.carepay.assignment.domain.posts.PostDetails;
import com.carepay.assignment.domain.posts.PostInfo;
import com.carepay.assignment.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    PostRepository postRepository;

    @Override
    public PostDetails createPost(@Valid CreatePostRequest createPostRequest) {
        throw new IllegalArgumentException("Not implemented"); // TODO: Implement
    }

    @Override
    public Page<PostInfo> getPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> listOfPosts = posts.getContent();
        List<PostInfo> postInfos = new ArrayList<>();
        listOfPosts.stream().forEach(post -> {
            postInfos.add(new PostInfo(post.getId(), post.getTitle()));
        });
        return new PageImpl<>(postInfos, pageable, posts.getTotalElements());
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
