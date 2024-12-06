import javax.swing.*;
import java.sql.*;

class Cliente {
    public static void menu(Connection connection) {
        while (true) {
            String menu = """
                      Cliente:
                      
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
        String nome = JOptionPane.showInputDialog("Nome:");
        String cpfCnpj = JOptionPane.showInputDialog("CPF/CNPJ:");
        String endereco = JOptionPane.showInputDialog("Endereço:");
        String telefone = JOptionPane.showInputDialog("Telefone:");
        String email = JOptionPane.showInputDialog("Email:");
        String tipo = JOptionPane.showInputDialog("Tipo (Física/Jurídica):");
        String responsavel = JOptionPane.showInputDialog("Responsável:");

        String sql = "INSERT INTO cliente (nome, cpf_cnpj, endereco, telefone, email, tipo, responsavel) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, nome);
        stmt.setString(2, cpfCnpj);
        stmt.setString(3, endereco);
        stmt.setString(4, telefone);
        stmt.setString(5, email);
        stmt.setString(6, tipo);
        stmt.setString(7, responsavel);

        stmt.executeUpdate();
        JOptionPane.showMessageDialog(null, "Cliente inserido com sucesso!");
    }

    public static void listar(Connection connection) throws SQLException {
        String sql = "SELECT * FROM cliente";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        StringBuilder resultados = new StringBuilder("Clientes:\n");
        while (rs.next()) {
            resultados.append(String.format("ID: %d | Nome: %s | CPF/CNPJ: %s | Endereço: %s | Telefone: %s | Email: %s | Tipo: %s | Responsável: %s\n",
                    rs.getInt("id_cliente"),
                    rs.getString("nome"),
                    rs.getString("cpf_cnpj"),
                    rs.getString("endereco"),
                    rs.getString("telefone"),
                    rs.getString("email"),
                    rs.getString("tipo"),
                    rs.getString("responsavel")));
        }

        JOptionPane.showMessageDialog(null, resultados.toString());
    }

    public static void atualizar(Connection connection) throws SQLException {
        int id = Integer.parseInt(JOptionPane.showInputDialog("ID do cliente a ser atualizado:"));

        String nome = JOptionPane.showInputDialog("Nome:");
        String cpfCnpj = JOptionPane.showInputDialog("CPF/CNPJ:");
        String endereco = JOptionPane.showInputDialog("Endereço:");
        String telefone = JOptionPane.showInputDialog("Telefone:");
        String email = JOptionPane.showInputDialog("Email:");
        String tipo = JOptionPane.showInputDialog("Tipo (Física/Jurídica):");
        String responsavel = JOptionPane.showInputDialog("Responsável:");

        String sql = "UPDATE cliente SET nome=?, cpf_cnpj=?, endereco=?, telefone=?, email=?, tipo=?, responsavel=? WHERE id_cliente=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, nome);
        stmt.setString(2, cpfCnpj);
        stmt.setString(3, endereco);
        stmt.setString(4, telefone);
        stmt.setString(5, email);
        stmt.setString(6, tipo);
        stmt.setString(7, responsavel);
        stmt.setInt(8, id);

        stmt.executeUpdate();
        JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso!");
    }

    public static void deletar(Connection connection) throws SQLException {
        int id = Integer.parseInt(JOptionPane.showInputDialog("ID do cliente a ser deletado:"));

        String sql = "DELETE FROM cliente WHERE id_cliente=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);

        stmt.executeUpdate();
        JOptionPane.showMessageDialog(null, "Cliente deletado com sucesso!");
    }
}