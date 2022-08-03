package com.springboot.blog.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;

@RestController
@RequestMapping("/api/v1")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDto> createComment (@PathVariable Long postId,@Valid @RequestBody CommentDto commentDto) {
		return new ResponseEntity<CommentDto>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
	}

	@GetMapping("/posts/{postId}/comments")
	public List<CommentDto> getCommnetsByPostId(@PathVariable Long postId) {
		return commentService.getCommentByPostId(postId);
	}

	@GetMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> getCommnetById(@PathVariable Long commentId, @PathVariable Long postId) {
		return new ResponseEntity<CommentDto>(commentService.getCommnetById(commentId, postId), HttpStatus.OK);
	}

	@PutMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> updateComment(@PathVariable Long commentId,@PathVariable Long postId,
			@Valid @RequestBody CommentDto commentDto) {
		return new ResponseEntity<CommentDto>(commentService.updateComment(postId, commentId, commentDto),
				HttpStatus.OK);

	}
	@DeleteMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<String> deleteCommet(@PathVariable Long commentId, @PathVariable Long postId){
		commentService.deleteComment(postId, commentId);
		return new ResponseEntity<>("comment deleted sucessfully", HttpStatus.OK);
	}

}
