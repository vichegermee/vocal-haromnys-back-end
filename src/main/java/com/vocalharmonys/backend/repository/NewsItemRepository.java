package com.vocalharmonys.backend.repository;

import com.vocalharmonys.backend.entity.NewsItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsItemRepository extends JpaRepository<NewsItem, Long> {

    List<NewsItem> findAllByOrderByIdDesc();
}
