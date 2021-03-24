package br.com.louvemos.api.category;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import br.com.louvemos.api.base.BaseRepositoryHibernate;
import br.com.louvemos.api.exception.LvmsException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    public List<Category> list(List<String> nameList) throws LvmsException {

        String queryStrBase = "SELECT cat.* FROM category cat ";

        List<String> filterStrList = new ArrayList();
        if (nameList != null && !nameList.isEmpty()) {
            filterStrList.add("(cat.category_name in (:nameList))");
        }

        // Build final query string
        StringBuilder queryStrBuilder = new StringBuilder(queryStrBase);

        if (!filterStrList.isEmpty()) {
            queryStrBuilder.append(" WHERE ");
            queryStrBuilder.append(String.join("\n AND ", filterStrList));
            queryStrBuilder.append("\n");
        }

        queryStrBuilder.append(" ORDER BY cat.category_name");

        // Build query
        Query query = getCurrentSession().createNativeQuery(
                queryStrBuilder.toString())
                .addEntity(Category.class);

        if (nameList != null && !nameList.isEmpty()) {
            query.setParameter("nameList", nameList);
        }

        return query.list();
    }

}
