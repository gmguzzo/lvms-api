package br.com.louvemos.api.songcategory;

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
public class SongCategoryRepository extends BaseRepositoryHibernate<SongCategory> {

    public SongCategory loadBySongAndCategory(Long songId, Long categoryId) {
        String queryStr = "SELECT * from song_category where song_id = :songId AND category_id = :categoryId";
        Query query = getCurrentSession().createNativeQuery(queryStr).addEntity(SongCategory.class);

        query.setParameter("songId", songId);
        query.setParameter("categoryId", categoryId);
        List<SongCategory> list = query.list();

        if (list == null || list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }

    @Override
    protected Class<SongCategory> getEntityClass() {
        return SongCategory.class;
    }

}
