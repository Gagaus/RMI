package rmi;

import java.io.Serializable;
import java.util.List;

//classe auxiliar compartilhada entre cliente e servidor
//funciona como classe auxiliar para retornar simultaneamente os dados e o tempo de processamento
//utilizado pelo servidor
public class QueryResult implements Serializable {
	private static final long serialVersionUID = 1L;
	public List<List<String>> data;
	//tempo em ms
	public float serverProcessingTime;
}