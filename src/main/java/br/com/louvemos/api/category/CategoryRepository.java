package br.com.louvemos.api.category;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import br.com.louvemos.api.base.BaseRepositoryHibernate;
import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

}
