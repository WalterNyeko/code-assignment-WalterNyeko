package com.carepay.assignment.helpers;

import com.carepay.assignment.domain.comments.CommentDetails;
import com.carepay.assignment.domain.comments.CommentResponse;
import com.carepay.assignment.domain.posts.PaginationInfo;
import org.springframework.data.domain.Page;
import java.util.ArrayList;

public class CommentMappers {

    public static CommentResponse mapComments(Page<CommentDetails> commentDetails) {
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setContent(commentDetails.getContent().isEmpty() ?
                new ArrayList<>() : commentDetails.getContent());
        commentResponse.setPaginationInfo(
                new PaginationInfo(
                        commentDetails.getPageable().getPageNumber() + 1,
                        commentDetails.getPageable().getPageSize(),
                        commentDetails.getTotalPages(),
                        commentDetails.getTotalElements()));
        return commentResponse;
    }
}
