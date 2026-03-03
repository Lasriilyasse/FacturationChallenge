package ma.example.facturation.controller;

import ma.example.facturation.entity.Facture;
import ma.example.facturation.service.FactureService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/factures")
public class FactureController {

    private final FactureService factureService;

    public FactureController(FactureService factureService) {
        this.factureService = factureService;
    }

    @PostMapping
    public Facture creerFacture(@RequestBody Facture facture) {
        return factureService.creerFacture(facture);
    }

    @GetMapping
    public List<Facture> listerFactures() {
        return factureService.listerFactures();
    }

    @GetMapping("/{id}/export")
    public Facture exporterFacture(@PathVariable Long id) {
        return factureService.getFacture(id);
    }

    // 🔹 Recherche par client
    @GetMapping("/client/{clientId}")
    public List<Facture> getFacturesParClient(@PathVariable Long clientId) {
        return factureService.rechercherFacturesParClient(clientId);
    }

    // 🔹 Recherche par date
    @GetMapping("/date")
    public List<Facture> getFacturesParDate(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date
    ) {
        return factureService.rechercherFacturesParDate(date);
    }



}