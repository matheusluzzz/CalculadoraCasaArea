package calculadora;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CalculadoraArea {

    private static double obterMedidaValida(Scanner scanner, String tipoMedida) {
        double medida = -1;
        while (medida <= 0) {
            System.out.println("Digite a " + tipoMedida + " do cômodo em metros (valor positivo):");
            if (scanner.hasNextDouble()) {
                medida = scanner.nextDouble();
                if (medida <= 0) {
                    System.out.println("Valor inválido. Tente novamente.");
                }
            } else {
                System.out.println("Entrada inválida. Por favor, insira um número.");
                scanner.next();
            }
        }
        scanner.nextLine();
        return medida;
    }

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

        String[][] comodos = new String[numComodos][3];

        for (int i = 0; i < numComodos; i++) {
            System.out.println("Digite o nome do cômodo " + (i + 1) + ":");
            comodos[i][0] = scanner.nextLine();
            double largura = obterMedidaValida(scanner, "largura");
            double comprimento = obterMedidaValida(scanner, "comprimento");
            comodos[i][1] = String.valueOf(largura);
            comodos[i][2] = String.valueOf(comprimento);
        }

        System.out.println("Todas as dimensões foram inseridas. Calculando a área total...");

        double areaTotal = 0;
        for (int i = 0; i < numComodos; i++) {
            String nomeComodo = comodos[i][0];
            double largura = 0, comprimento = 0;
            try {
                largura = Double.parseDouble(comodos[i][1]);
                comprimento = Double.parseDouble(comodos[i][2]);
            } catch (NumberFormatException e) {
                System.out.println("Erro na conversão das dimensões do cômodo " + nomeComodo + ". Por favor, verifique os dados.");
                continue;
            }
            double areaComodo = largura * comprimento;
            areaTotal += areaComodo;

            System.out.println("A área de " + nomeComodo + " é de: " + areaComodo + " m².");
        }

        System.out.println("E a área total da casa é de: " + areaTotal + " m².");

        scanner.close();
    }
}




