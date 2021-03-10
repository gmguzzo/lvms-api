package br.com.louvemos.api.category;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import br.com.louvemos.api.base.BaseRepositoryHibernate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CategoryRepository extends BaseRepositoryHibernate<Category> {

    @Override
    protected Class<Category> getEntityClass() {
        return Category.class;
    }

}
