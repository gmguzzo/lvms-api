package br.com.louvemos.api.user;

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
public class PersonRepository extends BaseRepositoryHibernate<Person> {

    @Override
    protected Class<Person> getEntityClass() {
        return Person.class;
    }

    public Person loadByUsername(String username) {
        String queryStr = "SELECT * from person where username = :username";
        Query query = getCurrentSession().createNativeQuery(queryStr).addEntity(Person.class);

        query.setParameter("username", username);
        List<Person> list = query.list();

        if (list == null || list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }
}
