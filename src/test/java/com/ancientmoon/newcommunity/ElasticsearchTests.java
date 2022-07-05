package com.ancientmoon.newcommunity;

import com.ancientmoon.newcommunity.dao.elasticsearch.DiscussPostRepository;
import com.ancientmoon.newcommunity.dao.mapper.DiscussPostMapper;
import com.ancientmoon.newcommunity.entity.DiscussPost;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.List;
import java.util.Map;


@SpringBootTest
public class ElasticsearchTests {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchTemplate;

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private DiscussPostRepository discussPostRepository;

    @Test
    public void testInsert(){
        discussPostRepository.save(discussPostMapper.selectDiscussPostById(241));
        discussPostRepository.save(discussPostMapper.selectDiscussPostById(242));
        discussPostRepository.save(discussPostMapper.selectDiscussPostById(243));
    }

    @Test
    public void testInsertList(){
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(101,0,100,0));
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(102,0,100,0));
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(103,0,100,0));
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(111,0,100,0));
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(113,0,100,0));
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(131,0,100,0));
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(132,0,100,0));
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(133,0,100,0));
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(134,0,100,0));
    }

    @Test
    public void testSearchByRepository(){
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery("互联网寒冬", "title", "content"))
                .withSort(SortBuilders.fieldSort("type").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                .withPageable(PageRequest.of(0, 10))
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
            if(highlightFields.get("title") != null){
                post.setTitle(highlightFields.get("title").get(0));
            }
            if(highlightFields.get("content") != null){
                post.setContent(highlightFields.get("content").get(0));
            }
        }
        SearchPage<DiscussPost> searchPages = SearchHitSupport.searchPageFor(hits, searchQuery.getPageable());
        Page<DiscussPost> page = (Page)SearchHitSupport.unwrapSearchHits(searchPages);
        for (DiscussPost post:
                page) {
            System.out.println(post);
        }
    }
}
