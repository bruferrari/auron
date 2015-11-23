package br.com.caelum.auron.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.caelum.auron.model.Par;
import br.com.caelum.auron.model.Sorteio;

@Stateless
public class SorteioDao {

	@PersistenceContext
	private EntityManager em;

	public void inserir(Sorteio sorteio) {
		em.persist(sorteio);
	}

	public List<Par> getPares() {
		return em.createQuery("from Par", Par.class).getResultList();
	}
	
	
}
