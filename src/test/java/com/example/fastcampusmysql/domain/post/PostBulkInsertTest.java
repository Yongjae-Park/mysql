package com.example.fastcampusmysql.domain.post;

import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.repository.PostRepository;
import com.example.fastcampusmysql.util.PostFixtureFactory;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
@Transactional
public class PostBulkInsertTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    @Disabled
    @Rollback(false)
    public void bulkInsert() {
        EasyRandom easyRandom = PostFixtureFactory.get(
                4L,
                LocalDate.of(1970, 1, 1),
                LocalDate.of(2023, 2, 1)
        );

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        int _TEN_THOUSAND = 10000;
        List<Post> posts = IntStream.range(0, _TEN_THOUSAND * 100)
                .parallel()
                .mapToObj(i -> easyRandom.nextObject(Post.class))
                .toList();
        stopWatch.stop();

        System.out.println("까지 생성 시간: " + stopWatch.getTotalTimeSeconds());

        StopWatch queryStopWatch = new StopWatch();
        queryStopWatch.start();

        postRepository.bulkInsert(posts);
        queryStopWatch.stop();
        System.out.println("DB 인서트 시간 : " + queryStopWatch.getTotalTimeSeconds());

    }
}
