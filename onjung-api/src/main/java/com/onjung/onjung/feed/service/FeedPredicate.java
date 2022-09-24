package com.onjung.onjung.feed.service;

import com.onjung.onjung.feed.domain.ClientFeed;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public class FeedPredicate {
    public static Predicate search(String category){


        BooleanBuilder builder = new BooleanBuilder();

        if(category != null){
//            builder.and(clientFeed.category.eq(category));
        }

        return builder;
    }

}
