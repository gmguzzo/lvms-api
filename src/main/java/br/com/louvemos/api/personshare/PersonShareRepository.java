package br.com.louvemos.api.personshare;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import br.com.louvemos.api.base.BaseRepositoryHibernate;
import br.com.louvemos.api.base.SortDirectionEnum;
import br.com.louvemos.api.base.StringUtils;
import br.com.louvemos.api.exception.LvmsException;
import br.com.louvemos.api.person.Person;
import com.google.common.base.Joiner;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Repository
@Transactional
public class PersonShareRepository extends BaseRepositoryHibernate<PersonShare> {

    @Override
    protected Class<PersonShare> getEntityClass() {
        return PersonShare.class;
    }

    public PersonShare loadBySubjectAndTarget(PersonShareSubjectTypeEnum subjectType,
                                              Long subjectId,
                                              Long pTargetId) {
        String queryStr =
                "SELECT * from person_share\n"
                        + "WHERE subject_type = :subjectType\n"
                        + "AND subject = :subjectId\n"
                        + "AND target_person_id = :pTargetId";
        Query query = getCurrentSession().createNativeQuery(queryStr).addEntity(Person.class);

        query.setParameter("subject_type", subjectType.toString());
        query.setParameter("subject_id", subjectId);
        query.setParameter("target_person_id", pTargetId);
        List<PersonShare> list = query.list();

        if (list == null || list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }

    public List<Person> list(
            String q,
            List<Long> pIdList,
            List<String> firstNames,
            List<String> lastNames,
            List<String> emails,
            Integer firstResult,
            Integer maxResults,
            LinkedHashMap<String, SortDirectionEnum> sortMap) throws LvmsException {
        String queryStrBase = "SELECT p.*\n"
                + "FROM person p \n";

        List<String> filterStrList = new ArrayList<>();

        if (pIdList != null && !pIdList.isEmpty()) {
            filterStrList.add("(p.id IN (:pIdList))");
        }

        if (firstNames != null && !firstNames.isEmpty()) {
            filterStrList.add("(p.first_name IN (:firstNames))");
        }

        if (lastNames != null && !lastNames.isEmpty()) {
            filterStrList.add("(p.last_name IN (:lastNames))");
        }

        if (emails != null && !emails.isEmpty()) {
            filterStrList.add("(p.last_name IN (:emails))");
        }

        if (!StringUtils.isBlank(q)) {
            filterStrList.add("(p.first_name ilike (:q)\n"
                    + "OR p.last_name ilike (:q)\n"
                    + "OR p.email ilike (:q))");
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
                .addEntity(Person.class);

        if (pIdList != null && !pIdList.isEmpty()) {
            query.setParameterList("pIdList", pIdList);
        }
        if (firstNames != null && !firstNames.isEmpty()) {
            query.setParameterList("firstNames", firstNames);
        }
        if (lastNames != null && !lastNames.isEmpty()) {
            query.setParameterList("lastNames", lastNames);
        }
        if (emails != null && !emails.isEmpty()) {
            query.setParameterList("emails", emails);
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
