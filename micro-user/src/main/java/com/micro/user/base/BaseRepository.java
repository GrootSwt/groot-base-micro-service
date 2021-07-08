package com.micro.user.base;

import com.querydsl.core.types.dsl.PathBuilderFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.Querydsl;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

public abstract class BaseRepository {

    @Resource
    protected EntityManager entityManager;


    protected abstract Class<?> getModelClass();

    /**
     * 获取Querydsl
     * @return  Querydsl
     */
    protected Querydsl querydsl() {
        return new Querydsl(entityManager, new PathBuilderFactory().create(getModelClass()));
    }

    /**
     * 获取JPAQueryFactory
     * @return  JPAQueryFactory
     */
    protected JPAQueryFactory queryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}
