package com.sprinboot.clienteapp.models.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sprinboot.clienteapp.models.entity.Ciudad;

@Repository
public interface CiudadRepository extends CrudRepository<Ciudad, Long> {

}
