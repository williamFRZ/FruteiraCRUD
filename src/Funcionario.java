import javax.swing.*;
import java.sql.*;

class Funcionario {
    public static void menu(Connection connection) {
        while (true) {
            String menu = """
                      Funcionário:
                      
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
        String cpf = JOptionPane.showInputDialog("CPF:");
        String endereco = JOptionPane.showInputDialog("Endereço:");
        String cargo = JOptionPane.showInputDialog("Cargo:");
        double salario = Double.parseDouble(JOptionPane.showInputDialog("Salário:"));

        String sql = "INSERT INTO funcionario (nome, cpf, endereco, cargo, salario) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, nome);
        stmt.setString(2, cpf);
        stmt.setString(3, endereco);
        stmt.setString(4, cargo);
        stmt.setDouble(5, salario);

        stmt.executeUpdate();
        JOptionPane.showMessageDialog(null, "Funcionário inserido com sucesso!");
    }

    public static void listar(Connection connection) throws SQLException {
        String sql = "SELECT * FROM funcionario";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        StringBuilder resultados = new StringBuilder("Funcionários:\n");
        while (rs.next()) {
            resultados.append(String.format("ID: %d | Nome: %s | CPF: %s | Endereço: %s | Cargo: %s | Salário: %.2f\n",
                    rs.getInt("id_funcionario"),
                    rs.getString("nome"),
                    rs.getString("cpf"),
                    rs.getString("endereco"),
                    rs.getString("cargo"),
                    rs.getDouble("salario")));
        }

        JOptionPane.showMessageDialog(null, resultados.toString());
    }

    public static void atualizar(Connection connection) throws SQLException {
        int id = Integer.parseInt(JOptionPane.showInputDialog("ID do funcionário a ser atualizado:"));

        String nome = JOptionPane.showInputDialog("Novo Nome:");
        String cpf = JOptionPane.showInputDialog("Novo CPF:");
        String endereco = JOptionPane.showInputDialog("Novo Endereço:");
        String cargo = JOptionPane.showInputDialog("Novo Cargo:");
        double salario = Double.parseDouble(JOptionPane.showInputDialog("Novo Salário:"));

        String sql = "UPDATE funcionario SET nome=?, cpf=?, endereco=?, cargo=?, salario=? WHERE id_funcionario=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, nome);
        stmt.setString(2, cpf);
        stmt.setString(3, endereco);
        stmt.setString(4, cargo);
        stmt.setDouble(5, salario);
        stmt.setInt(6, id);

        stmt.executeUpdate();
        JOptionPane.showMessageDialog(null, "Funcionário atualizado com sucesso!");
    }

    public static void deletar(Connection connection) throws SQLException {
        int id = Integer.parseInt(JOptionPane.showInputDialog("ID do funcionário a ser deletado:"));

        String sql = "DELETE FROM funcionario WHERE id_funcionario=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);

        stmt.executeUpdate();
        JOptionPane.showMessageDialog(null, "Funcionário deletado com sucesso!");
    }
}
