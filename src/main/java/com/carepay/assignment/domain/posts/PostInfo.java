package com.carepay.assignment.domain.posts;

import com.carepay.assignment.domain.comments.CommentInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class  PostInfo {
    private Long id;
    private String title;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<CommentInfo> comments;

    public PostInfo(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}
