package ma.example.facturation.service;

import ma.example.facturation.entity.Facture;
import ma.example.facturation.entity.LigneFacture;
import ma.example.facturation.repository.ClientRepository;
import ma.example.facturation.repository.FactureRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class FactureService {

    private static final Set<BigDecimal> TAUX_TVA_AUTORISES = Set.of(
            BigDecimal.ZERO,
            new BigDecimal("5.5"),
            new BigDecimal("10"),
            new BigDecimal("20")
    );

    private final FactureRepository factureRepository;
    private final ClientRepository clientRepository;

    public FactureService(FactureRepository factureRepository, ClientRepository clientRepository) {
        this.factureRepository = factureRepository;
        this.clientRepository = clientRepository;
    }

    public Facture creerFacture(Facture facture) {

        if (facture.getLignes() == null || facture.getLignes().isEmpty()) {
            throw new RuntimeException("Une facture doit contenir au moins une ligne");
        }

        BigDecimal totalHt = BigDecimal.ZERO;
        BigDecimal totalTva = BigDecimal.ZERO;

        for (LigneFacture ligne : facture.getLignes()) {

            if (!TAUX_TVA_AUTORISES.contains(ligne.getTauxTva())) {
                throw new RuntimeException("Taux de TVA non autorisé");
            }

            BigDecimal ligneHt = ligne.getPrixUnitaireHt()
                    .multiply(BigDecimal.valueOf(ligne.getQuantite()));

            BigDecimal ligneTva = ligneHt
                    .multiply(ligne.getTauxTva())
                    .divide(BigDecimal.valueOf(100));

            ligne.setTotalHt(ligneHt);
            ligne.setTotalTva(ligneTva);
            ligne.setFacture(facture);

            totalHt = totalHt.add(ligneHt);
            totalTva = totalTva.add(ligneTva);
        }

        facture.setTotalHt(totalHt);
        facture.setTotalTva(totalTva);
        facture.setTotalTtc(totalHt.add(totalTva));

        return factureRepository.save(facture);
    }

    public List<Facture> rechercherFacturesParClient(Long clientId) {
        return factureRepository.findByClientId(clientId);
    }

    public List<Facture> rechercherFacturesParDate(LocalDate dateFacture) {
        return factureRepository.findByDateFacture(dateFacture);
    }

    public List<Facture> rechercherFacturesParClientEtDate(Long clientId, LocalDate dateFacture) {
        return factureRepository.findByClientIdAndDateFacture(clientId, dateFacture);
    }

    public List<Facture> listerFactures() {
        return factureRepository.findAll();
    }

    public Facture getFacture(Long id) {
        return factureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facture introuvable"));
    }
}
