package com.springboot.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.service.CommentService;
import com.springboot.blog.service.PostService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {
	
		Comment comment=mapToEntity(commentDto);
		Post post = postService.GetingPostById(postId);
		comment.setPost(post);
		commentRepository.save(comment);
		return mapToDto(comment);
	}

	private CommentDto mapToDto(Comment comment) {
//		CommentDto commentDto= new CommentDto();
//		commentDto.setId(comment.getId());
//		commentDto.setName(comment.getName());
//		commentDto.setEmail(comment.getEmail());
//		commentDto.setBody(comment.getBody());
//		
		CommentDto commentDto=mapper.map(comment, CommentDto.class);
		
		return commentDto;
	}
	private Comment mapToEntity(CommentDto commentDto) {
		
//		Comment comment= new Comment();
//		comment.setId(commentDto.getId());
//		comment.setEmail(commentDto.getEmail());
//		comment.setName(commentDto.getName());
//		comment.setBody(commentDto.getBody());
		
		Comment comment=mapper.map(commentDto, Comment.class);
		return comment;
	}

	@Override
	public List<CommentDto> getCommentByPostId(Long PostId) {
		List<Comment> comments = commentRepository.findByPostId(PostId);
		
		return comments.stream().map((commnet-> mapToDto(commnet))).collect(Collectors.toList());
	}

	@Override
	public CommentDto getCommnetById(Long commentId, Long postId) {
		
		Post post = postService.GetingPostById(postId);
		Comment comment = GetingCommentById(commentId);
		
		if(!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
		}
		return mapToDto(comment);
	}
	@Override
	public Comment GetingCommentById(Long id) {
		return commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
	}

	@Override
	public CommentDto updateComment(Long postId, Long commentId, CommentDto commentReuest) {
		Post post = postService.GetingPostById(postId);
		Comment comment = GetingCommentById(commentId);
		
		if(!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
		}
		comment.setName(commentReuest.getName());
		comment.setEmail(commentReuest.getEmail());
		comment.setBody(commentReuest.getBody());
		
		commentRepository.save(comment);
		return mapToDto(comment);
	}

	@Override
	public void deleteComment(Long postId, Long commentId) {
		
		Post post = postService.GetingPostById(postId);
		Comment comment = GetingCommentById(commentId);
		if(!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
		}
		commentRepository.delete(comment);
		
	}

}
