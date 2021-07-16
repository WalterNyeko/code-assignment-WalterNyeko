package com.carepay.assignment.helpers;

import com.carepay.assignment.domain.posts.PostInfo;
import com.carepay.assignment.domain.posts.PostPaginationInfo;
import com.carepay.assignment.domain.posts.PostResponse;
import org.springframework.data.domain.Page;
import java.util.ArrayList;


public class PostMappers {

    public static PostResponse mapPosts(Page<PostInfo> postInfoPage) {
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postInfoPage.getContent().isEmpty() ?
                new ArrayList<>() : postInfoPage.getContent());
        postResponse.setPaginationInfo(
                new PostPaginationInfo(
                        postInfoPage.getPageable().getPageNumber() + 1,
                                    postInfoPage.getPageable().getPageSize(),
                                    postInfoPage.getTotalPages(),
                        postInfoPage.getTotalElements()));
        return postResponse;
    }
}
