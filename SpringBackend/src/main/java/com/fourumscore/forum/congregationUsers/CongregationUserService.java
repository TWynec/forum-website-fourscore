package com.fourumscore.forum.congregationUsers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CongregationUserService {

    @Autowired
    private CongregationUserRepo congregationUserRepo;

    public int getCongregationUserPosts(String congregation, String user) {
        Optional<CongregationUser> congregationUser = congregationUserRepo.findById(new CongregationUserId(congregation, user));
        return congregationUser.map(CongregationUser::getPosts).orElse(0);
    }

    public void addCongregationUser(String congregation, String user) {
        congregationUserRepo.save(new CongregationUser(new CongregationUserId(congregation, user), getCongregationUserPosts(congregation, user) + 1));
    }

    public void deleteCongregationUser(String congregation, String user) {
        CongregationUserId congregationUserId = new CongregationUserId(congregation, user);
        int posts = getCongregationUserPosts(congregation, user) - 1;
        if (posts > 0) congregationUserRepo.save(new CongregationUser(congregationUserId, posts));
        else congregationUserRepo.deleteById(congregationUserId);
    }
}
