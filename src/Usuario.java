import java.util.Objects;

public class Usuario {

	private Long id;
	private String username;
	private String senha;
	private Cliente cliente;
	private Empresa empresa;

	public Usuario() {
		super();
	}

	public Usuario(Long id, String username, String senha, Cliente cliente, Empresa empresa) {
		super();
		this.id = id;
		this.username = username;
		this.senha = senha;
		this.cliente = cliente;
		this.empresa = empresa;
	}

	public boolean IsAdmin() {
		return this.empresa == null && this.cliente == null;
	}

	public boolean isEmpresa() {
		return this.empresa != null;
	}

	public boolean IsCliente() {
		return this.cliente != null;
	}

	public String getUsername() {
		return username;
	}

	public Long getId() {
		return id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Usuario usuario = (Usuario) o;
		return id.equals(usuario.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
