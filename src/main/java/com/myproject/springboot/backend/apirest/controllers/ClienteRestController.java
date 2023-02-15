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
import jakarta.transaction.Status;
import java.util.HashMap;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;

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
    public ResponseEntity<?> view(@RequestBody Cliente cliente) {

        Map<String, Object> response = new HashMap<>();
        Cliente clientResult = null;
        try {
            clientResult = iClienteService.findBy(cliente.getId());
        } catch (DataAccessException d) {
            response.put("message", "Error al realizar la consulta en base de datos");
            response.put("code", HttpStatus.NOT_FOUND);
            response.put("error", d.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        if (clientResult == null) {
            response.put("message", "El cliente con ID: ".concat(cliente.getId().toString()).concat(" no existe"));
            response.put("code", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(clientResult, HttpStatus.OK);
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity save(@RequestBody Cliente cliente) {
        Cliente newCliente = null;
        Map<String, Object> response = new HashMap<>();
        try {
            newCliente = iClienteService.save(cliente);
        } catch (DataAccessException dataE) {
            response.put("message", "Error al insertar en base de datos");
            response.put("code", HttpStatus.INTERNAL_SERVER_ERROR);
            response.put("error", dataE.getMessage());

        }

        //TODO validar que el objecto no venga vacio para responder con el mensaje de éxito
        
        response.put("message", "El Cliente se ha creado con éxito");
        response.put("cliente", newCliente);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
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
