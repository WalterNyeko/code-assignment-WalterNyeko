package com.carepay.assignment.domain.posts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostRequest {

    @Size(min = 1, max = 50)
    private String title;
    @Size(min = 1, max = 5000)
    private String content;
}
