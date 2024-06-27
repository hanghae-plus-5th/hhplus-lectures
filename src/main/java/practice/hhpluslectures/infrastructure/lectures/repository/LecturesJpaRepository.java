package practice.hhpluslectures.infrastructure.lectures.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import practice.hhpluslectures.infrastructure.lectures.entity.LecturesEntity;

@Repository
public interface LecturesJpaRepository extends JpaRepository<LecturesEntity, Long> {

}
