package com.ancientmoon.newcommunity.utils;

import org.apache.commons.lang3.CharUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


public class Trie {
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
                node.children.put(ch,new Trie());
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
