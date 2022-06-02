package com.ancientmoon.newcommunity.utils;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;


@Component
public class SensitiveFilter {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);

    //替换符
    private static final String REPLACEMENT = "***";

    private final Trie trie = new Trie();

    @PostConstruct
    public void init(){
        trie.insert("赌博");
        trie.insert("嫖娼");
        trie.insert("吸毒");
        trie.insert("黄色");
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


    private static class Trie {
        public Map<Character, Trie> children;
        public boolean isEnd;

        Trie(){
            this.children = new HashMap<>();
            isEnd = false;
        }

        public void insert(String word){
            Trie node = this;
            for(int i = 0 ;i<word.length() ;i++){
                char ch = word.charAt(i);
                if(node.children.get(ch) == null){
                    node.children.put(ch, new Trie());
                }
                node = node.children.get(ch);
            }
            node.isEnd = true;
        }

        public boolean search(String word) {
            Trie node = this;
            for (int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                if(isSymbol(ch)){ continue;}
                if (node.children.get(ch) == null) {
                    return false;
                }
                node = node.children.get(ch);
            }
            return node.isEnd;
        }

        public boolean searchPrefix(String word) {
            Trie node = this;
            for (int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                if(isSymbol(ch)){
                    if(i == 0) return false;
                    continue;
                }
                if (node.children.get(ch) == null) {
                    return false;
                }
                node = node.children.get(ch);
            }
            return true;
        }

        private boolean isSymbol(Character ch){
            return !CharUtils.isAsciiAlphanumeric(ch) && (ch < 0x2E80 || ch> 0x9FFF);
        }
    }

}
