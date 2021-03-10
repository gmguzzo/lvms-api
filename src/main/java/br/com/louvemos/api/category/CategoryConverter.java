package br.com.louvemos.api.category;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {

    public Category toModel(Long id, CategoryDTO cd) {
        if (cd == null) {
            if (id == null) {
                return null;
            }
            return new Category(id);
        }

        Category c = new Category();

        c.setId(id);
        c.setCategoryName(cd.getCategoryName());
        c.setDescription(cd.getDescription());

        return c;
    }

    public CategoryDTO toDTO(Category c) {
        if (c == null) {
            return null;
        }

        CategoryDTO cd = new CategoryDTO();
        cd.setId(c.getId());
        cd.setCategoryName(c.getCategoryName());
        cd.setDescription(c.getDescription());

        return cd;
    }

}
