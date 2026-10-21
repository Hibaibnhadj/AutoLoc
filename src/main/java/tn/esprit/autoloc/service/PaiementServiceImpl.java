package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.repository.IPaiementRepository;

@Service
public class PaiementServiceImpl extends GenericServiceImpl<Paiement, Long> implements IPaiementService {

    public PaiementServiceImpl(IPaiementRepository repository) {
        super(repository);
    }

    @Override
    protected String getEntityName() {
        return "Paiement";
    }

    @Override
    protected Long getId(Paiement paiement) {
        return paiement.getIdPaiement();
    }

    @Override
    protected void setFields(Paiement source, Paiement target) {
        // read-only: nothing to copy
    }

    @Override
    public Paiement create(Paiement paiement) {
        throw new UnsupportedOperationException("Paiement is read-only");
    }

    @Override
    public Paiement update(Long id, Paiement paiement) {
        throw new UnsupportedOperationException("Paiement is read-only");
    }

    @Override
    public void deleteById(Long id) {
        throw new UnsupportedOperationException("Paiement is read-only");
    }
}