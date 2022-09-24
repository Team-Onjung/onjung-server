package com.onjung.onjung.feed.repository;

import com.onjung.onjung.feed.domain.ClientFeed;
import com.onjung.onjung.feed.domain.QClientFeed;
import com.onjung.onjung.feed.domain.status.ItemStatus;
import com.onjung.onjung.item.repository.CategoryRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class QueryRepository {

    @Autowired
    private JPAQueryFactory queryFactory;

    public void JPAQueryFactory(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    QClientFeed clientFeed = QClientFeed.clientFeed;

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<ClientFeed> getclientFeedList(String price, String created, String category, String status) {

        List<ClientFeed> res;
        BooleanBuilder builder=new BooleanBuilder();
        if(status!=null){
            builder.and(clientFeed.status.eq(ItemStatus.valueOf(status)));
        }
        if(category!=null){
            builder.and(clientFeed.category.eq(categoryRepository.findById(Long.parseLong(category)).get()));
        }

        if(price != null){
            if(price.equals("ASC")){
                res= queryFactory
                        .selectFrom(clientFeed)
                        .where(builder)
                        .orderBy(clientFeed.price.asc())
                        .fetch();
            }else {
                res= queryFactory
                        .selectFrom(clientFeed)
                        .where(builder)
                        .orderBy(clientFeed.price.desc())
                        .fetch();
            }

        }else if(created!=null){
            if(created.equals("ASC")){
                res= queryFactory
                        .selectFrom(clientFeed)
                        .where(builder)
                        .orderBy(clientFeed.createdAt.asc())
                        .fetch();
            }else {
                res= queryFactory
                        .selectFrom(clientFeed)
                        .where(builder)
                        .orderBy(clientFeed.createdAt.desc())
                        .fetch();
            }
        }else {
            res= queryFactory
                    .selectFrom(clientFeed)
                    .where(builder)
                    .fetch();
        }
        System.out.println(res);
        return res;
    }
}
