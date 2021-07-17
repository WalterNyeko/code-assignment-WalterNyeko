package com.carepay.assignment.domain.comments;

import com.carepay.assignment.domain.posts.PaginationInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private List<CommentDetails> content;
    private PaginationInfo paginationInfo;
}