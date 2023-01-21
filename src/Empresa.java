import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Empresa {
	private Long id;
	private String nome;
	private String cnpj;
	private Double taxa;
	private BigDecimal saldo;

	private List<Produto> produtos = new ArrayList<>();

	public Empresa() {
		super();
	}

	public Empresa(Long id, String nome, String cnpj, Double taxa, BigDecimal saldo, List<Produto> produtos) {
		super();
		this.id = id;
		this.nome = nome;
		this.cnpj = cnpj;
		this.taxa = taxa;
		this.saldo = saldo;
		this.produtos = produtos;
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

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Double getTaxa() {
		return taxa;
	}

	public void setTaxa(Double taxa) {
		this.taxa = taxa;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Empresa empresa = (Empresa) o;
		return id.equals(empresa.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
