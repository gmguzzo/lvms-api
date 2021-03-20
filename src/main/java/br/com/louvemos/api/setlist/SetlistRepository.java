package br.com.louvemos.api.setlist;

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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class SetlistRepository extends BaseRepositoryHibernate<Setlist> {

    @Override
    protected Class<Setlist> getEntityClass() {
        return Setlist.class;
    }

    public List<Setlist> list(
            List<Long> ids,
            List<Long> pIds,
            String q,
            List<String> names,
            Integer firstResult,
            Integer maxResults,
            LinkedHashMap<String, SortDirectionEnum> sortMap) throws LvmsException {

        String queryStrBase = "SELECT s.*\n"
                + "FROM setlist s \n"
                + "JOIN person p ON s.person_id = p.id\n";

        List<String> filterStrList = new ArrayList();

        if (ids != null && !ids.isEmpty()) {
            filterStrList.add("(s.id IN (:ids))");
        }

        if (pIds != null && !pIds.isEmpty()) {
            filterStrList.add("(p.id IN (:pIds))");
        }

        if (names != null && !names.isEmpty()) {
            filterStrList.add("(s.name IN (:names))");
        }

        if (!StringUtils.isBlank(q)) {
            filterStrList.add("(s.name ilike (:q)\n"
                    + "OR s.description ilike (:q))\n");
        }

        // Build final query string
        StringBuilder queryStrBuilder = new StringBuilder(queryStrBase);

        if (!filterStrList.isEmpty()) {
            queryStrBuilder.append(" WHERE ");
            queryStrBuilder.append(String.join("\n AND ", filterStrList));
            queryStrBuilder.append("\n");
        }

        if (sortMap != null && !sortMap.isEmpty()) {
            queryStrBuilder.append("ORDER BY ");
            queryStrBuilder.append(Joiner.on(",").withKeyValueSeparator(" ").join(sortMap));
            queryStrBuilder.append("\n");
        }

        if (firstResult != null) {
            queryStrBuilder.append("OFFSET :firstResult\n");
        }
        if (maxResults != null && maxResults != 0) {
            queryStrBuilder.append("LIMIT :maxResults\n");
        }

        // Build query
        Query query = getCurrentSession().createNativeQuery(
                queryStrBuilder.toString())
                .addEntity(Setlist.class);

        if (ids != null && !ids.isEmpty()) {
            query.setParameterList("ids", ids);
        }
        if (pIds != null && !pIds.isEmpty()) {
            query.setParameterList("pIds", pIds);
        }
        if (names != null && !names.isEmpty()) {
            query.setParameterList("names", names);
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
