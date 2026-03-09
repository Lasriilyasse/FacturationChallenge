package ma.example.facturation.controller;

import ma.example.facturation.entity.Client;
import ma.example.facturation.service.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public Client creerClient(@RequestBody Client client) {
        return clientService.creerClient(client);
    }

    @GetMapping
    public List<Client> listerClients() {
        return clientService.listerClients();
    }

    @GetMapping("/{id}")
    public Client getClient(@PathVariable Long id) {
        return clientService.getClient(id);
    }

    @PutMapping("/modifier/{id}")
    public Client modifierClient(@PathVariable Long id, @RequestBody Client client) {
        return clientService.modifierClient(id, client);
    }

    @PostMapping("supprimer/{id}")
    public void supprimerClient(@PathVariable Long id) { clientService.supprimerClient(id); }
}
