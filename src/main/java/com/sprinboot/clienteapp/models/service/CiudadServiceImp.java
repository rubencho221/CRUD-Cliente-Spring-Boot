package com.sprinboot.clienteapp.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprinboot.clienteapp.models.entity.Ciudad;
import com.sprinboot.clienteapp.models.repository.CiudadRepository;

@Service
public class CiudadServiceImp implements ICiudadService {

	@Autowired
	private CiudadRepository ciudadRepository;
	
	@Override
	public List<Ciudad> listaCiudades() {
		return (List<Ciudad>) ciudadRepository.findAll();
	}

}
