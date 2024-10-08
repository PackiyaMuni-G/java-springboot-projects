package com.springboot.crud.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.DispatcherServlet;

import com.springboot.crud.controller.ClientsController;
import com.springboot.crud.exception.ClientNotFoundException;
import com.springboot.crud.model.Client;
import com.springboot.crud.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);
    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
        logger.info("ClientService initialized with repository: {}",
        clientRepository.getClass().getSimpleName());
        System.out.println("ClientService constructed with repository: " + clientRepository);
    }

    public List<Client> getAllClients() {
        logger.info("Fetching all clients");
        try {
            List<Client> clients = clientRepository.findAll();
            logger.debug("Retrieved {} clients from database", clients.size());
            if (clients.isEmpty()) {
                logger.warn("No clients found in database");
            }
            return clients;
        } catch (Exception e) {
            logger.error("Error occurred while fetching all clients: {}", e.getMessage(), e);
            throw e;
        }
    }

    public Client getClientById(Long id) {
        logger.info("Fetching client with id: {}", id);
        try {
            Optional<Client> client = clientRepository.findById(id);
            if (client.isPresent()) {
                logger.debug("Found client: {}", client.get());
                return client.get();
            } else {
                logger.warn("Client not found with id: {}", id);
                throw new ClientNotFoundException("Client with id " + id + " not found");
            }
        } catch (ClientNotFoundException e) {
            logger.error("Client not found with id: {}", id);
            throw e;
        } catch (Exception e) {
            logger.error("Error occurred while fetching client with id {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
    
    public Client createClient(Client client) {
        logger.info("Creating new client: {}", client);
        try {
            Client savedClient = clientRepository.save(client);
            logger.debug("Successfully created client with id: {}", savedClient.getId());
            return savedClient;
        } catch (Exception e) {
            logger.error("Error occurred while creating client: {}", e.getMessage(), e);
            throw e;
        }
    }

    public void deleteClient(Long id) {
        logger.info("Attempting to delete client with id: {}", id);
        try {
            Optional<Client> client = clientRepository.findById(id);
            if (client.isEmpty()) {
                logger.warn("Cannot delete - client not found with id: {}", id);
                throw new ClientNotFoundException("Client with id " + id + " not found");
            }
            clientRepository.deleteById(id);
            logger.debug("Successfully deleted client with id: {}", id);
        } catch (ClientNotFoundException e) {
            logger.error("Client not found with id: {}", id);
            throw e;
        } catch (Exception e) {
            logger.error("Error occurred while deleting client with id {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    public Client updateClient(Long id, Client client) {
        logger.info("Attempting to update client with id: {}", id);
        try {
            Optional<Client> currentClient = clientRepository.findById(id);
            if (currentClient.isPresent()) {
                Client currentClientDetails = currentClient.get();
                logger.debug("Found existing client: {}", currentClientDetails);
                
                // Log changes being made
                if (!currentClientDetails.getName().equals(client.getName())) {
                    logger.debug("Updating name from '{}' to '{}'", 
                        currentClientDetails.getName(), client.getName());
                }
                if (!currentClientDetails.getEmail().equals(client.getEmail())) {
                    logger.debug("Updating email from '{}' to '{}'", 
                        currentClientDetails.getEmail(), client.getEmail());
                }
                
                currentClientDetails.setName(client.getName());
                currentClientDetails.setEmail(client.getEmail());
                
                Client updatedClient = clientRepository.save(currentClientDetails);
                logger.debug("Successfully updated client: {}", updatedClient);
                return updatedClient;
            } else {
                logger.warn("Cannot update - client not found with id: {}", id);
                throw new ClientNotFoundException("Client not found with id " + id);
            }
        } catch (ClientNotFoundException e) {
            logger.error("Client not found with id: {}", id);
            throw e;
        } catch (Exception e) {
            logger.error("Error occurred while updating client with id {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}
