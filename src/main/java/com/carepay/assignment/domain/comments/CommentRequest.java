package com.carepay.assignment.domain.comments;

import com.carepay.assignment.helpers.APIConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {
    @Size(min = 1, max = 50)
    private String comment;
}