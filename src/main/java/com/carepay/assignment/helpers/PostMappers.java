package com.carepay.assignment.helpers;

import com.carepay.assignment.domain.comments.Comment;
import com.carepay.assignment.domain.comments.CommentInfo;
import com.carepay.assignment.domain.posts.*;
import org.springframework.data.domain.Page;
import java.util.ArrayList;
import java.util.List;


public class PostMappers {

    public static PostResponse mapPosts(Page<PostInfo> postInfoPage) {
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postInfoPage.getContent().isEmpty() ?
                new ArrayList<>() : postInfoPage.getContent());
        postResponse.setPaginationInfo(
                new PaginationInfo(
                        postInfoPage.getPageable().getPageNumber() + 1,
                                    postInfoPage.getPageable().getPageSize(),
                                    postInfoPage.getTotalPages(),
                        postInfoPage.getTotalElements()));
        return postResponse;
    }

    public static PostDetails mapPostDetails(Post post, List<CommentInfo> comments) {
        PostDetails postDetails = new PostDetails();
        if (post != null) {
            postDetails.setContent(post.getContent());
            postDetails.setId(post.getId());
            postDetails.setTitle(post.getTitle());
            postDetails.setComments(comments);
        }
        return postDetails;
    }
}
