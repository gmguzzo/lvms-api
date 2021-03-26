package br.com.louvemos.api.songsetlist;

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
public class SongSetlistRepository extends BaseRepositoryHibernate<SongSetlist> {

    @Override
    protected Class<SongSetlist> getEntityClass() {
        return SongSetlist.class;
    }

    public SongSetlist loadBySongAndSetlist(Long songId, Long setlistId) {
        String queryStr = "SELECT * from song_setlist where song_id = :songId AND setlist_id = :setlistId";
        Query query = getCurrentSession().createNativeQuery(queryStr).addEntity(SongSetlist.class);

        query.setParameter("songId", songId);
        query.setParameter("setlistId", setlistId);
        List<SongSetlist> list = query.list();

        if (list == null || list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }

}
