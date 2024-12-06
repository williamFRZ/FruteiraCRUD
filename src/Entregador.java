import javax.swing.*;
import java.sql.*;

class Entregador {
    public static void menu(Connection connection) {
        while (true) {
            String menu = """
                      Entregador:
                      
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
        int id = Integer.parseInt(JOptionPane.showInputDialog("ID do Entregador:"));
        String cnh = JOptionPane.showInputDialog("CNH:");
        String tipoVeiculo = JOptionPane.showInputDialog("Tipo de Veículo (Moto/Carro):");

        if (!tipoVeiculo.equalsIgnoreCase("Moto") && !tipoVeiculo.equalsIgnoreCase("Carro")) {
            JOptionPane.showMessageDialog(null, "Tipo de veículo inválido! Use 'Moto' ou 'Carro'.");
            return;
        }

        String sql = "INSERT INTO entregador (id_entregador, cnh, tipo_veiculo) VALUES (?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.setString(2, cnh);
        stmt.setString(3, tipoVeiculo);

        stmt.executeUpdate();
        JOptionPane.showMessageDialog(null, "Entregador inserido com sucesso!");
    }

    public static void listar(Connection connection) throws SQLException {
        String sql = "SELECT * FROM entregador";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        StringBuilder resultados = new StringBuilder("Entregadores:\n");
        while (rs.next()) {
            resultados.append(String.format("ID: %d | CNH: %s | Tipo de Veículo: %s\n",
                    rs.getInt("id_entregador"),
                    rs.getString("cnh"),
                    rs.getString("tipo_veiculo")));
        }

        JOptionPane.showMessageDialog(null, resultados.toString());
    }

    public static void atualizar(Connection connection) throws SQLException {
        int id = Integer.parseInt(JOptionPane.showInputDialog("ID do entregador a ser atualizado:"));

        String cnh = JOptionPane.showInputDialog("Nova CNH:");
        String tipoVeiculo = JOptionPane.showInputDialog("Novo Tipo de Veículo (Moto/Carro):");

        if (!tipoVeiculo.equalsIgnoreCase("Moto") && !tipoVeiculo.equalsIgnoreCase("Carro")) {
            JOptionPane.showMessageDialog(null, "Tipo de veículo inválido! Use 'Moto' ou 'Carro'.");
            return;
        }

        String sql = "UPDATE entregador SET cnh=?, tipo_veiculo=? WHERE id_entregador=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, cnh);
        stmt.setString(2, tipoVeiculo);
        stmt.setInt(3, id);

        stmt.executeUpdate();
        JOptionPane.showMessageDialog(null, "Entregador atualizado com sucesso!");
    }

    public static void deletar(Connection connection) throws SQLException {
        int id = Integer.parseInt(JOptionPane.showInputDialog("ID do entregador a ser deletado:"));

        String sql = "DELETE FROM entregador WHERE id_entregador=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);

        stmt.executeUpdate();
        JOptionPane.showMessageDialog(null, "Entregador deletado com sucesso!");
    }
}