package com.springboot.crud;


import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static org.mockito.ArgumentMatchers.any;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import com.springboot.crud.exception.ClientNotFoundException;
import com.springboot.crud.model.Client;
import com.springboot.crud.repository.ClientRepository;
import com.springboot.crud.service.ClientService;

@ExtendWith(MockitoExtension.class)
class SpringBootRestCrud1ApplicationTests {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    private Client client1;
    private Client client2;

    @BeforeEach
    void setup() {
        client1 = new Client(1L, "John Doe", "john@example.com");
        client2 = new Client(2L, "Jane Doe", "jane@example.com");

        // Use lenient() for stubbing that may not be used in every test case
        lenient().doReturn(Arrays.asList(client1, client2)).when(clientRepository).findAll();
    }

    @Test
    public void testGetAllClients() {
        // Call the service method
        List<Client> clients = clientService.getAllClients();

        // Verify the mock was called
        verify(clientRepository).findAll();

        // Verify the result
        assertEquals(2, clients.size());
        assertEquals("John Doe", clients.get(0).getName());
        assertEquals("Jane Doe", clients.get(1).getName());
    }

    @Test
    public void testGetClientById() {
        // Stub the repository to return client1 when findById(1L) is called
        doReturn(Optional.of(client1)).when(clientRepository).findById(1L);

        // Call the service method
        Client client = clientService.getClientById(1L);

        // Verify the result
        assertEquals("John Doe", client.getName());
        assertEquals("john@example.com", client.getEmail());

        // Verify that findById was called
        verify(clientRepository).findById(1L);
    }
    @Test
    public void testGetClientById_ClientNotFound() {
    	           doReturn(Optional.empty()).when(clientRepository).findById(1999L);
    	                    ClientNotFoundException exception = assertThrows(ClientNotFoundException.class, ()->{
    	                    	clientService.getClientById(1999L);
    	                    });
    	           verify(clientRepository).findById(1999L);
    	           assertEquals("Client with id 1999 not found", exception.getMessage());
    	           }
    @Test
    public void testcreateClient() {
    	       doReturn(client1).when(clientRepository).save(client1);
    	       Client newclient = clientService.createClient(client1);
    	       verify(clientRepository).save(client1);
    	       assertEquals("John Doe", newclient.getName());
    	       assertEquals("john@example.com", newclient.getEmail());
    	       
    }
    @Test
    public void testupdateClient() {
    	Long id = client1.getId();
//    	Client updatedClientInfo=new Client(id,"Muni","muni234@gmail.com");
    	Client expectedupdatedClient=new Client(id,"Muni","muni234@gmail.com");
    	                 doReturn(Optional.of(client1)).when(clientRepository).findById(id);
    	                  doReturn(expectedupdatedClient).when(clientRepository).save(any());
    	             
    	                  Client updatedClient = clientService.updateClient(id, client1);
    	                 
    	                  assertEquals("Muni", updatedClient.getName());
    	                  assertEquals("muni234@gmail.com",updatedClient.getEmail());
    	                  verify(clientRepository).findById(id);
    	                  verify(clientRepository).save(any());
    	              
    }   
    @Test
    public void testUpdateClient_ClientNotFound() {
    	Long id=1999L;
    	       doReturn(Optional.empty()).when(clientRepository).findById(id);
    	       assertThrows(ClientNotFoundException.class, ()->{
    	    	    clientService.updateClient(id, client1);
    	       });
    }
    @Test
    public void testdeleteClient_byId() {
    	doReturn(Optional.of(client1)).when(clientRepository).findById(1L);

        
        // Call the service method to delete the client
        clientService.deleteClient(1L);  
        
        // Verify that deleteById was called exactly once
        verify(clientRepository, times(1)).deleteById(1L);  
    }
    
    @Test
    public void testdeleteClient_ClientNotFound() {
    	Long id=1999L;
    	doReturn(Optional.empty()).when(clientRepository).findById(id);
    	          ClientNotFoundException exception = assertThrows(ClientNotFoundException.class, ()->{
    	        	 clientService.deleteClient(id); 
    	          });
    	        
   	           assertEquals("Client with id 1999 not found", exception.getMessage());
    	          
    	          verify(clientRepository, never()).deleteById(id);
    }
    
}
