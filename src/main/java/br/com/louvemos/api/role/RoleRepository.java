package br.com.louvemos.api.role;

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
public class RoleRepository extends BaseRepositoryHibernate<Role> {

    @Override
    protected Class<Role> getEntityClass() {
        return Role.class;
    }

    public Role loadByName(String name) {
        String queryStr = "SELECT * from role where name = :name";
        Query query = getCurrentSession().createNativeQuery(queryStr).addEntity(Role.class);

        query.setParameter("name", name);
        List<Role> list = query.list();

        if (list == null || list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }
}
