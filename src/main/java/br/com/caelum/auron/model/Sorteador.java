package br.com.caelum.auron.model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Sorteador {

	private Sorteio sorteio;
	private List<Participante> participantes;
	private int totalDeParticipantes;

	public Sorteador(Sorteio sorteio, List<Participante> participantes) throws SorteioException {
		if (participantes == null) {
			throw new SorteioException("Por favor insira uma lista de participantes");
		}
		this.sorteio = sorteio;
		this.participantes = participantes;
		totalDeParticipantes = participantes.size();

	}

	public void sortear() throws SorteioException {
		int indiceAtual = 0;

		verificaTamanhoDaListaDeParticipantes();
		embaralhaParticipantes();

		for (indiceAtual = 0; indiceAtual < totalDeParticipantes; indiceAtual++) {
			if (participanteAtualEhUltimo(indiceAtual)) {
				// Gera um participante para a última iteração, ou seja, volta
				// para o primeiro
				// participante da lista
				criaEAdicionaOParNoSorteio(sorteio, indiceAtual, 0);
				break;
			}

			criaEAdicionaOParNoSorteio(sorteio, indiceAtual, indiceAtual + 1);
		}
	}

	private void embaralhaParticipantes() {
		Collections.shuffle(participantes);
	}

	private void verificaTamanhoDaListaDeParticipantes() throws SorteioException {
		if (totalDeParticipantes < 2) {
			throw new SorteioException("Por favor insira uma lista de participantes com no mínimo dois participantes");
		}
	}

	private boolean participanteAtualEhUltimo(int indiceAtual) {
		return indiceAtual == totalDeParticipantes - 1;
	}

	private void criaEAdicionaOParNoSorteio(Sorteio sorteio, int indiceAtual, int indiceFinal) {
		Par par = new Par(participantes.get(indiceAtual), participantes.get(indiceFinal), sorteio);
		sorteio.adicionaPar(par);
	}

}
