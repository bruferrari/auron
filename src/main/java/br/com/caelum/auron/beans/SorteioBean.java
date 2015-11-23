package br.com.caelum.auron.beans;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.auron.dao.ParticipanteDao;
import br.com.caelum.auron.dao.SorteioDao;
import br.com.caelum.auron.model.Par;
import br.com.caelum.auron.model.Participante;
import br.com.caelum.auron.model.Sorteador;
import br.com.caelum.auron.model.Sorteio;
import br.com.caelum.auron.model.SorteioException;

@Named
@RequestScoped
public class SorteioBean {

	private Sorteio sorteio = new Sorteio();
	
	@Inject
	private ParticipanteDao participanteDao;
	
	@Inject SorteioDao sorteioDao;

	public Sorteio getSorteio() {
		return sorteio;
	}
	
	public List<Par> getPares() {
		return sorteioDao.getPares();
	}
	
	public void sortear() throws SorteioException {
		List<Participante> participantes = participanteDao.getParticipantes();
		Sorteador sorteador = new Sorteador(sorteio, participantes);
		sorteador.sortear();
		sorteioDao.inserir(sorteio);
		
		try {
			sorteador.sortear();
		} catch (SorteioException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
		}
	}

}
