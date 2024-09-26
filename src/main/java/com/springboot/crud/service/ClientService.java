package com.springboot.crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.crud.exception.ClientNotFoundException;
import com.springboot.crud.model.Client;
import com.springboot.crud.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
        System.out.println("ClientService constructed with repository: " + clientRepository);
    }

    public List<Client> getAllClients() {
    	 System.out.println("getAllClients called, repository is: " + clientRepository);
    	  System.out.println("Repository class: " + clientRepository.getClass().getName());
         List<Client> clients = clientRepository.findAll();
        System.out.println("findAll returned: " + clients);
        return clients;
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client with id " + id + " not found"));
    }

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public void deleteClient(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        if(client.isEmpty()) {
        	throw new ClientNotFoundException("Client with id " + id + " not found");
        }
        clientRepository.deleteById(id);
    }

    public Client updateClient(Long id, Client client) {
    	               Optional<Client> currentClient = clientRepository.findById(id);
    	               if(currentClient.isPresent()) {
    	            	       Client currentClientDetails = currentClient.get();
    	            	       currentClientDetails.setName(client.getName());
    	            	       currentClientDetails.setEmail(client.getEmail());
    	            	       return clientRepository.save(currentClientDetails);
    	               }
    	               else
    	            	   throw new ClientNotFoundException("Client not found with id"+ id);
    	          
//        Client currentClient = getClientById(id);
//        currentClient.setName(client.getName());
//        currentClient.setEmail(client.getEmail());
//        return clientRepository.save(currentClient);
    }
}
