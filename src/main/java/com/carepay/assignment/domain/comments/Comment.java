package com.carepay.assignment.domain.comments;

import com.carepay.assignment.domain.posts.Post;
import com.carepay.assignment.helpers.APIConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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

    @ManyToOne
    @JoinColumn(name = APIConstants.COMMENT_JOIN_COLUMN_NAME,
            referencedColumnName = APIConstants.POST_ID)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;
}
