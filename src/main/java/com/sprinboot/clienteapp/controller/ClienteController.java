package com.sprinboot.clienteapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sprinboot.clienteapp.models.entity.Ciudad;
import com.sprinboot.clienteapp.models.entity.Cliente;
import com.sprinboot.clienteapp.models.service.ICiudadService;
import com.sprinboot.clienteapp.models.service.IClienteService;

@Controller
@RequestMapping("/views/clientes")
public class ClienteController {

	@Autowired
	private IClienteService iClienteService;

	@Autowired
	private ICiudadService iCiudadService;

	@Secured("ROLE_USER")
	@GetMapping("/")
	public String listarClientes(Model model) {
		List<Cliente> listadoClientes = iClienteService.listarTodos();

		model.addAttribute("titulo", "Lista de Clientes");
		model.addAttribute("clientes", listadoClientes);

		return "/views/clientes/listar";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/create")
	public String crear(Model model) {

		Cliente cliente = new Cliente();
		List<Ciudad> listaCiudades = iCiudadService.listaCiudades();

		model.addAttribute("titulo", "Formulario: Nuevo Cliente");
		model.addAttribute("cliente", cliente);
		model.addAttribute("ciudades", listaCiudades);

		return "/views/clientes/frmCrear";
	}

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@PostMapping("/save")
	public String guardar(@Valid @ModelAttribute Cliente cliente, BindingResult result, 
			Model model, RedirectAttributes attribute) {
		
		List<Ciudad> listaCiudades = iCiudadService.listaCiudades();

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario: Nuevo Cliente");
			model.addAttribute("cliente", cliente);
			model.addAttribute("ciudades", listaCiudades);
			System.out.println("Existieron errores en el formulario");
			return "/views/clientes/frmCrear";
		}
		
		iClienteService.guardar(cliente);
		System.out.println("Cliente Guardado con exito!");
		attribute.addFlashAttribute("success", "Cliente Guardado con exito!");
		return "redirect:/views/clientes/";
	}

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") Long idCliente, 
			Model model, RedirectAttributes attribute) {

		Cliente cliente = null;
		
		if (idCliente > 0) {
			cliente = iClienteService.buscarPorId(idCliente);
			
			if (cliente == null) {
				System.out.println("Error: El ID del Cliente no exixte!");
				attribute.addFlashAttribute("error", "ATENCION: El ID del Cliente no exixte!");
				return "redirect:/views/clientes/";
			}
		} else {
			System.out.println("Error: El ID del Cliente no existe");
			attribute.addFlashAttribute("error", "ATENCION: Error con el ID del Cliente!");
			return "redirect:/views/clientes/";
		}

		List<Ciudad> listaCiudades = iCiudadService.listaCiudades();

		model.addAttribute("titulo", "Formulario: Editar Cliente");
		model.addAttribute("cliente", cliente);
		model.addAttribute("ciudades", listaCiudades);

		return "/views/clientes/frmCrear";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") Long idCliente, RedirectAttributes attribute) {
		
		Cliente cliente = null;
		
		if (idCliente > 0) {
			cliente = iClienteService.buscarPorId(idCliente);
			
			if (cliente == null) {
				System.out.println("Error: Error con el ID del Cliente");
				attribute.addFlashAttribute("error", "ATENCION: El ID del Cliente no exixte!");
				return "redirect:/views/clientes/";
			}
		} else {
			System.out.println("Error: El ID del Cliente no existe");
			attribute.addFlashAttribute("error", "ATENCION: Error con el ID del Cliente!");
			return "redirect:/views/clientes/";
		}
		
		iClienteService.eliminar(idCliente);
		System.out.println("Registro Eliminado con Exito!");
		attribute.addFlashAttribute("warning", "Registro Eliminado con Exito!");
		return "redirect:/views/clientes/";
	}

}
