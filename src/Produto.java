import javax.swing.*;
import java.sql.*;
import java.text.SimpleDateFormat;

class Produto {
    public static void menu(Connection connection) {
        while (true) {
            String menu = """
                      Produto:
                      
                    1 - Inserir
                    2 - Listar
                    3 - Atualizar
                    4 - Deletar
                    0 - Voltar
                    Escolha uma opção:
                    """;
            String opcao = JOptionPane.showInputDialog(menu);

            if (opcao == null || opcao.equals("0")) break;

            try {
                switch (opcao) {
                    case "1" -> inserir(connection);
                    case "2" -> listar(connection);
                    case "3" -> atualizar(connection);
                    case "4" -> deletar(connection);
                    default -> JOptionPane.showMessageDialog(null, "Opção inválida!");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
            }
        }
    }

    public static void inserir(Connection connection) throws SQLException {
        String nome = JOptionPane.showInputDialog("Nome do Produto:");
        String categoria = JOptionPane.showInputDialog("Categoria (Fruta, Legume, Verdura, Outro):");
        if (!categoria.equalsIgnoreCase("Fruta") && !categoria.equalsIgnoreCase("Legume") &&
                !categoria.equalsIgnoreCase("Verdura") && !categoria.equalsIgnoreCase("Outro")) {
            JOptionPane.showMessageDialog(null, "Categoria inválida!");
            return;
        }
        int quantidadeEstoque = Integer.parseInt(JOptionPane.showInputDialog("Quantidade em Estoque:"));
        double preco = Double.parseDouble(JOptionPane.showInputDialog("Preço:"));
        String dataValidade = JOptionPane.showInputDialog("Data de Validade (yyyy-MM-dd):");

        String sql = "INSERT INTO produto (nome, categoria, quantidade_estoque, preco, data_validade) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, nome);
        stmt.setString(2, categoria);
        stmt.setInt(3, quantidadeEstoque);
        stmt.setDouble(4, preco);
        stmt.setDate(5, Date.valueOf(dataValidade));

        stmt.executeUpdate();
        JOptionPane.showMessageDialog(null, "Produto inserido com sucesso!");
    }

    public static void listar(Connection connection) throws SQLException {
        String sql = "SELECT * FROM produto";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        StringBuilder resultados = new StringBuilder("Produtos:\n");
        while (rs.next()) {
            resultados.append(String.format("ID: %d | Nome: %s | Categoria: %s | Estoque: %d | Preço: %.2f | Validade: %s\n",
                    rs.getInt("id_produto"),
                    rs.getString("nome"),
                    rs.getString("categoria"),
                    rs.getInt("quantidade_estoque"),
                    rs.getDouble("preco"),
                    rs.getDate("data_validade")));
        }

        JOptionPane.showMessageDialog(null, resultados.toString());
    }

    public static void atualizar(Connection connection) throws SQLException {
        int id = Integer.parseInt(JOptionPane.showInputDialog("ID do produto a ser atualizado:"));

        String nome = JOptionPane.showInputDialog("Novo Nome do Produto:");
        String categoria = JOptionPane.showInputDialog("Nova Categoria (Fruta, Legume, Verdura, Outro):");
        if (!categoria.equalsIgnoreCase("Fruta") && !categoria.equalsIgnoreCase("Legume") &&
                !categoria.equalsIgnoreCase("Verdura") && !categoria.equalsIgnoreCase("Outro")) {
            JOptionPane.showMessageDialog(null, "Categoria inválida!");
            return;
        }
        int quantidadeEstoque = Integer.parseInt(JOptionPane.showInputDialog("Nova Quantidade em Estoque:"));
        double preco = Double.parseDouble(JOptionPane.showInputDialog("Novo Preço:"));
        String dataValidade = JOptionPane.showInputDialog("Nova Data de Validade (yyyy-MM-dd):");

        String sql = "UPDATE produto SET nome=?, categoria=?, quantidade_estoque=?, preco=?, data_validade=? WHERE id_produto=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, nome);
        stmt.setString(2, categoria);
        stmt.setInt(3, quantidadeEstoque);
        stmt.setDouble(4, preco);
        stmt.setDate(5, Date.valueOf(dataValidade));
        stmt.setInt(6, id);

        stmt.executeUpdate();
        JOptionPane.showMessageDialog(null, "Produto atualizado com sucesso!");
    }

    public static void deletar(Connection connection) throws SQLException {
        int id = Integer.parseInt(JOptionPane.showInputDialog("ID do produto a ser deletado:"));

        String sql = "DELETE FROM produto WHERE id_produto=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);

        stmt.executeUpdate();
        JOptionPane.showMessageDialog(null, "Produto deletado com sucesso!");
    }
}