package com.formacionbdi.springboot.app.productos.models.service;

import java.util.List;

import com.formacionbdi.springboot.app.productos.models.entity.Producto;

public interface IProductoService {
	//implementamos metodos
	public List<Producto> findAll();
	public Producto findByID(Long id);
	
	//Metodos para el CRUD
	public Producto save(Producto producto);
	public void deleteById(Long id);
}
