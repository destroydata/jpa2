package com.example.demo.todos.repository;


import com.example.demo.todos.domain.dto.TodoCondition;
import com.example.demo.todos.domain.entity.Todo;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import java.util.List;
import static com.example.demo.todos.domain.entity.QTodo.todo;
import static com.example.demo.members.domain.entity.QMember.member;

public class CustomTodoRepositoryImpl
        implements CustomTodoRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Todo> findAllByCondition(
            PageRequest request,
            TodoCondition condition
    ){
        JPAQuery<Todo> query = queryFactory
                .select(todo)
                .from(todo)
                .leftJoin(todo.member, member)
                .fetchJoin()
                .where(
                        contentContains(condition.getContent()),
                        titleEq(condition.getTitle()),
                        isDoneEq(condition.getIsDone()),
                        isLikeGoe(condition.getLikeGoe()),
                        isLikeLoe(condition.getLikeLoe())
                )
                .offset(request.getPageNumber())
                .limit(request.getPageSize());
        List<Todo> content = query.fetch();
        Long totalSize = queryFactory
                .select(todo.count())
                .from(todo)
                .where(
                        contentContains(condition.getContent()),
                        titleEq(condition.getTitle()),
                        isDoneEq(condition.getIsDone()),
                        isLikeGoe(condition.getLikeGoe()),
                        isLikeLoe(condition.getLikeLoe())
                )
                .fetchOne();
        return new PageImpl<>(content, request, totalSize);
    }

    private BooleanExpression contentContains(String content) {
           return content == null
                    ? null
            : todo.content.contains(content);
    }

    private BooleanExpression titleEq(String title) {
        return title == null
                ? null
                : todo.title.eq(title);
    }

    private BooleanExpression isDoneEq(Boolean isDone) {
        return isDone == null
                ? null
                : todo.isDone.eq(isDone);
    }

    private BooleanExpression isLikeGoe(Integer likeGoe) {
        return likeGoe == null
                ? null
                : todo.likeCount.goe(likeGoe);
    }

    private BooleanExpression isLikeLoe(Integer likeLoe) {
        return likeLoe == null
                ? null
                : todo.likeCount.loe(likeLoe);
    }

    public CustomTodoRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }
}
