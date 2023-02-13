package com.myproject.springboot.backend.apirest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.springboot.backend.apirest.models.entity.Cliente;
import com.myproject.springboot.backend.apirest.models.service.IClienteService;
import org.springframework.web.bind.annotation.DeleteMapping;

@CrossOrigin(origins = {"http://localhost:4200"}) //urls que puedan acceder a la api
@RestController
@RequestMapping("/api")
public class ClienteRestController {

    @Autowired
    private IClienteService iClienteService;

    @GetMapping("/clientsFindAll")
    public List<Cliente> index() {
        return iClienteService.findAll();
    }

    @PostMapping("/clientsById")
    public Cliente view(@RequestBody Cliente cliente) {

        return iClienteService.findBy(cliente.getId());
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente save(@RequestBody Cliente cliente) {
        return iClienteService.save(cliente);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente update(@RequestBody Cliente cliente) {

        //primero buscar si un cliente existe - Si existe se actualiza de lo contrario
        //retorna error
        Cliente clienteActual = iClienteService.findBy(cliente.getId());

        clienteActual.setName(cliente.getName());
        clienteActual.setLastName(cliente.getLastName());
        clienteActual.setEmail(cliente.getEmail());

        return iClienteService.save(clienteActual);

    }

    @PostMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody Cliente cliente) {
        iClienteService.deleteById(cliente.getId());
    }

}
