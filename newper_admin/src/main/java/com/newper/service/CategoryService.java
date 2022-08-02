package com.newper.service;

import com.newper.entity.Category;
import com.newper.exception.MsgException;
import com.newper.repository.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepo categoryRepo;

    @Transactional
    public void categoryOrder(Integer[] cate_idx){
        //마지막은 드래그앤드랍 중복되는 idx들어감
        for (int i = 0; i < cate_idx.length - 1; i++) {
            Category category = categoryRepo.findById(cate_idx[i]).orElseThrow(()->new MsgException("존재하지 않는 대분류 입니다."));
            //미노출이 음수이므로 order는 0이 아닌 1부터 시작하게끔
            category.updateCategoryOrder(i+1);
        }
    }

    @Transactional
    public void categoryDelete(int CATE_IDX){
        Category category = categoryRepo.findById(CATE_IDX).orElseThrow(()->new MsgException(("존재하지 않는 카테고리입니다.")));
        categoryRepo.delete(category);
    }

}
