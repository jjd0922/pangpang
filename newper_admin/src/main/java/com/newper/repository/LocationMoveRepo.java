package com.newper.repository;

import com.newper.entity.LocationMove;
import com.newper.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationMoveRepo extends JpaRepository<LocationMove, Long> {

    @EntityGraph(attributePaths = {"location1", "location2", "user"})
    public LocationMove findLocationMoveByLmIdx(long lmIdx);

}
