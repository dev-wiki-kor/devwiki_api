package com.dk0124.project.comment.infrastructure.repository;

import com.dk0124.project.comment.infrastructure.CommentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentHistoryRepository extends JpaRepository<CommentHistory, Long> {
}
