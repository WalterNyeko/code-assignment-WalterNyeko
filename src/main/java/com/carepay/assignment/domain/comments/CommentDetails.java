package com.carepay.assignment.domain.comments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDetails {
    private long commentId;
    private long postId;
    private String comment;

}
