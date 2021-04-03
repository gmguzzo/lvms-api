package br.com.louvemos.api.artist;

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
public class ArtistRepository extends BaseRepositoryHibernate<Artist> {

    @Override
    protected Class<Artist> getEntityClass() {
        return Artist.class;
    }

    public Artist loadByName(String name) {
        String queryStr = "SELECT * from artist where artist_name = :name";
        Query query = getCurrentSession().createNativeQuery(queryStr).addEntity(Artist.class);

        query.setParameter("name", name);
        List<Artist> list = query.list();

        if (list == null || list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }

    public List<Artist> list(
            String q, List<Long> aIdList, List<String> names, Boolean isPublic, Integer firstResult, Integer maxResults, LinkedHashMap<String, SortDirectionEnum> sortMap) throws LvmsException {
        String queryStrBase = "SELECT a.*\n"
                + "FROM artist a \n";

        List<String> filterStrList = new ArrayList<>();

        if (aIdList != null && !aIdList.isEmpty()) {
            filterStrList.add("(a.id IN (:aIdList))");
        }

        if (names != null && !names.isEmpty()) {
            filterStrList.add("(a.artist_name IN (:names))");
        }

        if (!StringUtils.isBlank(q)) {
            filterStrList.add("(a.artist_name ilike (:q))");
        }

        if (isPublic != null) {
            filterStrList.add("(a.is_public = (:isPublic))");
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
                .addEntity(Artist.class);

        if (aIdList != null && !aIdList.isEmpty()) {
            query.setParameterList("aIdList", aIdList);
        }
        if (names != null && !names.isEmpty()) {
            query.setParameterList("names", names);
        }

        if (isPublic != null) {
            query.setParameter("isPublic", isPublic);
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
