package com.saad.socialmedia.Repository;

import com.saad.socialmedia.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer>
{

}
