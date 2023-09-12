package imb.pr3.estetica.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import imb.pr3.estetica.service.IProvinciaService;
import imb.pr3.estetica.entity.Provincia;

@RestController
@RequestMapping("/api/v1")
public class ProvinciaController {

	@Autowired
	IProvinciaService service;
	
	@GetMapping("/provincia")
	public ResponseEntity<APIResponse<List<Provincia>>>obtenerTodasLasProvincias(){
		APIResponse<List<Provincia>> response = new APIResponse<List<Provincia>> (200, null, service.buscarTodos());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/provincia/{id}")
	public ResponseEntity<APIResponse<Provincia>> obtenerProvinciaPorId(@PathVariable("id") Integer id) {
		Provincia provincia = service.buscarPorId(id);
		if(provincia == null){
			List<String> messages = new ArrayList<>();
			messages.add("No se encontró la Provincia con id = " + id.toString());
			messages.add("Revise el parámetro");
			APIResponse<Provincia> response = new APIResponse<Provincia>(HttpStatus.BAD_REQUEST.value(), messages, null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}else {
			APIResponse<Provincia> response = new APIResponse<Provincia>(HttpStatus.OK.value(),null,provincia);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
	}
	
	@PostMapping("/provincia")
	public ResponseEntity<APIResponse<Provincia>> crearProvincia(@RequestBody Provincia provincia) {
		if(provincia.getId() != null) {
			Provincia buscaProvincia = service.buscarPorId(provincia.getId());
			if (buscaProvincia != null) {
				List<String> messages = new ArrayList<>();
				messages.add("Ya existe una provincia con el ID = " + provincia.getId().toString());
				messages.add("Para actualizar utilice el verbo PUT");
				APIResponse<Provincia> response = new APIResponse<Provincia>(HttpStatus.BAD_REQUEST.value(), messages, null);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
		}
		service.guardar(provincia);
		APIResponse<Provincia> response = new APIResponse<Provincia>(HttpStatus.CREATED.value(),null,provincia);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
		
	}
	
	@PutMapping("/provincia")
	public ResponseEntity<APIResponse<Provincia>> modificarProvincia(@RequestBody Provincia provincia) {
		boolean isError;
		String idStr;
		if(provincia.getId() != null) {
			Provincia buscaProvincia = service.buscarPorId(provincia.getId());
			idStr = provincia.getId().toString();
			if (buscaProvincia != null) {
				//Devolver un OK
				isError = false;
			}else {
				//Devolver un Error
				isError = true;
			}
			
		}else {
			idStr = "<No definido>";
			//Devolver un error
			isError = true;
		}
		
		if(isError) {
			//Devolvemos el error
			List<String> messages = new ArrayList<>();
			messages.add("No existe una provincia con el ID = " + idStr);
			messages.add("Para crear una nueva utilice el verbo POST");
			APIResponse<Provincia> response = new APIResponse<Provincia>(HttpStatus.BAD_REQUEST.value(), messages, null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}else {
			//Devolvemos el OK
			service.guardar(provincia);
			APIResponse<Provincia> response = new APIResponse<Provincia>(HttpStatus.OK.value(),null,provincia);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		
	}
	
	@DeleteMapping("/provincia/{id}")
	public ResponseEntity<APIResponse<Provincia>> eliminarProvincia(@PathVariable("id") Integer id) {
		Provincia buscaProvincia = service.buscarPorId(id);
		if(buscaProvincia == null) {
			//Error
			List<String> messages = new ArrayList<>();
			messages.add("No existe una provincia con el ID = " + id.toString());
			APIResponse<Provincia> response = new APIResponse<Provincia>(HttpStatus.BAD_REQUEST.value(),messages,null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}else {
			service.eliminar(id);
			List<String> messages = new ArrayList<>();
			messages.add("La provincia que figura en el cuerpo ha sido eliminada");
			APIResponse<Provincia> response = new APIResponse<Provincia>(HttpStatus.OK.value(),messages,buscaProvincia);
			return ResponseEntity.status(HttpStatus.OK).body(response);
			//eliminar
		}
		
	}
}
