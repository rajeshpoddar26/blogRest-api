package com.springboot.blog.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

	@Autowired
	private PostService postService;
	
	@PostMapping
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto dto){
		
		return new ResponseEntity<PostDto>(postService.createPost(dto), HttpStatus.CREATED);
	}
	@PutMapping("/{id}")
	public ResponseEntity<PostDto> updatePost(@PathVariable Long id, @Valid @RequestBody PostDto dto){
		return  new ResponseEntity<PostDto>( postService.updatePost(id, dto), HttpStatus.OK);
	}
	
	@GetMapping
	public PostResponse getAllPost(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
			){
		return postService.getAllPost(pageNo, pageSize, sortBy, sortDir);
	}
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Long id){
		return ResponseEntity.ok(postService.getPostById(id));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePostById(@PathVariable Long id){
		postService.deletePostById(id);
		return new ResponseEntity<>("Post with ID: "+id+" gets Deleted sucessfully", HttpStatus.OK);
	}
	
	
	
	
}
