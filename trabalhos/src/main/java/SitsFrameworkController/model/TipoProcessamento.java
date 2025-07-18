package SitsFrameworkController.model;

/**
 * Enumeração dos tipos de processamento de imagem disponíveis no sistema.
 * 
 * <ul>
 * <li>{@link #CINZA} — Conversão para escala de cinza</li>
 * <li>{@link #BINARIZACAO} — Binarização da imagem</li>
 * <li>{@link #BORDA} — Detecção de bordas</li>
 * <li>{@link #NEGATIVO} — Negativo da imagem</li>
 * <li>{@link #CONTRASTE} — Realce de contraste</li>
 * </ul>
 */
public enum TipoProcessamento {
    CINZA,
    BINARIZACAO,
    BORDA,
    NEGATIVO,
    CONTRASTE,
}
