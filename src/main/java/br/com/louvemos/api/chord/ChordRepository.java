package br.com.louvemos.api.chord;

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
public class ChordRepository extends BaseRepositoryHibernate<Chord> {

    @Override
    protected Class<Chord> getEntityClass() {
        return Chord.class;
    }

    public List<Chord> list(
            String qSymbol,
            List<Long> cIdList,
            List<String> symbolList,
            Integer firstResult,
            Integer maxResults,
            LinkedHashMap<String, SortDirectionEnum> sortMap) throws LvmsException {

        String queryStrBase = "SELECT c.*\n"
                + "FROM chord c \n";

        List<String> filterStrList = new ArrayList();

        if (cIdList != null && !cIdList.isEmpty()) {
            filterStrList.add("(c.id IN (:cIdList))");
        }

        if (symbolList != null && !symbolList.isEmpty()) {
            filterStrList.add("(c.symbol IN (:symbolList))");
        }

        if (!StringUtils.isBlank(qSymbol)) {
            filterStrList.add("(c.symbol ilike (:qSymbol))");
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
                .addEntity(Chord.class);

        if (cIdList != null && !cIdList.isEmpty()) {
            query.setParameterList("cIdList", cIdList);
        }
        if (symbolList != null && !symbolList.isEmpty()) {
            query.setParameterList("symbolList", symbolList);
        }

        if (!StringUtils.isBlank(qSymbol)) {
            query.setParameter("qSymbol", '%' + qSymbol + '%');
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
