package br.com.caelum.auron.shiro;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;

import br.com.caelum.auron.dao.ParticipanteDao;
import br.com.caelum.auron.model.Participante;

public class Autenticador implements Realm {

	private ParticipanteDao participanteDao;

	@Override
	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken usernameToken = (UsernamePasswordToken) token;

		String email = usernameToken.getUsername();
		String senha = new String(usernameToken.getPassword());
		
		participanteDao = getParticipanteDao();

		Participante participante = participanteDao.getParticipante(email, senha);

		if (participante != null) {
			AuthenticationInfo info = new SimpleAuthenticationInfo(email, senha, getName());
			return info;
		}
		throw new AuthenticationException();

	}

	public ParticipanteDao getParticipanteDao() {
		Properties props = new Properties();
		props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		try {
			InitialContext ctx = new InitialContext(props);
			ParticipanteDao dao = (ParticipanteDao) ctx.lookup("java:module/ParticipanteDao");
			return dao;
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public boolean supports(AuthenticationToken arg0) {
		return true;
	}

}
