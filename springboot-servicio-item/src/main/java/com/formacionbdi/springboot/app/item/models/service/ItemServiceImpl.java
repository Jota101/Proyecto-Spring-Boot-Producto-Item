package com.formacionbdi.springboot.app.item.models.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.formacionbdi.springboot.app.item.models.Item;
import com.formacionbdi.springboot.app.item.models.Producto;

@Service("restTempService")
public class ItemServiceImpl implements ItemService {

	@Autowired
	private RestTemplate clienteRest;
	
	@Override
	public List<Item> findAll() {
		List<Producto> productos = Arrays.asList(clienteRest.getForObject("http://localhost:8001/listar",Producto[].class));
		
		return productos.stream().map(prod-> new Item(prod,1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer cantidad) {
		HashMap<String,String>pathVariables = new HashMap<>();
		pathVariables.put("id", id.toString());
		Producto producto = clienteRest.getForObject("http://localhost:8001/ver/{id}", Producto.class, pathVariables);
		return new Item (producto,cantidad);
	}

	@Override
	public Producto save(Producto producto) {
		HttpEntity<Producto> body = new HttpEntity<>(producto);
		ResponseEntity<Producto> response = clienteRest.exchange(
				"http://springboot-servicio-productos/crear",
				HttpMethod.POST,
				body,Producto.class);
		Producto productoResponse = response.getBody();
		return productoResponse;
	}

	@Override
	public Producto update(Producto producto, Long id) {
		HashMap<String,String>pathVariables = new HashMap<>();
		pathVariables.put("id", id.toString());
		HttpEntity<Producto> body = new HttpEntity<>(producto);
		ResponseEntity<Producto> response = clienteRest.exchange("http://springboot-servicio-productos/editar/{id}", HttpMethod.PUT,body,Producto.class,pathVariables);
		Producto prodDb = response.getBody();
		return prodDb;
	}

	@Override
	public void deleteById(Long id) {
		Map<String,String> pathVariables = new HashMap<>();
		pathVariables.put("id",id.toString());
		clienteRest.delete("http://springboot-servicio-productos/editar/{id}", pathVariables);
	}

}
