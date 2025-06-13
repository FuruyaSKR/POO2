package Junho13.Decorator;

abstract class Janela {
    public abstract void draw();

}

class JanelaSimples extends Janela {
    public void draw() {
        System.out.println("Desenha uma janela");
    }
}

abstract class JanelaDecorator extends Janela {
    protected Janela janelaDecorada;

    public JanelaDecorator(Janela janelaDecorada) {
        this.janelaDecorada = janelaDecorada;
    }
}

class DecoradorBarraVertical extends JanelaDecorator {
    public DecoradorBarraVertical(Janela janelaDecorada) {
        super(janelaDecorada);
    }

    public void draw() {
        drawBarraVertical();
        janelaDecorada.draw();
    }

    private void drawBarraVertical() {
        System.out.println("Desenha uma barra vertical na janela");
    }
}