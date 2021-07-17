package com.carepay.assignment.domain.comments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentInfo extends CommentRequest{
    private long commentId;
}
