package com.trendyol.svc.shopping.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class Category {
    private String title;
    private String parentCategoryId;

    public Category(String title) {
        this.title = title;
    }

    public void setParentCategoryId(String parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        if (getParentCategoryId()== null || category.getParentCategoryId()==null){
            return Objects.equals(getTitle(), category.getTitle());
        }
        return Objects.equals(getTitle(), category.getTitle()) &&
                Objects.equals(getParentCategoryId(), category.getParentCategoryId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getParentCategoryId());
    }
}
