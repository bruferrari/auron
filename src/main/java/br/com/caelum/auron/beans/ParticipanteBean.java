package br.com.caelum.auron.beans;


import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import br.com.caelum.auron.dao.ParticipanteDao;
import br.com.caelum.auron.model.Participante;

@Named
@RequestScoped
public class ParticipanteBean {

	private Participante participante = new Participante();
	private List<Participante> participantes;
	
	@Inject
	private ParticipanteDao dao;
	
	@Inject
	private Subject user;
	
	@Inject
	private FacesContext ctx;
	
	public void cadastrar() {
		dao.inserir(participante);
	}

	public Participante getParticipante() {
		return participante;
	}
	
	public List<Participante> getParticipantes() {
		if(participantes == null) {
			return dao.getParticipantes();
		} else {
			return participantes;
		}
		
	}
	
	@SuppressWarnings("static-access")
	public String login() {
		try{
		UsernamePasswordToken token = new UsernamePasswordToken(participante.getEmail(), participante.getSenha());
		
		user.login(token);
		
		return "sorteio?faces-redirect=true";
		
		} catch (AuthenticationException e) {
			ctx.getCurrentInstance().addMessage(null, new FacesMessage("Usuário e/ou senha inválidos"));
		}
		
		return null;
	}

}
