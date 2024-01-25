package com.avegarlabs.coninsuservice.repositories;

import com.avegarlabs.coninsuservice.models.Coninsu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConinsuRepository extends JpaRepository<Coninsu, String> {

}
