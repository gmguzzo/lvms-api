package br.com.louvemos.api.passwordreset;

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
public class SecureTokenRepository extends BaseRepositoryHibernate<SecureToken> {

    @Override
    protected Class<SecureToken> getEntityClass() {
        return SecureToken.class;
    }

    public SecureToken findByToken(String token) {
        String queryStr = "SELECT * from secure_token where token = :token";
        Query query = getCurrentSession().createNativeQuery(queryStr).addEntity(SecureToken.class);

        query.setParameter("token", token);
        List<SecureToken> list = query.list();

        if (list == null || list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }

    public void removeByToken(String token) {
        SecureToken t = this.findByToken(token);
        this.delete(t);
    }

}
