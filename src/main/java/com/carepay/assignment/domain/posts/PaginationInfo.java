package com.carepay.assignment.domain.posts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationInfo {
    private int pageNumber;
    private int pageSize;
    private long totalPages;
    private long totalNumberOfPosts;
}
