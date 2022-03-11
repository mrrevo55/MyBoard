package com.revo.myboard.post;

import lombok.AllArgsConstructor;

import com.revo.myboard.category.CategoryService;
import com.revo.myboard.exception.ArgumentInUseException;
import com.revo.myboard.exception.ObjectNotExistsException;
import com.revo.myboard.group.Authority;
import com.revo.myboard.like.LikeService;
import com.revo.myboard.user.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/*
 * Created By Revo
 */

@Service
@Transactional
@AllArgsConstructor
public class PostService {

  private final PostRepository repository;
  private final UserService userService;
  private final CategoryService categoryService;
  private final LikeService likeService;

  public long createPost(String token, long category_id, String title, String content) {
    if (repository.existsByTitle(title)) {
      throw new ArgumentInUseException(title);
    }
    userService.currentLoggedUser(token).setLastActivityDate(new Date(System.currentTimeMillis()));
    return repository.save(Post
        .builder()
        .author(userService.currentLoggedUser(token))
        .category(categoryService.getCategoryById(category_id))
        .content(content)
        .date(new Date(System.currentTimeMillis()))
        .lastActiveDate(new Date(System.currentTimeMillis()))
        .lastEditDate(new Date(System.currentTimeMillis()))
        .title(title)
        .build()).getId();
  }

  public List<Post> searchPostsByContent(String content) {
    return repository.findAll().stream()
        .filter(post -> post.getTitle().toLowerCase().contains(content.toLowerCase()))
        .toList();
  }

  public Post getPostById(long id) {
    return repository.getById(id);
  }

  public void editPostById(String token, long id, String title, String content) {
    var user = userService.currentLoggedUser(token);
    if (user.getGroup().getAuthority().equals(Authority.USER) && !user
        .getPosts()
        .contains(getPostById(id))) {
      throw new ObjectNotExistsException(String.valueOf(id));
    }
    var post = getPostById(id);
    post.setContent(content);
    post.setTitle(title);
    post.setLastEditDate(new Date(System.currentTimeMillis()));
  }

  public void giveLikeForPostById(String token, long id) {
    likeService.giveForPostById(token, id);
  }

  public void removeLikeFromPostById(String token, long id) {
    likeService.removeFromPostById(token, id);
  }

  public void deletePostById(String token, long id) {
    var user = userService.currentLoggedUser(token);
    if (user.getGroup().getAuthority().equals(Authority.USER)) {
      if (user.getPosts().stream().filter(post -> post.getId() == id).toList().isEmpty()) {
        throw new ObjectNotExistsException(String.valueOf(id));
      }
    }
    repository.delete(getPostById(id));
  }

  public List<Post> getTenLastActivitedPosts() {
    return repository.findByOrderByLastActiveDateDesc(PageRequest.of(0, 10));
  }

  public List<Post> getTenMostLikedPosts() {
    return repository.findByOrderByMyLikesDesc(PageRequest.of(0, 10));
  }

}
