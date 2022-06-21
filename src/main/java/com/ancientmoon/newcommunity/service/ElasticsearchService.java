package com.ancientmoon.newcommunity.service;

import com.ancientmoon.newcommunity.dao.elasticsearch.DiscussPostRepository;
import com.ancientmoon.newcommunity.entity.DiscussPost;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ElasticsearchService {

    @Autowired
    private DiscussPostRepository discussPostRepository;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchTemplate;

    @Autowired
    private ElasticsearchService service;

    public void saveDiscussPost(DiscussPost post){
        discussPostRepository.save(post);
    }

    public void deleteDiscussPost(int id){
        discussPostRepository.deleteById(id);
    }

    public Page<DiscussPost> searchDiscussPost(String keyword,int current ,int limit){
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery(keyword, "title", "content"))
                .withSort(SortBuilders.fieldSort("type").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                .withPageable(PageRequest.of(current, limit))
                .withHighlightFields(
                        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
                        new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
                ).build();

        // elasticTemplate.queryForPage(searchQuery, class, SearchResultMapper)
        // 底层获取得到了高亮显示的值, 但是没有返回.
        SearchHits<DiscussPost> hits =elasticsearchTemplate.search(searchQuery, DiscussPost.class);
        for (SearchHit<DiscussPost> hit : hits) {
            Map<String, List<String>> highlightFields = hit.getHighlightFields();
            DiscussPost post = hit.getContent();
            //处理高亮显示
            if(highlightFields.get("title") != null){
                post.setTitle(highlightFields.get("title").get(0));
            }
            if(highlightFields.get("content") != null){
                post.setContent(highlightFields.get("content").get(0));
            }
        }
        SearchPage<DiscussPost> searchPages = SearchHitSupport.searchPageFor(hits, searchQuery.getPageable());
        Page<DiscussPost> page = (Page) SearchHitSupport.unwrapSearchHits(searchPages);
        return page;
    }

}
