package com.think_different.think_different.bucketlist.repository;

import com.think_different.think_different.bucketlist.entity.BucketList;
import com.think_different.think_different.couple.domain.Couple;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BucketListRepository extends JpaRepository<BucketList, Long> {

    List<BucketList> findByCoupleOrderByCreatedAtDesc(Couple couple);

    List<BucketList> findByCoupleAndCompletedFalse(Couple couple);
}
