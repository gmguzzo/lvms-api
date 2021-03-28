package br.com.louvemos.api.song;

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
public class SongRepository extends BaseRepositoryHibernate<Song> {

    @Override
    protected Class<Song> getEntityClass() {
        return Song.class;
    }

    public List<Song> list(
            List<Long> ids,
            List<Long> albumIds,
            List<Long> artistIds,
            String q,
            List<String> categoryList,
            Integer firstResult,
            Integer maxResults,
            LinkedHashMap<String, SortDirectionEnum> sortMap) throws LvmsException {

        String queryStrBase = "SELECT s.*\n"
                + "FROM song s \n"
                + "JOIN album a ON s.album_id = a.id \n"
                + "JOIN artist ar ON a.artist_id = ar.id\n";

        if (categoryList != null && !categoryList.isEmpty()) {
            queryStrBase += "JOIN (SELECT DISTINCT sc.song_id as sId\n"
                    + "		FROM song_category sc\n"
                    + "		LEFT JOIN category c ON c.id = sc.category_id\n"
                    + "		WHERE c.category_name IN (:categoryList)) categories ON categories.sId = s.id\n";
        }

        List<String> filterStrList = new ArrayList();

        if (ids != null && !ids.isEmpty()) {
            filterStrList.add("(s.id IN (:ids))");
        }

        if (albumIds != null && !albumIds.isEmpty()) {
            filterStrList.add("(s.album_id IN (:albumIds))");
        }

        if (artistIds != null && !artistIds.isEmpty()) {
            filterStrList.add("(ar.id IN (:artistIds))");
        }

        if (!StringUtils.isBlank(q)) {
            filterStrList
                    .add("(s.title ilike (:q)\n"
                            + "OR s.lyric ilike (:q)\n"
                            + "OR a.album_name ilike (:q)\n"
                            + "OR ar.artist_name ilike (:q))");
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
                .addEntity(Song.class);

        if (ids != null && !ids.isEmpty()) {
            query.setParameterList("ids", ids);
        }
        if (albumIds != null && !albumIds.isEmpty()) {
            query.setParameterList("albumIds", albumIds);
        }
        if (artistIds != null && !artistIds.isEmpty()) {
            query.setParameterList("artistIds", artistIds);
        }
        if (categoryList != null && !categoryList.isEmpty()) {
            query.setParameterList("categoryList", categoryList);
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
