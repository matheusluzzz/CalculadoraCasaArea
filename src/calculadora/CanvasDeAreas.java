package calculadora;

import javax.swing.*;
import java.awt.*;

public class CanvasDeAreas extends JPanel {
    private final String[][] comodos;

    public CanvasDeAreas(String[][] comodos) {
        this.comodos = comodos;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int x = 20;
        int y = 20;
        int padding = 40;
        int maxWidth = getWidth() - padding * 2;

        for (String[] comodo : comodos) {
            double largura = Double.parseDouble(comodo[2]);
            double comprimento = Double.parseDouble(comodo[3]);
            int larguraPixel = (int) (largura * 50);
            int comprimentoPixel = (int) (comprimento * 50);

            if (x + larguraPixel > maxWidth) {
                x = padding;
                y += comprimentoPixel + padding;
            }

            g2d.setColor(getColorForCategory(comodo[1]));
            g2d.fillRect(x, y, larguraPixel, comprimentoPixel);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, y, larguraPixel, comprimentoPixel);

            String texto = comodo[0] + " (" + comodo[1] + ")";
            String medidas = "L: " + largura + "m, C: " + comprimento + "m";
            FontMetrics metrics = g2d.getFontMetrics();
            int textWidth = metrics.stringWidth(texto);
            int medidasWidth = metrics.stringWidth(medidas);
            int textX = x + (larguraPixel - textWidth) / 2;
            int medidasX = x + (larguraPixel - medidasWidth) / 2;
            int textY = y + (comprimentoPixel / 2) - metrics.getHeight();
            int medidasY = textY + metrics.getHeight() + 5;

            g2d.drawString(texto, textX, textY);
            g2d.drawString(medidas, medidasX, medidasY);

            x += larguraPixel + padding;
        }
    }

    private Color getColorForCategory(String category) {
        switch (category.toLowerCase()) {
            case "sala":
                return Color.CYAN;
            case "cozinha":
                return Color.ORANGE;
            case "banheiro":
                return Color.PINK;
            case "quarto":
                return Color.GREEN;
            case "outro":
            default:
                return Color.LIGHT_GRAY;
        }
    }
}
