package com.keepgoi.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.keepgoi.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, String>{

}
