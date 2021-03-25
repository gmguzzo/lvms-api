package br.com.louvemos.api.category;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import br.com.louvemos.api.base.BaseRepositoryHibernate;
import br.com.louvemos.api.base.SortDirectionEnum;
import br.com.louvemos.api.base.StringUtils;
import br.com.louvemos.api.exception.LvmsException;
import com.google.common.base.Joiner;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Repository
@Transactional
public class CategoryRepository extends BaseRepositoryHibernate<Category> {

    @Override
    protected Class<Category> getEntityClass() {
        return Category.class;
    }

    public Category loadByName(String name) {
        String queryStr = "SELECT * from category where category_name = :categoryName";
        Query query = getCurrentSession().createNativeQuery(queryStr).addEntity(Category.class);

        query.setParameter("categoryName", name);
        List<Category> list = query.list();

        if (list == null || list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }

    public List<Category> list(List<Long> idList,
            String q,
            Integer firstResult,
            Integer maxResults,
            LinkedHashMap<String, SortDirectionEnum> sortMap) throws LvmsException {

        String queryStrBase = "SELECT cat.* FROM category cat ";

        List<String> filterStrList = new ArrayList();
        if (idList != null && !idList.isEmpty()) {
            filterStrList.add("(cat.id in (:idList))");
        }

        if (!StringUtils.isBlank(q)) {
            filterStrList.add("(cat.category_name ilike (:q))");
        }

        if (sortMap != null && !sortMap.isEmpty()) {
            queryStrBase += "ORDER BY ";
            queryStrBase += Joiner.on(",").withKeyValueSeparator(" ").join(sortMap);
            queryStrBase += "\n";
        }

        if (firstResult != null) {
            queryStrBase += "OFFSET :firstResult\n";
        }
        if (maxResults != null && maxResults != 0) {
            queryStrBase += "LIMIT :maxResults\n";
        }

        // Build final query string
        StringBuilder queryStrBuilder = new StringBuilder(queryStrBase);

        if (!filterStrList.isEmpty()) {
            queryStrBuilder.append(" WHERE ");
            queryStrBuilder.append(String.join("\n AND ", filterStrList));
            queryStrBuilder.append("\n");
        }

        // Build query
        Query query = getCurrentSession().createNativeQuery(
                queryStrBuilder.toString())
                .addEntity(Category.class);

        if (idList != null && !idList.isEmpty()) {
            query.setParameterList("idList", idList);
        }

        if (!StringUtils.isBlank(q)) {
            query.setParameter("q", '%' + q + '%');
        }
        
        //date filter
        if (firstResult != null) {
            query.setParameter("firstResult", firstResult);
        }
        if (maxResults != null && maxResults != 0) {
            query.setParameter("maxResults", maxResults);
        }

        return query.list();
    }

}
