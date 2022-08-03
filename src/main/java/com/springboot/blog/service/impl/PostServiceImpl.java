package com.springboot.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;




@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public PostDto createPost(PostDto dto) {
		// save DTO to entity
		Post post = mapTOEntity(dto);
		postRepository.save(post);
		// entity to DTO
		return (mapToDto(post));
	}

	@Override
	public PostDto updatePost(Long id, PostDto dto) {
		Post post = GetingPostById(id);
		post.setContent(dto.getContent());
		post.setDescription(dto.getContent());
		post.setTitle(dto.getTitle());
		postRepository.save(post);
		return mapToDto(post);
	}

	@Override
	public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);

        // get content for page object
        List<Post> listOfPosts = posts.getContent();

        List<PostDto> content= listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElemens(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());
		
		
		
		return postResponse;

	}

	@Override
	public PostDto getPostById(Long id) {
		Post post = GetingPostById(id);
		return mapToDto(post);
	}

	@Override
	public void deletePostById(Long id) {
		Post post = GetingPostById(id);
		postRepository.delete(post);
	}

//	Post by Id
	@Override
	public Post GetingPostById(Long id) {
		return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
	}

//	Entity into DTO
	private PostDto mapToDto(Post post) {
//		PostDto dto = new PostDto();
//		dto.setId(post.getId());
//		dto.setTitle(post.getTitle());
//		dto.setContent(post.getContent());
//		dto.setDescription(post.getDescription());

		PostDto dto = mapper.map(post, PostDto.class);
		return dto;
	}

//	DTO to Entity
	private Post mapTOEntity(PostDto postDto) {
//		Post post = new Post();
//		post.setTitle(postDto.getTitle());
//		post.setDescription(postDto.getDescription());
//		post.setContent(postDto.getContent());

		Post post=mapper.map(postDto, Post.class);
		return post;
	}
}
