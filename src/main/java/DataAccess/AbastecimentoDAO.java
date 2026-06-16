package DataAccess;

import DomainModel.Abastecimento;

public class AbastecimentoDAO extends GenericDAO<Abastecimento> {
    private static AbastecimentoDAO instance;

    private AbastecimentoDAO() {
        super(Abastecimento.class);
    }

    public static AbastecimentoDAO getInstance() {
        if (instance == null) {
            instance = new AbastecimentoDAO();
        }
        return instance;
    }
}
