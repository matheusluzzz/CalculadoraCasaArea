package calculadora;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CalculadoraArea {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numComodos = -1;
        while (numComodos <= 0) {
            System.out.println("Digite o número de cômodos na casa:");
            try {
                numComodos = scanner.nextInt();
                if (numComodos <= 0) {
                    System.out.println("Número de cômodos deve ser positivo. Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, insira um número inteiro.");
                scanner.next();
            }
        }
        scanner.nextLine();

        final String[][] comodos = new String[numComodos][4];
        final String[] categorias = {"Sala", "Cozinha", "Banheiro", "Quarto", "Outro"};

        for (int i = 0; i < numComodos; i++) {
            System.out.println("Digite o nome do cômodo " + (i + 1) + ":");
            comodos[i][0] = scanner.nextLine();

            System.out.println("Selecione a categoria do cômodo:");
            for (int j = 0; j < categorias.length; j++) {
                System.out.println((j + 1) + ". " + categorias[j]);
            }

            int categoriaIndex = -1;
            while (categoriaIndex < 1 || categoriaIndex > categorias.length) {
                try {
                    categoriaIndex = scanner.nextInt();
                    if (categoriaIndex < 1 || categoriaIndex > categorias.length) {
                        System.out.println("Opção inválida. Tente novamente.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Por favor, selecione um número.");
                    scanner.next();
                }
            }
            scanner.nextLine();
            comodos[i][1] = categorias[categoriaIndex - 1];

            double largura = obterMedidaValida(scanner, "largura");
            double comprimento = obterMedidaValida(scanner, "comprimento");
            comodos[i][2] = String.valueOf(largura);
            comodos[i][3] = String.valueOf(comprimento);
        }

        double areaTotal = 0;
        for (String[] comodo : comodos) {
            double largura = Double.parseDouble(comodo[2]);
            double comprimento = Double.parseDouble(comodo[3]);
            double areaComodo = largura * comprimento;
            areaTotal += areaComodo;
        }

        System.out.println("A área total da casa é de: " + areaTotal + " m².");

        criarInterfaceGrafica(comodos, areaTotal);

        scanner.close();
    }

    private static void criarInterfaceGrafica(String[][] comodos, double areaTotal) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Visualização das Áreas");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 700);
            frame.setLayout(new BorderLayout());

            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

            CanvasDeAreas canvas = new CanvasDeAreas(comodos);
            canvas.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            canvas.setBackground(Color.WHITE);

            JPanel tabelaPanel = new JPanel(new BorderLayout());
            tabelaPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
            String[] colunas = {"Nome", "Categoria", "Largura (m)", "Comprimento (m)", "Área (m²)"};
            DefaultTableModel tableModel = new DefaultTableModel(colunas, 0);
            JTable tabela = new JTable(tableModel);
            tabela.setFillsViewportHeight(true);
            tabela.setGridColor(Color.GRAY);
            tabela.setBackground(Color.LIGHT_GRAY);
            tabela.setFont(new Font("Arial", Font.PLAIN, 14));

            for (String[] comodo : comodos) {
                double largura = Double.parseDouble(comodo[2]);
                double comprimento = Double.parseDouble(comodo[3]);
                double area = largura * comprimento;
                tableModel.addRow(new Object[]{comodo[0], comodo[1], largura, comprimento, area});
            }

            JLabel areaTotalLabel = new JLabel("Área Total: " + areaTotal + " m²");
            areaTotalLabel.setHorizontalAlignment(SwingConstants.CENTER);
            areaTotalLabel.setFont(new Font("Arial", Font.BOLD, 16));
            areaTotalLabel.setBorder(new EmptyBorder(10, 0, 0, 0));
            tabelaPanel.add(new JScrollPane(tabela), BorderLayout.CENTER);
            tabelaPanel.add(areaTotalLabel, BorderLayout.SOUTH);

            JButton refreshButton = new JButton("Atualizar");
            refreshButton.setFont(new Font("Arial", Font.PLAIN, 14));
            refreshButton.addActionListener(e -> {});
            tabelaPanel.add(refreshButton, BorderLayout.NORTH);

            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, canvas, tabelaPanel);
            splitPane.setDividerLocation(500);

            mainPanel.add(splitPane, BorderLayout.CENTER);

            JPanel topPanel = new JPanel(new BorderLayout());
            JButton closeButton = new JButton("Fechar");
            closeButton.setFont(new Font("Arial", Font.PLAIN, 14));
            closeButton.addActionListener(e -> frame.dispose());
            topPanel.add(closeButton, BorderLayout.EAST);

            JLabel welcomeLabel = new JLabel("Bem-vindo à Visualização das Áreas da Casa!", SwingConstants.CENTER);
            welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
            topPanel.add(welcomeLabel, BorderLayout.CENTER);

            frame.add(topPanel, BorderLayout.NORTH);
            frame.add(mainPanel, BorderLayout.CENTER);

            frame.setVisible(true);
        });
    }

    private static double obterMedidaValida(Scanner scanner, String medida) {
        double valor = -1;
        while (valor <= 0) {
            System.out.println("Digite a " + medida + " do cômodo em metros:");
            try {
                valor = scanner.nextDouble();
                if (valor <= 0) {
                    System.out.println("A " + medida + " deve ser um valor positivo. Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, insira um número decimal.");
                scanner.next();
            }
        }
        scanner.nextLine();
        return valor;
    }
}
