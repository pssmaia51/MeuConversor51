package br.com.conversomoeda;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class appMoeda {
    private static final String API_KEY = "ef3175fc6eb8f78d6fd445d9";

    public static void main(String[] args) {
        Scanner menu = new Scanner(System.in);

        while (true) {
            System.out.print("|--------------------Menu de Opções------------------|\n");
            System.out.print("|----------------------------------------------------|\n");
            System.out.print("| Opção 1 - Euro (EUR) pra Dolar (USD)               |\n");
            System.out.print("| Opção 2 - Dolar (USD) pra Euro (EUR)               |\n");
            System.out.print("| Opção 3 - Real (BRL) pra Dolar (USD)               |\n");
            System.out.print("| Opção 4 - Dolar (USD) pra Real (BRL)               |\n");
            System.out.print("| Opção 5 - Pesos Argentinos(ARS) pra Dolar (USD)    |\n");
            System.out.print("| Opção 6 - Dolar (USD) pra Pesos Argentinos(ARS)    |\n");
            System.out.print("| Opção 0 - Sair                                     |\n");
            System.out.print("|----------------------------------------------------|\n");
            System.out.print("Digite uma opção: ");

            int opcao = menu.nextInt();

            if (opcao == 0) {
                System.out.print("\nConversor Finalizado!");
                menu.close();
                break;
            }

            System.out.print("Digite o valor a ser convertido: ");
            double valor = menu.nextDouble();

            switch (opcao) {
                case 1:
                    System.out.printf("\n%.2f EUR = %.2f USD\n", valor, converterMoeda("EUR", "USD", valor));
                    break;

                case 2:
                    System.out.printf("\n%.2f USD = %.2f EUR\n", valor, converterMoeda("USD", "EUR", valor));
                    break;

                case 3:
                    System.out.printf("\n%.2f BRL = %.2f USD\n", valor, converterMoeda("BRL", "USD", valor));
                    break;

                case 4:
                    System.out.printf("\n%.2f USD = %.2f BRL\n", valor, converterMoeda("USD", "BRL", valor));
                    break;

                case 5:
                    System.out.printf("\n%.2f ARS = %.2f USD\n", valor, converterMoeda("ARS", "USD", valor));
                    break;

                case 6:
                    System.out.printf("\n%.2f USD = %.2f ARS\n", valor, converterMoeda("USD", "ARS", valor));
                    break;

                default:
                    System.out.print("\nOpção Inválida! tente novamente com uma opção valida!\n");
                    break;
            }
        }
    }

    private static double converterMoeda(String from, String to, double valor) {
        try {
            String url_str = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/" + from;
            URL url = new URL(url_str);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject jsonobj = root.getAsJsonObject();

            double taxaDeCambio = jsonobj.getAsJsonObject("conversion_rates").get(to).getAsDouble();

            return valor * taxaDeCambio;
        } catch (Exception e) {
            System.out.println("Erro ao obter taxa de câmbio: " + e.getMessage());
            return 0;
        }
    }
}