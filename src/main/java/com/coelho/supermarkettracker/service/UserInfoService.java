package com.coelho.supermarkettracker.service;

import com.coelho.supermarkettracker.domain.UserInfo;
import com.coelho.supermarkettracker.repo.UserInfoRepository;
import org.springframework.stereotype.Repository;
import org.vaadin.crudui.crud.CrudListener;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserInfoService implements CrudListener<UserInfo> {

    private final UserInfoRepository repo;

    public UserInfoService(UserInfoRepository repo) {
        this.repo = repo;
    }

    @Override
    public Collection<UserInfo> findAll() {
        List<UserInfo> result = repo.findAll();
        result = result.stream().sorted(Comparator.comparing(UserInfo::getFullName, String.CASE_INSENSITIVE_ORDER)).collect(Collectors.toList());
        return result;
    }

    @Override
    public UserInfo add(UserInfo userInfo) {
        return repo.insert(userInfo);
    }

    @Override
    public UserInfo update(UserInfo userInfo) {
        return repo.save(userInfo);
    }

    @Override
    public void delete(UserInfo userInfo) {
        repo.delete(userInfo);
    }
}
