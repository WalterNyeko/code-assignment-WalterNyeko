package com.carepay.assignment.domain.comments;

import com.carepay.assignment.domain.posts.Post;
import com.carepay.assignment.helpers.APIConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String comment;

    @OneToOne
    @JoinColumn(name = APIConstants.COMMENT_JOIN_COLUMN_NAME,
            referencedColumnName = APIConstants.POST_ID)
    private Post post;
}
