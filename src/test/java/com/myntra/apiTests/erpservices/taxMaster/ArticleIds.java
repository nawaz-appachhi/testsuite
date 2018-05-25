package com.myntra.apiTests.erpservices.taxMaster;

import java.util.Arrays;
import java.util.List;

/**
 * Created by 16553 on 26/10/16.
 */
 abstract class ArticleIds {
     abstract List<Long> getArticleIds();
}

class khaadiArticleIds extends ArticleIds{
    List<Long> getArticleIds(){
        return Arrays.asList(79L,83L,85L,89L,251L);
    }
}
