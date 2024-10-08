package com.springboot.crud.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.crud.model.Client;
import com.springboot.crud.service.ClientService;

@RestController
@CrossOrigin(origins = "http://localhost:3001")
@RequestMapping("/clients")
public class ClientsController {
    private static final Logger logger = LoggerFactory.getLogger(ClientsController.class);

    private final ClientService clientService;

    @Autowired
    public ClientsController(ClientService clientService) {
        this.clientService = clientService;
        logger.debug("ClientsController initialized");
    }

    @GetMapping
    public List<Client> getClients() {
    	logger.info("Received a get rquest from all clients");
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    public Client getClientByID(@PathVariable Long id) {
    	   logger.info("Fetching client with id: {}", id);
        Client client=clientService.getClientById(id);
        if (client != null) {
            logger.debug("Retrieved client: {}", client);
        } else {
            logger.warn("No client found with id: {}", id);
        }
        return client;
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) throws URISyntaxException {
    	  logger.info("Creating new client: {}", client);
        Client savedClient = clientService.createClient(client);
        logger.debug("Created client with id: {}", savedClient.getId());
        return ResponseEntity.created(new URI("/clients/" + savedClient.getId())).body(savedClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
    	 logger.info("Deleting client with id: {}", id);
        clientService.deleteClient(id); // Correctly delete the client
        logger.debug("Deleted client with id: {}", id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client client) {
    	   logger.info("Updating client with id: {}", id);
        Client updatedClient = clientService.updateClient(id, client);
        logger.debug("Updated client: {}", updatedClient);
        return ResponseEntity.ok(updatedClient);
    }
}
