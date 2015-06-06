package rmi;

import java.io.Serializable;
import java.util.List;

public class QueryResult implements Serializable {
	private static final long serialVersionUID = 1L;
	public List<List<String>> data;
	public float serverProcessingTime;
}