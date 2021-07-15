package com.carepay.assignment.domain.posts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class  PostInfo {
    private Long id;
    private String title;
}
