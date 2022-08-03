package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.payload.CommentDto;

public interface CommentService {

	CommentDto createComment(long postId, CommentDto commentDto);
	
	List<CommentDto> getCommentByPostId(Long PostId);

	CommentDto getCommnetById(Long commentId, Long postId);

	Comment GetingCommentById(Long id);
	
	CommentDto updateComment(Long postId, Long commentId, CommentDto commentReuest);
	
	void deleteComment(Long postId, Long commentId);

	
}
