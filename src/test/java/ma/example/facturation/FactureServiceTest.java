package ma.example.facturation;

import ma.example.facturation.entity.Client;
import ma.example.facturation.entity.Facture;
import ma.example.facturation.entity.LigneFacture;
import ma.example.facturation.repository.FactureRepository;
import ma.example.facturation.service.FactureService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FactureServiceTest {

    @Mock
    private FactureRepository factureRepository;

    @InjectMocks
    private FactureService factureService;

    @Test
    void factureTest() {


        Client client = new Client();
        client.setId(1L);

        LigneFacture ligne = new LigneFacture();
        ligne.setDescription("Développement");
        ligne.setQuantite(2);
        ligne.setPrixUnitaireHt(new BigDecimal("100"));
        ligne.setTauxTva(new BigDecimal("20"));

        Facture facture = new Facture();
        facture.setClient(client);
        facture.setDateFacture(LocalDate.now());
        facture.setLignes(List.of(ligne));


        when(factureRepository.save(any(Facture.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Facture resultat = factureService.creerFacture(facture);

        // 🔹 THEN (assertions)
        assertEquals(new BigDecimal("200"), resultat.getTotalHt());
        assertEquals(new BigDecimal("40"), resultat.getTotalTva());
        assertEquals(new BigDecimal("240"), resultat.getTotalTtc());

        assertEquals(1, resultat.getLignes().size());
        assertEquals(new BigDecimal("200"), resultat.getLignes().get(0).getTotalHt());
        assertEquals(new BigDecimal("40"), resultat.getLignes().get(0).getTotalTva());
    }
}