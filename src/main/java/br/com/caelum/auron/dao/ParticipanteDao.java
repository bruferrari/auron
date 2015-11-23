package br.com.caelum.auron.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.caelum.auron.model.Participante;

@Stateless
public class ParticipanteDao {
	
	@PersistenceContext
	private EntityManager em;
	
	public void inserir(Participante participante) {
		em.persist(participante);
	}

	public List<Participante> getParticipantes() {
		return em.createQuery("from Participante", Participante.class).getResultList();
	}

	public Participante getParticipante(String email, String senha) {

		TypedQuery<Participante> query = 
				em.createQuery("from Participante p where p.email=? and p.senha=?", Participante.class);
		query.setParameter(1, email);
		query.setParameter(2, senha);
		
		return query.getSingleResult();
	}
}
