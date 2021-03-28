/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.externallink;

import br.com.louvemos.api.base.BaseRepositoryHibernate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Andersson
 */
@Repository
@Transactional
public class ExternalLinkRepository extends BaseRepositoryHibernate<ExternalLink> {

    @Override
    protected Class<ExternalLink> getEntityClass() {
        return ExternalLink.class;
    }

}
