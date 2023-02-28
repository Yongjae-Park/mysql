package com.example.fastcampusmysql.domain.post.repository;

import com.example.fastcampusmysql.domain.post.entity.PostLike;
import com.example.fastcampusmysql.domain.post.entity.PostLike;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostLikeRepository {
    final private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    final static String TABLE = "PostLike";

    final static private RowMapper<PostLike> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> PostLike.builder()
            .id(resultSet.getLong("id"))
            .memberId(resultSet.getLong("memberId"))
            .postId(resultSet.getLong("postId"))
            .createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
            .build();

    public PostLike save(PostLike PostLike) {
        if (PostLike.getId() == null)
            return insert(PostLike);
        throw new UnsupportedOperationException("Post는 갱신을 지원하지 않습니다.");
    }

    private PostLike insert(PostLike PostLike) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE)
                .usingGeneratedKeyColumns("id");

        SqlParameterSource params = new BeanPropertySqlParameterSource(PostLike);
        Long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();

        return PostLike.builder()
                .id(id)
                .memberId(PostLike.getMemberId())
                .createdAt(PostLike.getCreatedAt())
                .build();
    }

    public Long count(Long postId) {
        String sql = String.format("""
                SELECT count(id)
                FROM %s
                WHERE postId = :postId
                """, TABLE);
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("postId", postId);

        return namedParameterJdbcTemplate.queryForObject(sql, params, Long.class);
    }

}

