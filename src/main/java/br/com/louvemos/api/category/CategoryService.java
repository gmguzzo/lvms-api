package br.com.louvemos.api.category;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import br.com.louvemos.api.base.ServiceUtils;
import br.com.louvemos.api.base.SortDirectionEnum;
import br.com.louvemos.api.exception.LvmsException;
import java.util.LinkedHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class CategoryService {

    @Autowired
    private CategoryServiceValidator categoryServiceValidator;

    @Autowired
    private CategoryRepository categoryRepository;

    public Category update(Category c) throws LvmsException {
        Category cPersist = load(c.getId(), null);
        categoryServiceValidator.validateCategoryFound(cPersist);

        cPersist.setCategoryName(c.getCategoryName());
        cPersist.setDescription(c.getDescription());

        cPersist.setUpTimestamps();
        categoryServiceValidator.validatePersist(cPersist);

        return categoryRepository.update(cPersist);

    }

    public Category loadOrCreate(Category category) throws LvmsException {
        if (category == null) {
            return null;
        }

        Category cPersist = this.load(category.getId(), category.getCategoryName());

        if (cPersist != null) {
            return category;
        }

        return this.create(category);

    }

    public Category load(Long id, String name) {
        if (id != null) {
            return categoryRepository.loadById(id);
        } else {
            return categoryRepository.loadByName(name);
        }
    }

    public Category create(Category category) throws LvmsException {
        category.setUpTimestamps();

        categoryServiceValidator.validatePersist(category);

        return categoryRepository.save(category);
    }

    public void delete(Long cId) throws LvmsException {
        Category c = categoryRepository.loadById(cId);
        categoryServiceValidator.validateCategoryFound(c);

        categoryRepository.delete(c);
    }

    public List<Category> list(List<Long> idList, String q, Integer firstResult, Integer maxResults, LinkedHashMap<String, SortDirectionEnum> sortMap) throws LvmsException {
        
        LinkedHashMap<String, SortDirectionEnum> sortWithDbKeys = ServiceUtils.convertSortMapToDbKeys(
                sortMap,
                "cat.category_name",
                SortDirectionEnum.asc,
                (apiKey, apiValue) -> {
                    switch (apiKey.toLowerCase()) {
                        default:
                            return "";
                    }
                });
        
        return categoryRepository.list(idList, q, firstResult, maxResults, sortWithDbKeys);
    }

}
