package com.carepay.assignment.web;

import javax.validation.Valid;

import com.carepay.assignment.domain.posts.CreatePostRequest;
import com.carepay.assignment.domain.posts.PostDetails;
import com.carepay.assignment.domain.posts.PostInfo;
import com.carepay.assignment.helpers.APIConstants;
import com.carepay.assignment.helpers.PostMappers;
import com.carepay.assignment.service.PostService;
import com.carepay.assignment.service.PostServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/posts", produces = MediaType.APPLICATION_JSON_VALUE)
public class PostController {
    private final PostService postService;

    public PostController(PostServiceImpl postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<Object> getPosts(@RequestParam Optional<Integer> page,
                                   @RequestParam Optional<Integer> pageSize,
                                   @RequestParam Optional<String> sortBy) {
        Page<PostInfo> postResults = postService.getPosts(PageRequest.of(
                page.isPresent() && page.get() > 0 ?
                        page.get() - 1 : page.orElse(APIConstants.DEFAULT_PAGE_NUMBER),
                pageSize.orElse(APIConstants.DEFAULT_PAGE_SIZE),
                Sort.Direction.ASC,
                sortBy.orElse(APIConstants.SORT_POSTS_BY)));
        return new ResponseEntity<>(PostMappers.mapPosts(postResults), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    PostDetails createPost(@Valid @RequestBody CreatePostRequest createPostRequest) {
        return postService.createPost(createPostRequest);
    }

    @GetMapping("{id}")
    PostDetails getPostDetails(@PathVariable("id") final Long id) {
        return postService.getPostDetails(id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    void deletePost(@PathVariable("id") final Long id) {
        postService.deletePost(id);
    }
}
