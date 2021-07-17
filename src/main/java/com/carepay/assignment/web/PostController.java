package com.carepay.assignment.web;

import javax.validation.Valid;
import com.carepay.assignment.domain.posts.CreatePostRequest;
import com.carepay.assignment.domain.posts.PostDetails;
import com.carepay.assignment.domain.posts.PostInfo;
import com.carepay.assignment.exceptions.InvalidBlogPostException;
import com.carepay.assignment.exceptions.PostNotFoundException;
import com.carepay.assignment.helpers.APIConstants;
import com.carepay.assignment.helpers.PostMappers;
import com.carepay.assignment.service.PostService;
import com.carepay.assignment.service.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    PostService postService;

    public PostController(PostServiceImpl postService) {
        this.postService = postService;
    }

    /**
     * Retrieve Paginated List of Posts
     * @param page the currently showing page number
     * @param pageSize the number of posts showing on the current page
     * @param sortBy the field that is used for sorting the posts
     * @return
     */
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

    /**
     * Creates a new Blog Post
     * @param createPostRequest
     * @return PostDetails of the created post
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    PostDetails createPost(@Valid @RequestBody CreatePostRequest createPostRequest) {
        if (createPostRequest == null ||
                createPostRequest.getTitle() == null ||
                createPostRequest.getContent() == null)
            throw new InvalidBlogPostException(APIConstants.INVALID_POST);
        return postService.createPost(createPostRequest);
    }

    /**
     * Retrieves a single blog post using its ID
     * @param id the ID that is used to identify which post to retrieve
     * @return PostDetails of the retrieved post
     */
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    PostDetails getPostDetails(@PathVariable("id") final Long id) {
        PostDetails postDetails = postService.getPostDetails(id);
        if (postDetails.getId() != null) {
            return postDetails;
        }
        throw new PostNotFoundException(APIConstants.POST_NOT_FOUND);
    }

    /**
     * Deletes an existing blog post using its ID
     * @param id the ID of the blog post which should be deleted
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    void deletePost(@PathVariable("id") final Long id) {
        postService.deletePost(id);
    }

}
