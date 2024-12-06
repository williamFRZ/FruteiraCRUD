import javax.swing.*;
import java.sql.*;

class HistoricoCompra {
    public static void menu(Connection connection) {
        while (true) {
            String menu = """
                      Histórico de Compras:
                      
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
        int idCliente = Integer.parseInt(JOptionPane.showInputDialog("ID do Cliente:"));
        int idProduto = Integer.parseInt(JOptionPane.showInputDialog("ID do Produto:"));
        int quantidade = Integer.parseInt(JOptionPane.showInputDialog("Quantidade:"));
        String dataCompra = JOptionPane.showInputDialog("Data da Compra (yyyy-MM-dd):");
        double valorTotal = Double.parseDouble(JOptionPane.showInputDialog("Valor Total:"));

        String sql = "INSERT INTO historicocompra (id_cliente, id_produto, quantidade, data_compra, valor_total) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, idCliente);
        stmt.setInt(2, idProduto);
        stmt.setInt(3, quantidade);
        stmt.setDate(4, Date.valueOf(dataCompra));
        stmt.setDouble(5, valorTotal);

        stmt.executeUpdate();
        JOptionPane.showMessageDialog(null, "Histórico de Compra inserido com sucesso!");
    }

    public static void listar(Connection connection) throws SQLException {
        String sql = "SELECT * FROM historicocompra";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        StringBuilder resultados = new StringBuilder("Histórico de Compras:\n");
        while (rs.next()) {
            resultados.append(String.format("ID: %d | ID Cliente: %d | ID Produto: %d | Quantidade: %d | Data Compra: %s | Valor Total: %.2f\n",
                    rs.getInt("id_historico"),
                    rs.getInt("id_cliente"),
                    rs.getInt("id_produto"),
                    rs.getInt("quantidade"),
                    rs.getDate("data_compra"),
                    rs.getDouble("valor_total")));
        }

        JOptionPane.showMessageDialog(null, resultados.toString());
    }

    public static void atualizar(Connection connection) throws SQLException {
        int idHistorico = Integer.parseInt(JOptionPane.showInputDialog("ID do Histórico de Compra a ser atualizado:"));

        int idCliente = Integer.parseInt(JOptionPane.showInputDialog("Novo ID do Cliente:"));
        int idProduto = Integer.parseInt(JOptionPane.showInputDialog("Novo ID do Produto:"));
        int quantidade = Integer.parseInt(JOptionPane.showInputDialog("Nova Quantidade:"));
        String dataCompra = JOptionPane.showInputDialog("Nova Data da Compra (yyyy-MM-dd):");
        double valorTotal = Double.parseDouble(JOptionPane.showInputDialog("Novo Valor Total:"));

        String sql = "UPDATE historicocompra SET id_cliente=?, id_produto=?, quantidade=?, data_compra=?, valor_total=? WHERE id_historico=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, idCliente);
        stmt.setInt(2, idProduto);
        stmt.setInt(3, quantidade);
        stmt.setDate(4, Date.valueOf(dataCompra));
        stmt.setDouble(5, valorTotal);
        stmt.setInt(6, idHistorico);

        stmt.executeUpdate();
        JOptionPane.showMessageDialog(null, "Histórico de Compra atualizado com sucesso!");
    }

    public static void deletar(Connection connection) throws SQLException {
        int idHistorico = Integer.parseInt(JOptionPane.showInputDialog("ID do Histórico de Compra a ser deletado:"));

        String sql = "DELETE FROM historicocompra WHERE id_historico=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, idHistorico);

        stmt.executeUpdate();
        JOptionPane.showMessageDialog(null, "Histórico de Compra deletado com sucesso!");
    }
}
