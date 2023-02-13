package com.myproject.springboot.backend.apirest.models.service;

import java.util.List;


import com.myproject.springboot.backend.apirest.models.entity.Cliente;

public interface IClienteService {

	public List<Cliente> findAll();
	
	public Cliente findBy(Long id);
	
	public Cliente save(Cliente cliente);
	
	public void deleteById(Long id);
	
	
}
