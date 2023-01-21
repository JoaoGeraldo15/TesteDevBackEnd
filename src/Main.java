import java.math.BigDecimal;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        // SIMULANDO BANCO DE DADOS

        List<Produto> carrinho = new ArrayList<>();
        List<Venda> vendas = new ArrayList<>();

        Produto produto = new Produto(1L, "Pão Frances", 5, BigDecimal.valueOf(3.50));
        Produto produto2 = new Produto(2L, "Coturno", 10, BigDecimal.valueOf(400.0));
        Produto produto3 = new Produto(3L, "Jaqueta Jeans", 15, BigDecimal.valueOf(150.0));
        Produto produto4 = new Produto(4L, "Calça Sarja", 15, BigDecimal.valueOf(150.0));
        Produto produto5 = new Produto(5L, "Prato feito - Frango", 10, BigDecimal.valueOf(25.0));
        Produto produto6 = new Produto(6L, "Prato feito - Carne", 10, BigDecimal.valueOf(25.0));
        Produto produto7 = new Produto(7L, "Suco Natural", 30, BigDecimal.valueOf(10.0));
        Produto produto8 = new Produto(8L, "Sonho", 5, BigDecimal.valueOf(8.50));
        Produto produto9 = new Produto(9L, "Croissant", 7, BigDecimal.valueOf(6.50));
        Produto produto10 = new Produto(10L, "Ché Gelado", 4, BigDecimal.valueOf(5.50));

        Empresa empresa = new Empresa(2L, "SafeWay Padaria", "30021423000159", 0.15, BigDecimal.valueOf(0.0),
                Arrays.asList(produto, produto8, produto9, produto10));
        Empresa empresa2 = new Empresa(1L, "Level Varejo", "53239160000154", 0.05, BigDecimal.valueOf(0.0),
                Arrays.asList(produto2, produto3, produto4));
        Empresa empresa3 = new Empresa(3L, "SafeWay Restaurante", "41361511000116", 0.20, BigDecimal.valueOf(0.0),
                Arrays.asList(produto5, produto6, produto7));


        Cliente cliente = new Cliente("07221134049", "Allan da Silva", "cliente", 20);
        Cliente cliente2 = new Cliente("72840700050", "Samuel da Silva", "cliente2", 24);

        Usuario usuario1 = new Usuario(1L, "admin", "1234", null, null);
        Usuario usuario2 = new Usuario(2L, "empresa", "1234", null, empresa);
        Usuario usuario3 = new Usuario(3L, "cliente", "1234", cliente, null);
        Usuario usuario4 = new Usuario(4L, "cliente2", "1234", cliente2, null);
        Usuario usuario5 = new Usuario(5L, "empresa2", "1234", null, empresa2);
        Usuario usuario6 = new Usuario(6L, "empresa3", "1234", null, empresa3);


        List<Usuario> usuarios = Arrays.asList(usuario1, usuario2, usuario3, usuario4, usuario5, usuario6);
        List<Empresa> empresas = Arrays.asList(empresa, empresa2, empresa3);
        executar(usuarios, empresas, carrinho, vendas);
    }

    public static void executar(List<Usuario> usuarios, List<Empresa> empresas,
                                List<Produto> carrinho, List<Venda> vendas) {
        Scanner sc = new Scanner(System.in);
        int continuar;
        do {

            System.out.println("Entre com seu usuário e senha:");
            System.out.print("Usuário: ");
            String username = sc.next();
            System.out.print("Senha: ");
            String senha = sc.next();

            List<Usuario> usuariosSearch = usuarios.stream().filter(x -> x.getUsername().equals(username)).toList();
            if (usuariosSearch.size() > 0) {
                Usuario usuarioLogado = usuariosSearch.get(0);
                if ((usuarioLogado.getSenha().equals(senha))) {

                    System.out.println("Escolha uma opção para iniciar");
                    if (usuarioLogado.isEmpresa()) {
                        menuEmpresa(vendas, sc, usuarioLogado);

                    } else {
                        menuCliente(empresas, carrinho, vendas, sc, usuarioLogado);
                    }

                } else
                    System.out.println("Senha incorreta");
            } else {
                System.out.println("Usuário não encontrado");
            }
            System.out.println("0 --> Finalizar Progrmar");
            System.out.println("1 --> continuar");
            continuar = sc.nextInt();
        } while (continuar != 0);

    }

    private static void menuCliente(List<Empresa> empresas, List<Produto> carrinho, List<Venda> vendas, Scanner sc, Usuario usuarioLogado) {
        int escolha = -1;
        while (escolha != 0) {
            System.out.println("1 - Relizar Compras");
            System.out.println("2 - Ver Compras");
            System.out.println("0 - Deslogar");
            escolha = sc.nextInt();
            switch (escolha) {
                case 1: {
                    System.out.println("Para realizar uma compra, escolha a empresa onde deseja comprar: ");
                    empresas.forEach(x -> {
                        System.out.println(x.getId() + " - " + x.getNome());
                    });
                    Long idEmpresaEscolhida = sc.nextLong();
                    Optional<Empresa> empresaEscolhida = empresas.stream()
                            .filter(empresa -> empresa.getId().equals(idEmpresaEscolhida)).findFirst();


                    Long escolhaProduto = -1L;
                    if (empresaEscolhida.isPresent()) {
                        do {
                            System.out.println("Escolha os seus produtos: ");

                            mostrarProdutosEmpresa(empresaEscolhida.get());

                            System.out.println("0 - Finalizar compra");
                            escolhaProduto = sc.nextLong();
                            Long finalEscolhaProduto = escolhaProduto;
                            Optional<Produto> produtoEscolhido = empresaEscolhida.get().getProdutos().stream()
                                    .filter(produto -> produto.getId().equals(finalEscolhaProduto)).findFirst();

                            if (produtoEscolhido.isPresent()) {
                                int quantidade = -1;
                                Produto produto = produtoEscolhido.get();
                                while (quantidade < 0 || quantidade > produto.getQuantidade()) {
                                    System.out.println("Informe a quantidade desejada: ");
                                    quantidade = sc.nextInt();
                                }
                                produto.setQuantidade(produto.getQuantidade() - quantidade);
                                carrinho.add(new Produto(produto.getId(), produto.getNome(), quantidade, produto.getPreco()));
                            }

                        } while (escolhaProduto != 0);
                        System.out.println("************************************************************");
                        System.out.println("Resumo da compra: ");
                        carrinho.forEach(produto -> {
                            System.out.println(produto.getId() + " - " + produto.getNome() + "    R$ "
                                    + produto.getPreco().multiply(BigDecimal.valueOf(produto.getQuantidade())));
                        });

                        Venda venda = criarVenda(carrinho, empresaEscolhida.get(), usuarioLogado.getCliente(), vendas);
                        System.out.println("Total: R$" + venda.getValor());
                        System.out.println("************************************************************");
                        carrinho.clear();
                    }
                    break;
                }
                case 2: {
                    System.out.println();
                    System.out.println("************************************************************");
                    System.out.println("COMPRAS EFETUADAS");
                    vendas.forEach(venda -> {
                        if (venda.getCliente().getUsername().equals(usuarioLogado.getUsername())) {
                            System.out.println("************************************************************");
                            System.out.println("Compra de código: " + venda.getCodigo() + " na empresa "
                                    + venda.getEmpresa().getNome() + ": ");
                            venda.getItens().forEach(x -> {
                                System.out.println(x.getId() + " - " + x.getNome() + "    R$" + x.getPreco() + " x " + x.getQuantidade());
                            });
                            System.out.println("Total: R$" + venda.getValor());
                            System.out.println("************************************************************");
                        }

                    });
                    break;
                }
                case 0: {
                    break;
                }

            }
        }

    }

    private static void mostrarProdutosEmpresa(Empresa empresaEscolhida) {
        empresaEscolhida.getProdutos().forEach(produto -> {
            System.out.printf("%d - %s - %f %d%n", produto.getId(), produto.getNome(), produto.getPreco().setScale(2), produto.getQuantidade());
        });

    }

    private static void menuEmpresa(List<Venda> vendas, Scanner sc, Usuario usuarioLogado) {
        int escolha = -1;
        while (escolha != 0) {
            System.out.println("1 - Listar vendas");
            System.out.println("2 - Ver produtos");
            System.out.println("0 - Deslogar");
            escolha = sc.nextInt();

            switch (escolha) {
                case 1: {
                    System.out.println();
                    System.out.println("************************************************************");
                    System.out.println("VENDAS EFETUADAS");
                    List<Venda> vendasEmpresa = vendas.stream()
                            .filter(venda -> venda.getEmpresa().getId().equals(usuarioLogado.getEmpresa().getId()))
                            .toList();

                    vendasEmpresa.forEach(venda -> {

                        System.out.println("************************************************************");
                        System.out.println("Venda de código: " + venda.getCodigo() + " no CPF "
                                + venda.getCliente().getCpf() + ": ");

                        venda.getItens().forEach(x -> {
                            System.out.println(x.getId() + " - " + x.getNome() + "    R$" + x.getPreco());
                        });
                        System.out.println("Total Venda: R$" + venda.getValor());
                        System.out.println("Total Taxa a ser paga: R$" + venda.getComissaoSistema());
                        System.out.println("Total Líquido  para empresa: "
                                + (venda.getValor().subtract(venda.getComissaoSistema())));
                        System.out.println("************************************************************");


                    });
                    System.out.println("Saldo Empresa: " + usuarioLogado.getEmpresa().getSaldo());
                    System.out.println("************************************************************");
                    break;

                }
                case 2: {
                    System.out.println();
                    System.out.println("************************************************************");
                    System.out.println("MEUS PRODUTOS");
                    usuarioLogado.getEmpresa().getProdutos().forEach(produto -> {
                        System.out.println("************************************************************");
                        System.out.println("Código: " + produto.getId());
                        System.out.println("Produto: " + produto.getNome());
                        System.out.println("Quantidade em estoque: " + produto.getQuantidade());
                        System.out.println("Valor: R$" + produto.getPreco());
                        System.out.println("************************************************************");
                    });

                    System.out.println("Saldo Empresa: " + usuarioLogado.getEmpresa().getSaldo());
                    System.out.println("************************************************************");
                    break;

                }
                case 0: {
                    break;

                }
                default: {
                    System.out.println("Opção inválida");
                }
            }

        }
    }

    public static Venda criarVenda(List<Produto> carrinho, Empresa empresa, Cliente cliente, List<Venda> vendas) {
        BigDecimal total = carrinho.stream().map(Produto::obterValorTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal comissaoSistema = total.multiply(BigDecimal.valueOf(empresa.getTaxa()));
        int idVenda = vendas.isEmpty() ? 1 : vendas.get(vendas.size() - 1).getCodigo() + 1;
        Venda venda = new Venda(idVenda, carrinho.stream().toList(), total, comissaoSistema, empresa, cliente);
        empresa.setSaldo(empresa.getSaldo().add(total.subtract(comissaoSistema)));
        vendas.add(venda);
        return venda;
    }
}
