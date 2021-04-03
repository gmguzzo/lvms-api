package br.com.louvemos.api.album;

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
public class AlbumRepository extends BaseRepositoryHibernate<Album> {

    @Override
    protected Class<Album> getEntityClass() {
        return Album.class;
    }

    public Album loadByName(String name) {
        String queryStr = "SELECT * from album where album_name = :name";
        Query query = getCurrentSession().createNativeQuery(queryStr).addEntity(Album.class);

        query.setParameter("name", name);
        List<Album> list = query.list();

        if (list == null || list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }

    public List<Album> list(
            String q, List<Long> aIdList, List<Long> artistIds, List<String> names, Integer firstResult, Integer maxResults, LinkedHashMap<String, SortDirectionEnum> sortMap) throws LvmsException {
        String queryStrBase = "SELECT a.*\n"
                + "FROM album a \n";

        List<String> filterStrList = new ArrayList<>();

        if (aIdList != null && !aIdList.isEmpty()) {
            filterStrList.add("(a.id IN (:aIdList))");
        }

        if (artistIds != null && !artistIds.isEmpty()) {
            filterStrList.add("(a.artist_id IN (:artistIds))");
        }

        if (names != null && !names.isEmpty()) {
            filterStrList.add("(a.album_name IN (:names))");
        }

        if (!StringUtils.isBlank(q)) {
            filterStrList.add("(a.album_name ilike (:q))");
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
                .addEntity(Album.class);

        if (aIdList != null && !aIdList.isEmpty()) {
            query.setParameterList("aIdList", aIdList);
        }

        if (artistIds != null && !artistIds.isEmpty()) {
            query.setParameterList("artistIds", artistIds);
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
