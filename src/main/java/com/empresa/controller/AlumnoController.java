package com.empresa.controller;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.entity.Alumno;
import com.empresa.service.AlumnoService;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@RestController
@RequestMapping("/rest/alumno")
public class AlumnoController {
	
	@Autowired
	private AlumnoService service;
	
	@GetMapping
	public ResponseEntity<List<Alumno>> lista() {
		System.out.println(">>> inicio >>> lista");
		List<Alumno> listAlumno = service.listaAlumno();
		return ResponseEntity.ok(listAlumno);
	}
	
	@GetMapping("/{dni}")
	public ResponseEntity<List<Alumno>> busca(@PathVariable("dni") String dni) {
		System.out.println(">>> inicio >>> buscar " + dni);
		List<Alumno> lstAlumno = service.listaPorDni(dni);
		
		if(lstAlumno.size() > 0) {
			return ResponseEntity.ok(lstAlumno);
		} else {
			System.out.println(">>> No existe el alumno con dni" + dni);
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<Alumno> registra(@RequestBody Alumno obj) {
		System.out.println(">>> inicio >>> registra " + obj.getNombre());
		Alumno objAlumno = service.insertaActualizaAlumno(obj);
		return ResponseEntity.ok(objAlumno);
	}
	
	@PutMapping
	public ResponseEntity<Alumno> actualiza(@RequestBody Alumno obj) {
		System.out.println(">>> inicio >>> actualiza " + obj.getIdAlumno());
		
		Optional<Alumno> optionAlumno = service.obtienePorId(obj.getIdAlumno());
		
		if(optionAlumno.isPresent()) {
			Alumno objAlumno = service.insertaActualizaAlumno(obj);
			return ResponseEntity.ok(objAlumno);
		} else {
			System.out.println(">>> No existe el alumno " + obj.getIdAlumno());
			return ResponseEntity.badRequest().build();
		}

	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Alumno> elimina(@PathVariable("id") Integer idAlumno) {
		System.out.println(">>> inicio >>> elimina " + idAlumno);
		Optional<Alumno> optionAlumno = service.obtienePorId(idAlumno);
		
		if(optionAlumno.isPresent()) {
			service.eliminaAlumno(idAlumno);
			return ResponseEntity.ok(optionAlumno.get());
		} else {
			System.out.println(">>> No existe el alumno " + idAlumno);
			return ResponseEntity.badRequest().build();
		}
	}
}
