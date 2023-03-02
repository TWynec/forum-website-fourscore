package com.fourumscore.forum.congregationUsers;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CongregationUserRepo extends CrudRepository<CongregationUser, CongregationUserId> {
}