package com.data.volodymyr.notecase.dao;

import android.database.Cursor;

import com.data.volodymyr.notecase.entity.Category;
import com.data.volodymyr.notecase.entity.Product;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created by volodymyr on 31.01.16.
 */
public interface ProductDAO {

    int addProduct(Product product);

    void updateProduct(Product product);

    Product getProductById(int productId);

    List<Product> getAllProducts(Timestamp since, Timestamp till);

    void deleteProductById(int productId);

    List<Product> getProductsByCategoryId(int categoryId);

    Map<Category, Double> getExpensesGroupedByCategories(Timestamp since, Timestamp till);

    Cursor getProductNameCursor();

    Cursor suggestProductName(String partialProductName);

}