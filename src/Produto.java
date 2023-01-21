import java.math.BigDecimal;

public class Produto {
	private Long id;
	private String nome;
	private Integer quantidade;
	private BigDecimal preco;

	public Produto(Long id,String nome, Integer quantidade, BigDecimal preco) {
		super();
		this.id = id;
		this.nome = nome;
		this.quantidade = quantidade;
		this.preco = preco;
	}

	public Produto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public BigDecimal obterValorTotal() {
		return this.getPreco().multiply(BigDecimal.valueOf(this.getQuantidade()));
	}

}
