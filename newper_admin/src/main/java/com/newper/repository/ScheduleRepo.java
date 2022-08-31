package com.newper.repository;

import com.newper.entity.Schedule;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepo extends JpaRepository<Schedule, Integer> {

    @EntityGraph(attributePaths = {"company"})
    Schedule findScheduleBysIdx(Integer sIdx);

}