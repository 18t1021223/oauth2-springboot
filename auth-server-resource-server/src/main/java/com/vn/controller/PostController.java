package com.vn.controller;

import com.vn.entity.Post;
import com.vn.service.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostRepo postRepo;

    @GetMapping()
    public List<Post> getAllPost() {
        return postRepo.findAll();
    }

    @GetMapping("/{id}")
    public Post getPost(@PathVariable int id) {
        Optional<Post> postOptional = postRepo.findById(id);
        return postOptional.orElse(null);
    }

}
