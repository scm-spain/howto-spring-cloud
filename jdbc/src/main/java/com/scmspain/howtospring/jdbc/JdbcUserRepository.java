package com.scmspain.howtospring.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import rx.Observable;

import javax.inject.Inject;

public class JdbcUserRepository implements UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcUserRepository.class);
    private final JdbcTemplate jdbcTemplate;

    @Inject
    public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(User user) {
        jdbcTemplate.update(
                "INSERT INTO users (id, name, lastname) values (?, ?, ?)",
                user.getId(),
                user.getName(),
                user.getLastname()
        );
    }

    @Override
    public Observable<User> findAll() {
        return Observable.create(subscriber -> {
            if (!subscriber.isUnsubscribed()) {
                jdbcTemplate
                        .query(
                                "SELECT id, name, lastname FROM users",
                                (rs, rowNum) -> new User(rs.getString("id"), rs.getString("name"), rs.getString("lastname"))
                        )
                        .forEach(user -> subscriber.onNext(user));
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<User> findById(String userId) {
        return Observable.create(subscriber -> {
            if (!subscriber.isUnsubscribed()) {
                User user = jdbcTemplate
                        .queryForObject(
                                "SELECT id, name, lastname FROM users WHERE id = ?",
                                (rs, rowNum) -> new User(rs.getString("id"), rs.getString("name"), rs.getString("lastname")),
                                userId
                        );
                subscriber.onNext(user);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<User> findByName(String name) {
        return Observable.create(subscriber -> {
            if (!subscriber.isUnsubscribed()) {
                jdbcTemplate
                        .query(
                                "SELECT id, name, lastname FROM users WHERE name = ?",
                                (rs, rowNum) -> new User(rs.getString("id"), rs.getString("name"), rs.getString("lastname")),
                                name
                        )
                        .forEach(user -> subscriber.onNext(user));
                subscriber.onCompleted();
            }
        });
    }
}
