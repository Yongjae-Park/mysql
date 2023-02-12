package com.example.fastcampusmysql.domain.post;

import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.repository.PostRepository;
import com.example.fastcampusmysql.util.PostFixtureFactory;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.stream.IntStream;

@SpringBootTest
public class PostBulkInsertTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    public void bulkInsert() {
        EasyRandom easyRandom = PostFixtureFactory.get(
                3L,
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 2, 1)
        );

        IntStream.range(0, 10)
                .mapToObj(i -> easyRandom.nextObject(Post.class))
                .forEach(x ->
                        System.out.println(x.getMemberId() + "/" + x.getCreatedDate()));
    }
}
