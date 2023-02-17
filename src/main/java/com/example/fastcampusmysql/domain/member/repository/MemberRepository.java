package com.example.fastcampusmysql.domain.member.repository;

import com.example.fastcampusmysql.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    final private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    static final private String TABLE = "member";

    static final  RowMapper<Member> rowMapper = (ResultSet resultSet, int rowNum) -> Member.builder()
            .id(resultSet.getLong("id"))
            .email(resultSet.getString("email"))
            .nickname(resultSet.getString("nickname"))
            .birthday(resultSet.getObject("birthday", LocalDate.class))
            .createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
            .build();

    public Optional<Member> findById(Long id) {
        /*
            select *
            from Member
            where id =:id
         */
        String sql = String.format("SELECT * FROM %s WHERE id = :id", TABLE);
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", id);
        try {
            Member member = namedParameterJdbcTemplate.queryForObject(sql, param, rowMapper);
            return Optional.ofNullable(member);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("해당 id를 가진 회원이 없습니다.");
        }
        return null;
    }

    public List<Member> findAllByIdIn(List<Long> ids) {

        if (ids.isEmpty())
            return List.of();
        /*
            TODO: 반복코드 중복제거
         */
        String sql = String.format("SELECT * FROM %s WHERE id in (:ids)", TABLE);
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("ids", ids);
        return namedParameterJdbcTemplate.query(sql, params, rowMapper);
    }
    public Member save(Member member) {
        /*
            member 의 id를 보고 갱신 또는 삽입을 정함
            반환값은 id를 담아서 반환한다.
         */
        if (member.getId() == null)
            return insert(member);
        return update(member);
    }

    public void saveAll(List<Member> members) {
        String sql = String.format("""
                INSERT INTO %s (nickname, email, birthday, createdAt)
                VALUES (:nickname, :email, :birthday, :createdAt)
                """, TABLE);

        SqlParameterSource[] params = members
                .stream()
                .map(BeanPropertySqlParameterSource::new)
                .toArray(SqlParameterSource[]::new);

        namedParameterJdbcTemplate.batchUpdate(sql, params);
    }

    private Member insert(Member member) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName("Member")
                .usingGeneratedKeyColumns("id");
        SqlParameterSource params = new BeanPropertySqlParameterSource(member);
        long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return Member
                .builder()
                .id(id)
                .email(member.getEmail())
                .nickname(member.getNickname())
                .birthday(member.getBirthday())
                .createdAt(member.getCreatedAt())
                .build();
    }

    private Member update(Member member) {
        String sql = String.format("UPDATE %s set email= :email, nickname= :nickname, birthday= :birthday WHERE id= :id"
                ,TABLE);
        SqlParameterSource params = new BeanPropertySqlParameterSource(member);
        namedParameterJdbcTemplate.update(sql, params);
        return member;
    }

    public List<Member> findAll() {
        String sql = String.format("""
                SELECT *
                FROM %s
                """, TABLE);

        return namedParameterJdbcTemplate.query(sql, rowMapper);
    }
}
