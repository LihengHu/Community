package com.ancientmoon.newcommunity.utils;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
public class SensitiveFilter {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);

    //替换符
    private static final String REPLACEMENT = "***";

    private Trie trie = new Trie();

    @PostConstruct
    public void init(){
        trie.insert("赌博");
        trie.insert("嫖娼");
        trie.insert("吸毒");
    }

    public String filter(String text){
        if(StringUtils.isBlank(text)){
            return null;
        }
        int begin = 0;
        int position = 1;
        StringBuilder sb = new StringBuilder();
        while (position <= text.length()){
            String word = text.substring(begin,position);
            if(trie.search(word)){
                sb.append(REPLACEMENT);
                begin = position;
                position = begin+1;
            }else if(trie.searchPrefix(word))
            {
                position++;
            }
            else{
                sb.append(text.charAt(begin));
                begin++;
                position = begin+1;
            }
        }
        return sb.toString();
    }



}
