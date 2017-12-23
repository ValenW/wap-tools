package com.worksap.repository;

import com.worksap.model.entity.BookStore;
import com.worksap.model.entity.BookStoreWithBooks;

import java.util.List;

/**
 * @author Xiaoyue Xiao
 */
public interface BookStoreRepository {

    BookStore selectBookStoreById(Long id);

    List<BookStore> selectAllBookStores();

    BookStoreWithBooks selectBookStoreWithBooksById(Long id);

}
