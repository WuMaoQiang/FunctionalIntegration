package com.cc.retrofitdemo.threadpool;

import com.cc.retrofitdemo.network.bean.ArticleListBean;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class ThreadPoolManagerTest {

    @Test
    public void doSubmit() {
        String word = "mocked";
        ArticleListBean apple = Mockito.mock(ArticleListBean.class);
        Mockito.when(apple.harvest(Mockito.anyString())).thenReturn(word);
        Assert.assertEquals(apple.harvest("anyString"), word);
    }
}