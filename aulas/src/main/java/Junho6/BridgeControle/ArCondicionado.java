package Junho6.BridgeControle;

public class ArCondicionado implements ControleRemoto {
    @Override
    public void ligar() {
        System.out.println("ArCondicionado ligado!");
    }

    @Override
    public void desligar() {
        System.out.println("ArCondicionado desligado!");
    }

    @Override
    public void aumentar() {
        System.out.println("Temp do ArCondicionado aumentado");
    }

    @Override
    public void diminuir() {
        System.out.println("Temp do ArCondicionado diminuido");
    }

}
