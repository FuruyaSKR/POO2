package Junho6.BridgeControle;

public class ControleRemotoComDispositivo implements ControleRemoto {
    private ControleRemoto controleRemoto;

    public ControleRemotoComDispositivo(ControleRemoto dispositivo) {
        this.controleRemoto = dispositivo;
    }

    @Override
    public void ligar() {
        controleRemoto.ligar();
    }

    @Override
    public void desligar() {
        controleRemoto.desligar();
    }

    @Override
    public void aumentar() {
        controleRemoto.aumentar();
    }

    @Override
    public void diminuir() {
        controleRemoto.diminuir();
    }
}
