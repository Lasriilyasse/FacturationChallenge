package ma.example.facturation.service;

import ma.example.facturation.entity.Client;
import ma.example.facturation.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client creerClient(Client client) {
        client.setDateCreation(LocalDate.now());
        return clientRepository.save(client);
    }

    public List<Client> listerClients() {
        return clientRepository.findAll();
    }

    public Client getClient(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client introuvable"));
    }

    public void supprimerClient(Long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
        }
    }
}
