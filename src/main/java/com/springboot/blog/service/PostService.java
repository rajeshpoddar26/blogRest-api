package com.springboot.blog.service;

import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;

public interface PostService {

	PostDto createPost(PostDto dto);
	
	PostDto updatePost(Long id, PostDto dto);

	PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);

	PostDto getPostById(Long id);
	
	void deletePostById(Long id);

	Post GetingPostById(Long id);

}
