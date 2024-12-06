import javax.swing.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class Main {
    static Connection connection;

    public static void main(String[] args) {
        try {

            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/fruteira", "root", "123456789");

            while (true) {
                String menu = """
                        Fruteira do William 🍎
                        
                        -----MENU-----
                        
                        1 - Cliente
                        2 - Entrega
                        3 - Funcionário
                        4 - Entregador
                        5 - Produto
                        6 - Histórico de Compra
                        0 - Sair
                        
                        Escolha uma opção:
                        """;
                String opcao = JOptionPane.showInputDialog(menu);

                if (opcao == null || opcao.equals("0")) break;

                switch (opcao) {
                    case "1" -> Cliente.menu(connection);
                    case "2" -> Entrega.menu(connection);
                    case "3" -> Funcionario.menu(connection);
                    case "4" -> Entregador.menu(connection);
                    case "5" -> Produto.menu(connection);
                    case "6" -> HistoricoCompra.menu(connection);
                    default -> JOptionPane.showMessageDialog(null, "Opção inválida!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }
}