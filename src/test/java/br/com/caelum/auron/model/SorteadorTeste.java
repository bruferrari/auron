package br.com.caelum.auron.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class SorteadorTeste {
	private Participante p1;
	private Participante p2;
	private Participante p3;
	private List<Participante> participantes;
	private Sorteio sorteio;

	@Before
	public void setUp() throws Exception {
		p1 = new Participante();
		p1.setNome("José");
		
		p2 = new Participante();
		p2.setNome("Bruno");
		
		p3 = new Participante();
		p3.setNome("Antônio");
		
		participantes = Arrays.asList(p1, p2, p3);
		sorteio = new Sorteio();
	
	}

	@Test
	public void aQuantidadeDeParesEParticipantesDeveSerAMesma() throws SorteioException {
		int quantidadeDeParticipantes = participantes.size();
		
		Sorteador sorteador = new Sorteador(sorteio, participantes);
		sorteador.sortear();
		
		int quantidadeDePares = sorteio.getQuantidadeDePares();
		
		assertEquals(quantidadeDePares, quantidadeDeParticipantes);
	}
	
	@Test(expected=SorteioException.class)
	public void naoDeveAceitarUmaListaComMenosDeDoisParticipantes() throws SorteioException {
		List<Participante> participantes = new ArrayList<>();
		Participante participante = new Participante();
		participante.setNome("Bruno");
		
		participantes.add(participante);
		
		
		Sorteador sorteador = new Sorteador(sorteio, participantes);
		sorteador.sortear();
	}
	
	@Test(expected=SorteioException.class)
	public void naoDeveAceitarUmaListaDeParticipantesNula() throws SorteioException {
		Sorteador sorteador = new Sorteador(sorteio, null);
		sorteador.sortear();
	}
	
	@Test
	public void naoDeveRepetirUmAmigoOculto() throws SorteioException {
		Sorteador sorteador = new Sorteador(sorteio, participantes);
		sorteador.sortear();
		
		Set<Par> pares = sorteio.getPares();
		Iterator<Par> it = pares.iterator();
		
		Par par1 = it.next();
		Par par2 = it.next();
		Par par3 = it.next();
		
		Participante amigoOculto1 = par1.getAmigoOculto();
		Participante amigoOculto2 = par2.getAmigoOculto();
		Participante amigoOculto3 = par3.getAmigoOculto();
		
		assertFalse(amigoOculto1.equals(amigoOculto2));
		assertFalse(amigoOculto2.equals(amigoOculto3));
		assertFalse(amigoOculto3.equals(amigoOculto1));
	}
	
	@Test
	public void deveVerificarSeAmigoEDiferenteDoAmigoOculto() throws SorteioException {
		Sorteador sorteador = new Sorteador(sorteio, participantes);
		sorteador.sortear();
		
		Set<Par> pares = sorteio.getPares();
		Iterator<Par> it = pares.iterator();
		
		Par par = it.next();
		Par par2 = it.next();
		Par par3 = it.next();
		
		Participante amigo1 = par.getAmigo();
		Participante amigoOculto1 = par.getAmigoOculto();
		
		Participante amigo2 = par.getAmigo();
		Participante amigoOculto2 = par.getAmigoOculto();
		
		Participante amigo3 = par.getAmigo();
		Participante amigoOculto3 = par.getAmigoOculto();
		
		assertFalse(amigo1.equals(amigoOculto1));
		assertFalse(amigo2.equals(amigoOculto2));
		assertFalse(amigo3.equals(amigoOculto3));
	}

	@Test
	public void deveVerificarSeAmigoOcultoDoUltimoParEhAmigoDoPrimeiroPar() throws SorteioException {
		Sorteador sorteador = new Sorteador(sorteio, participantes);
		sorteador.sortear();
		
		Set<Par> pares = sorteio.getPares();
		Iterator<Par> it = pares.iterator();
		
		Par par = it.next();
		Par par2 = it.next();
		Par par3 = it.next();
		
		Participante primeiro = par.getAmigo();
		Participante ultimo = par3.getAmigoOculto();
		
		assertTrue(ultimo.equals(primeiro));
	}
}
