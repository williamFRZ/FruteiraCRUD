import javax.swing.*;
import java.sql.*;
import java.text.SimpleDateFormat;

class Entrega {
    public static void menu(Connection connection) {
        while (true) {
            String menu = """
                      Entrega:
                      
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
        int idEntregador = Integer.parseInt(JOptionPane.showInputDialog("ID do Entregador:"));
        String dataHora = JOptionPane.showInputDialog("Data e Hora (ano-mês-dia hora:minuto:segundo):");
        double valorEntrega = Double.parseDouble(JOptionPane.showInputDialog("Valor da Entrega:"));
        double valorTotalCompra = Double.parseDouble(JOptionPane.showInputDialog("Valor Total da Compra:"));

        String sql = "INSERT INTO entrega (id_cliente, id_entregador, data_hora, valor_entrega, valor_total_compra) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, idCliente);
        stmt.setInt(2, idEntregador);
        stmt.setTimestamp(3, Timestamp.valueOf(dataHora));
        stmt.setDouble(4, valorEntrega);
        stmt.setDouble(5, valorTotalCompra);

        stmt.executeUpdate();
        JOptionPane.showMessageDialog(null, "Entrega inserida com sucesso!");
    }

    public static void listar(Connection connection) throws SQLException {
        String sql = "SELECT * FROM entrega";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        StringBuilder resultados = new StringBuilder("Entregas:\n");
        while (rs.next()) {
            resultados.append(String.format("ID: %d | ID Cliente: %d | ID Entregador: %d | Data e Hora: %s | Valor Entrega: %.2f | Valor Total Compra: %.2f\n",
                    rs.getInt("id_entrega"),
                    rs.getInt("id_cliente"),
                    rs.getInt("id_entregador"),
                    rs.getTimestamp("data_hora"),
                    rs.getDouble("valor_entrega"),
                    rs.getDouble("valor_total_compra")));
        }

        JOptionPane.showMessageDialog(null, resultados.toString());
    }

    public static void atualizar(Connection connection) throws SQLException {
        int id = Integer.parseInt(JOptionPane.showInputDialog("ID da entrega a ser atualizada:"));
        int idCliente = Integer.parseInt(JOptionPane.showInputDialog("Novo ID do Cliente:"));
        int idEntregador = Integer.parseInt(JOptionPane.showInputDialog("Novo ID do Entregador:"));
        String dataHora = JOptionPane.showInputDialog("Nova Data e Hora (yyyy-MM-dd HH:mm:ss):");
        double valorEntrega = Double.parseDouble(JOptionPane.showInputDialog("Novo Valor da Entrega:"));
        double valorTotalCompra = Double.parseDouble(JOptionPane.showInputDialog("Novo Valor Total da Compra:"));

        String sql = "UPDATE entrega SET id_cliente=?, id_entregador=?, data_hora=?, valor_entrega=?, valor_total_compra=? WHERE id_entrega=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, idCliente);
        stmt.setInt(2, idEntregador);
        stmt.setTimestamp(3, Timestamp.valueOf(dataHora));
        stmt.setDouble(4, valorEntrega);
        stmt.setDouble(5, valorTotalCompra);
        stmt.setInt(6, id);

        stmt.executeUpdate();
        JOptionPane.showMessageDialog(null, "Entrega atualizada com sucesso!");
    }

    public static void deletar(Connection connection) throws SQLException {
        int id = Integer.parseInt(JOptionPane.showInputDialog("ID da entrega a ser deletada:"));

        String sql = "DELETE FROM entrega WHERE id_entrega=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);

        stmt.executeUpdate();
        JOptionPane.showMessageDialog(null, "Entrega deletada com sucesso!");
    }
}
