package com.royasoft.sailevent.impl.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.royasoft.sailevent.impl.entity.SailDefaultClub;

public interface SailDefaultClubDao extends JpaRepository<SailDefaultClub, Integer> {

    @Query(value = "select clubId from SailDefaultClub where areaName = ?1")
    public List<Object> getClubIdByAreaName(String areaName);

    @Query(value = "select count(1) from SailDefaultClub where areaName = ?1 and clubId = ?2")
    public int getClubIdByClubIdAndAreaName(String areaName, int clubId);

}