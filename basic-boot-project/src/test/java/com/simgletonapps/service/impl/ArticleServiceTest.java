package com.simgletonapps.service.impl;

import com.simgletonapps.model.Article;
import com.simgletonapps.repository.ArticleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class ArticleServiceTest {

    @InjectMocks
    private ArticleService articleService;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private List<Article> articles;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        articles = Arrays.asList(
                new Article("art-1", "a1"),
                new Article("art-2", "a2"),
                new Article("art-3", "a3")
        );

    }

    @Test
    public void getAllArticles() throws Exception {

        Mockito.when(articleRepository.findAll()).thenReturn(articles);

        List<Article> art = articleService.getAllArticles();

        assertNotNull("Is not null", art);
        assertSame("","art-1",art.get(0).getTitle());

        verify(articleRepository).findAll();
        verifyZeroInteractions(articleRepository);

    }

}