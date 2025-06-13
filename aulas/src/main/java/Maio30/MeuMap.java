package Maio30;

/**
 * @author Victor
 * 
 * 
 **/

public class MeuMap<T> {
    private String chave;
    private T valor;

    /**
     * Detalahmento do constructor
     * 
     * @param chave Chave da classe, utilizando com uma chave primaria
     * @param valor Valor do tipo generico
     **/
    public MeuMap(String chave, T valor) {
        super();
        this.chave = chave;
        this.valor = valor;
    }

    public String getChave() {
        return this.chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public T getValor() {
        return this.valor;
    }

    public void setValor(T valor) {
        this.valor = valor;
    }

}
